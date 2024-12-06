package com.example.movieapp.library.network.retrofit

import com.example.movieapp.library.network.api.ApiService
import com.example.movieapp.library.network.model.ApiResponse
import com.example.movieapp.library.network.model.ApiResult
import com.example.movieapp.library.network.model.NetworkRequestInfo
import com.example.movieapp.library.network.model.RequestType
import com.google.gson.Gson
import java.lang.reflect.Type
import javax.inject.Inject

class NetworkRequestFactoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val gson: Gson,
    private val headerParser: HeaderParser
): NetworkRequestFactory {

    companion object {
        private const val KEY_HEADER_CONTENT_TYPE = "Content-Type"
        private const val CONTENT_TYPE_JSON = "application/json"
        private const val TYPE_XML = "xml"
    }

    override suspend fun <T> create(
        url: String,
        requestInfo: NetworkRequestInfo,
        type: Type
    ): ApiResult<T> {

        val headerMap = hashMapOf<String, String>().apply {
            put(KEY_HEADER_CONTENT_TYPE, CONTENT_TYPE_JSON)
            putAll(requestInfo.headers)
        }

        return try {
            val response = when(requestInfo.method) {
                RequestType.GET -> performGetRequest(headerMap, url)
                RequestType.POST -> performPostRequest(headerMap, url, requestInfo.body)
                RequestType.PUT -> performPutRequest(headerMap, url, requestInfo.body)
                RequestType.DELETE -> performDeleteRequest(headerMap, url, requestInfo.body)
            }

            val responseHeaders = headerParser.parseHeadersToMap(response.headers())
            val responseCode = response.code()

            val apiResponse = if(response.isSuccessful) {
                val body = response.body()
                val responseModel: T = gson.fromJson(body, type)
                ApiResponse.Success(responseModel)
            } else {
                val errorMessage = response.errorBody()?.string() ?: response.message()
                ApiResponse.Fail(Throwable(errorMessage))
            }
            ApiResult(apiResponse, responseHeaders, responseCode)
        } catch (genericException: Exception) {
            ApiResult(ApiResponse.Fail(genericException))
        }
    }

    private suspend fun performGetRequest(
        headers: Map<String, String>,
        url: String
    ) = apiService.get(url = url, headerMap = headers)

    private suspend fun performPostRequest(
        headers: Map<String, String>,
        url: String,
        body: Any?
    ) = body?.let { body ->
        apiService.post(headerMap = headers, url = url, body = body)
    } ?: apiService.post(headers, url)

    private suspend fun performPutRequest(
        headers: Map<String, String>,
        url: String,
        body: Any?
    ) = body?.let { body ->
        apiService.put(headerMap = headers, url = url, body = body)
    } ?: apiService.put(headerMap = headers, url = url)

    private suspend fun performDeleteRequest(
        headers: Map<String, String>,
        url: String,
        body: Any?
    ) = body?.let { body ->
        apiService.delete(headerMap = headers, url = url, body = body)
    } ?: apiService.delete(headerMap = headers, url = url)
}