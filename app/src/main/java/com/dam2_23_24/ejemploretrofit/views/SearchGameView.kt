package com.dam2_23_24.ejemploretrofit.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dam2_23_24.ejemploretrofit.viewModel.GamesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchGameView(viewModel: GamesViewModel, navController: NavController) {

    val query by viewModel.query.collectAsState()
    val active by viewModel.active.collectAsState()
    val games by viewModel.games.collectAsState()

    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        query = query,
        onQueryChange = { viewModel.setQuery(it) },
        onSearch = { viewModel.setActive(false) },
        active = active,
        onActiveChange = { viewModel.setActive(it) },
        placeholder = { Text(text = "Search") },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "")
        },
        trailingIcon = {
            Icon(imageVector = Icons.Default.Close, contentDescription = "",
                modifier = Modifier.clickable { navController.popBackStack() }
            )
        }
    ) {
        if (query.isNotEmpty()) {
            val filterGames = games.filter { it.name.contains(query, ignoreCase = true) }
            filterGames.forEach {
                Text(text = it.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(bottom = 10.dp, start = 10.dp)
                        .clickable {
                            navController.navigate("DetailView/${it.id}")
                        }
                )
            }
        }
    }

}