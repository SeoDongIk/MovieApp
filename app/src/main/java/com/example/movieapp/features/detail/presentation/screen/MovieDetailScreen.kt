package com.example.movieapp.features.detail.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.example.movieapp.R
import com.example.movieapp.features.common.entity.MovieDetailEntity
import com.example.movieapp.features.detail.presentation.input.IDetailViewModelInputs
import com.example.movieapp.features.detail.presentation.output.MovieDetailState
import com.example.movieapp.ui.component.buttons.PrimaryButton
import com.example.movieapp.ui.component.buttons.SecondaryButton
import com.example.movieapp.ui.component.movie.MovieMeta
import com.example.movieapp.ui.theme.Paddings
import com.example.movieapp.ui.util.getAnnotatedText

@Composable
fun MovieDetailScreen(
    movieDetailState: State<MovieDetailState>,
    input: IDetailViewModelInputs
) {
    val movieDetail by movieDetailState

    if(movieDetail is MovieDetailState.Main) {
        MovieDetail(
            movie = (movieDetail as MovieDetailState.Main).movieDetailEntity,
            input = input
        )
    }

}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MovieDetail(
    movie: MovieDetailEntity,
    input: IDetailViewModelInputs
) {
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                modifier = Modifier.requiredHeight(70.dp),
                navigationIcon = {
                    IconButton(onClick = { input.goBackToFeed() }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_back),
                            contentDescription = null
                        )
                    }
                },
                backgroundColor = MaterialTheme.colors.surface,
                elevation = 0.dp
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(
                    horizontal = Paddings.extra
                )
                .scrollable(
                    state = scrollState,
                    orientation = Orientation.Vertical
                )
        ) {
            Row {
                BigPoster(
                    movie = movie
                )

                Column(
                    modifier = Modifier.padding(start = Paddings.xlarge)
                ) {
                    MovieMeta(
                        key = "Rating",
                        value = movie.rating.toString()
                    )

                    MovieMeta(
                        key = "Director",
                        value = movie.directors.toString()
                    )

                    MovieMeta(
                        key = "Starring",
                        value = movie.actors.toString()
                    )

                    MovieMeta(
                        key = "Genre",
                        value = movie.genre.toString()
                    )
                }
            }

            Text(
                text = getAnnotatedText(text = movie.title),
                modifier = Modifier.padding(
                    top = Paddings.extra,
                    bottom = Paddings.large
                ),
                style = MaterialTheme.typography.h3
            )

            Text(
                text = getAnnotatedText(text = movie.desc),
                modifier = Modifier.padding(
                    bottom = Paddings.xlarge
                ),
                style = MaterialTheme.typography.body2
            )

            PrimaryButton(
                modifier = Modifier
                    .padding(top = Paddings.xlarge)
                    .fillMaxWidth(),
                text = "Add Rating Score",
                onClick = {
                    input.rateClicked()
                }
            )

            SecondaryButton(
                modifier = Modifier
                    .padding(top = Paddings.xlarge)
                    .fillMaxWidth(),
                text = "OPEN IMDB",
                onClick = {
                    input.openImdbClicked()
                }
            )
        }
    }
}

@Composable
fun BigPoster(
    movie: MovieDetailEntity
) {
    Card {
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest
                    .Builder(LocalContext.current)
                    .data(data = movie.thumbUrl)
                    .apply {
                        crossfade(true)
                        scale(Scale.FILL)
                    }.build()
            ),
            modifier = Modifier
                .width(180.dp)
                .height(250.dp),
            contentScale = ContentScale.FillHeight,
            contentDescription = "Movie Poster Image"
        )
    }
}