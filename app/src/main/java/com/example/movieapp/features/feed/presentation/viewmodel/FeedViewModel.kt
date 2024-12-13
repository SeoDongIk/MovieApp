package com.example.movieapp.features.feed.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.features.common.entity.EntityWrapper
import com.example.movieapp.features.common.repository.IMovieDataSource
import com.example.movieapp.features.feed.domain.usecase.IGetFeedCategoryUseCase
import com.example.movieapp.features.feed.presentation.input.IFeedViewModelInput
import com.example.movieapp.features.feed.presentation.output.FeedState
import com.example.movieapp.features.feed.presentation.output.FeedUiEffect
import com.example.movieapp.features.feed.presentation.output.IFeedViewModelOutput
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val getFeedCategoryUseCase: IGetFeedCategoryUseCase
) : ViewModel(), IFeedViewModelOutput, IFeedViewModelInput {

    val output: IFeedViewModelOutput = this
    val input: IFeedViewModelInput = this

    private val _feedState: MutableStateFlow<FeedState> =
        MutableStateFlow(FeedState.Loading)
    override val feedState: StateFlow<FeedState>
        get() = _feedState

    private val _feedUiEffect = MutableSharedFlow<FeedUiEffect>(replay = 0)
    override val feedUiEffect: SharedFlow<FeedUiEffect>
        get() = _feedUiEffect

    init {
        fetchFeed()
    }

    private fun fetchFeed() {
        viewModelScope.launch {
            _feedState.value = FeedState.Loading

            val categories = getFeedCategoryUseCase()
            _feedState.value = when(categories) {
                is EntityWrapper.Success -> {
                    FeedState.Main(
                        categories = categories.entity
                    )
                }

                is EntityWrapper.Fail -> {
                    FeedState.Failed(
                        reason = categories.error.message ?: "Unknown Error"
                    )
                }
            }
        }
    }

    override fun openDetail(movieName: String) {
        viewModelScope.launch {
            _feedUiEffect.emit(
                FeedUiEffect.OpenMovieDetail(movieName)
            )
        }
    }

    override fun openInfoDialog() {
        viewModelScope.launch {
            _feedUiEffect.emit(
                FeedUiEffect.OpenInfoDialog
            )
        }
    }

    override fun refreshFeed() {
        TODO("Not yet implemented")
    }

}