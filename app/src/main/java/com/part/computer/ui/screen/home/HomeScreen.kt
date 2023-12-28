package com.part.computer.ui.screen.home

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.part.computer.R
import com.part.computer.di.Injection
import com.part.computer.model.OrderPart
import com.part.computer.ui.ViewModelFactory
import com.part.computer.ui.common.UiState
import com.part.computer.ui.components.PartItem
@Composable
fun HomeScreen(navigateToDetail: (Long) -> Unit) {
    val viewModel: HomeViewModel = viewModel(factory = ViewModelFactory(Injection.provideRepository()))
    val uiState by rememberUpdatedState(newValue = viewModel.uiState.collectAsState(initial = UiState.Loading).value)

    when (uiState) {
        is UiState.Loading -> viewModel.getAllPart()
        is UiState.Success -> HomeContent(
            orderPart = (uiState as UiState.Success<List<OrderPart>>).data,
            navigateToDetail = navigateToDetail
        )
        is UiState.Error -> { /* Handle error state */ }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomeContent(orderPart: List<OrderPart>, navigateToDetail: (Long) -> Unit) {
    var searchText by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(modifier = Modifier.fillMaxWidth()) {
        Column {
            SearchTextField(searchText, onValueChange = { searchText = it }, onSearch = { keyboardController?.hide() })

            LazyVerticalGrid(
                columns = GridCells.Adaptive(160.dp),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
            ) {

                val sortedOrderPart = orderPart.sortedBy {
                    it.thePart.title.contains(searchText, ignoreCase = true).not()
                }

                items(orderPart) { data ->
                    if (data.thePart.title.contains(searchText, ignoreCase = true)) {
                        PartItem(
                            image = data.thePart.image,
                            title = data.thePart.title,
                            requiredPoint = data.thePart.requiredPoint,
                            modifier = Modifier.clickable {
                                navigateToDetail(data.thePart.id)

                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SearchTextField(value: String, onValueChange: (String) -> Unit, onSearch: () -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },
        placeholder = { Text(stringResource(id = R.string.search_hint)) },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { onSearch() }),
        shape = MaterialTheme.shapes.large,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .heightIn(min = 48.dp)
            .clip(MaterialTheme.shapes.large)
            .border(5.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))

    )
}