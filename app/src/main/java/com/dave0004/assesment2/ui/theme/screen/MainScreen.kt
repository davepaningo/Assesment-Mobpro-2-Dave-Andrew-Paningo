package com.dave0004.assesment2.ui.theme.screen


import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.RadioButton
import androidx.compose.material3.TextButton
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dave0004.assesment2.R
import com.dave0004.assesment2.model.Workout
import com.dave0004.assesment2.navigation.Screen
import com.dave0004.assesment2.ui.theme.Assesment2Theme
import com.dave0004.assesment2.util.SettingsDataStore
import com.dave0004.assesment2.util.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {

    val dataStore = SettingsDataStore(LocalContext.current)
    val showList by dataStore.layoutFlow.collectAsState(true)
    val selectedTheme by dataStore.themeFlow.collectAsState("Purple")
    var showThemeDialog by remember { mutableStateOf(false) }

    if (showThemeDialog) {
        ThemePickerDialog(
            selectedTheme = selectedTheme,
            onThemeSelected = { theme ->
                CoroutineScope(Dispatchers.IO).launch {
                    dataStore.saveTheme(theme)
                }
                showThemeDialog = false
            },
            onDismiss = { showThemeDialog = false }
        )
    }
    Scaffold(

        topBar = {

            TopAppBar(

                title = {
                    Text(text = "Workout App")
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),

                actions = {

                    IconButton(onClick = { showThemeDialog = true }) {
                        Icon(
                            imageVector = Icons.Filled.Palette,
                            contentDescription = "Pilih Tema",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    IconButton(
                        onClick = {

                            CoroutineScope(Dispatchers.IO).launch {
                                dataStore.saveLayout(!showList)
                            }
                        }
                    ) {

                        Icon(
                            painter = painterResource(
                                if (showList)
                                    R.drawable.baseline_grid_view_24
                                else
                                    R.drawable.baseline_view_list_24
                            ),

                            contentDescription = null,

                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        },

        floatingActionButton = {

            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.FormBaru.route)
                }
            ) {

                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        }

    ) { innerPadding ->

        ScreenContent(
            showList = showList,
            modifier = Modifier.padding(innerPadding),
            navController = navController
        )
    }
}
@Composable
fun ThemePickerDialog(
    selectedTheme: String,
    onThemeSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val themes = listOf("Purple", "Blue", "Green", "Red")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Pilih Tema") },
        text = {
            Column {
                themes.forEach { theme ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = (theme == selectedTheme),
                                onClick = { onThemeSelected(theme) },
                                role = Role.RadioButton
                            )
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (theme == selectedTheme),
                            onClick = null
                        )
                        Text(
                            text = theme,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Tutup")
            }
        }
    )
}
@Composable
fun ScreenContent(
    showList: Boolean,
    modifier: Modifier,
    navController: NavHostController
) {

    val context = LocalContext.current

    val factory = ViewModelFactory(context)

    val viewModel: MainViewModel = viewModel(
        factory = factory
    )

    val data by viewModel.data.collectAsState()

    if (data.isEmpty()) {

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),

            verticalArrangement = Arrangement.Center,

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(text = "Workout Kosong")
        }

    } else {

        if (showList) {

            LazyColumn(

                modifier = modifier.fillMaxSize(),

                contentPadding = PaddingValues(bottom = 84.dp)

            ) {

                items(data) {

                    ListItem(workout = it) {

                        navController.navigate(
                            Screen.FormUbah.withId(it.id)
                        )
                    }

                    HorizontalDivider()
                }
            }

        } else {

            LazyVerticalStaggeredGrid(

                modifier = modifier.fillMaxSize(),

                columns = StaggeredGridCells.Fixed(2),

                verticalItemSpacing = 8.dp,

                horizontalArrangement = Arrangement.spacedBy(8.dp),

                contentPadding = PaddingValues(
                    8.dp,
                    8.dp,
                    8.dp,
                    84.dp
                )

            ) {

                items(data) {

                    GridItem(workout = it) {

                        navController.navigate(
                            Screen.FormUbah.withId(it.id)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ListItem(
    workout: Workout,
    onClick: () -> Unit
) {

    Column(

        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),

        verticalArrangement = Arrangement.spacedBy(8.dp)

    ) {

        Text(
            text = workout.nama,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = workout.kategori,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Text(text = "Durasi: ${workout.durasi} menit")
        Text(text = "Tanggal: ${workout.tanggal}")
        Text(text = "jam: ${workout.jam}")
    }
}

@Composable
fun GridItem(
    workout: Workout,
    onClick: () -> Unit
) {

    Card(

        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },

        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),

        border = BorderStroke(
            1.dp,
            DividerDefaults.color
        )

    ) {

        Column(

            modifier = Modifier.padding(8.dp),

            verticalArrangement = Arrangement.spacedBy(8.dp)

        ) {

            Text(
                text = workout.nama,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = workout.kategori,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )

            Text(text = "Durasi: ${workout.durasi} menit")
            Text(text = "Tanggal: ${workout.tanggal}")
            Text(text = "Jam: ${workout.jam}")
        }
    }
}

@Preview(showBackground = true)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun MainScreenPreview() {

    Assesment2Theme() {

        MainScreen(
            rememberNavController()
        )
    }
}