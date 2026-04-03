package com.sonichighways.feature.home.presentation.ui.view.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuOpen
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.sonichighways.core.ui.theme.dimens
import com.sonichighways.core.ui.theme.surfaceDark
import com.sonichighways.feature.home.domain.model.Song
import com.sonichighways.feature.home.mock.SongMock
import com.sonichighways.feature.home.presentation.ui.view.search.component.SongListItem
import com.sonichighways.feature.home.presentation.ui.viewmodel.HomeUiState
import com.sonichighways.home.R

@Composable
fun SearchContent(
    uiState: HomeUiState,
    onSearch: (String) -> Unit,
    onSongClick: (Song) -> Unit,
    onViewAlbumClick: (Song) -> Unit,
    onToggleList: () -> Unit,
    isListVisible: Boolean
) {
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(dimens.spacingMedium)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.home_title_songs),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold
            )

            IconButton(onClick = onToggleList) {
                Icon(
                    imageVector = if (isListVisible) Icons.AutoMirrored.Filled.MenuOpen else Icons.Filled.Menu,
                    contentDescription = stringResource(id = if (isListVisible) R.string.home_menu_collapse_desc else R.string.home_menu_expand_desc),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        Spacer(modifier = Modifier.height(dimens.spacingNano))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = dimens.spacingMedium),
            placeholder = { Text("Search your library", color = Color.Gray) },
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.Gray
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(dimens.radiusLarge),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = surfaceDark,
                unfocusedContainerColor = surfaceDark,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = { onSearch(searchQuery) }
            )
        )

        val sectionTitle = if (searchQuery.isEmpty()) {
            "Tocadas recentemente"
        } else {
            "Resultados da busca"
        }

        Text(
            text = sectionTitle,
            style = MaterialTheme.typography.titleMedium,
            color = Color.Gray,
            modifier = Modifier.padding(vertical = dimens.spacingSmall)
        )

        when (uiState) {
            is HomeUiState.Initial -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Search for your favorite songs", color = Color.Gray)
                }
            }

            is HomeUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFF0086A0))
                }
            }

            is HomeUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = uiState.message, color = Color.Red)
                }
            }

            is HomeUiState.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(dimens.spacingMicro)
                ) {
                    items(uiState.songs) { song ->
                        SongListItem(
                            song = song,
                            onClick = { onSongClick(song) },
                            onViewAlbumClick = { onViewAlbumClick(song) }
                        )
                    }
                }
            }
        }
    }
}

@Preview(
    name = "Search Content - Success",
    showBackground = true,
    device= Devices.TABLET
)
@Composable
fun SearchContentSuccessPreview() {

    MaterialTheme {
        SearchContent(
            uiState = HomeUiState.Success(SongMock.songList),
            onSearch = {},
            onSongClick = {},
            onViewAlbumClick = {},
            onToggleList = {},
            isListVisible = true
        )
    }
}
