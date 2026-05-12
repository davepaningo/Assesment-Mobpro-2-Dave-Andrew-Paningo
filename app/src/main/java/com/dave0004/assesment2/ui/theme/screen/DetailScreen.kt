package com.dave0004.assesment2.ui.theme.screen

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dave0004.assesment2.R
import com.dave0004.assesment2.ui.theme.Assesment2Theme
import com.dave0004.assesment2.util.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavHostController,
    id: Long? = null
) {

    val factory = ViewModelFactory(navController.context)
    val viewModel: DetailViewModel = viewModel(factory = factory)

    val context = navController.context

    var nama by remember {
        mutableStateOf("")
    }

    var kategori by remember {
        mutableStateOf("")
    }

    var durasi by remember {
        mutableStateOf("")
    }

    var showDialog by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(id) {

        if (id == null) return@LaunchedEffect

        val data = viewModel.getWorkout(id) ?: return@LaunchedEffect

        nama = data.nama
        kategori = data.kategori
        durasi = data.durasi
    }

    Scaffold(

        topBar = {

            TopAppBar(

                navigationIcon = {

                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {

                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },

                title = {

                    if (id == null)
                        Text(text = stringResource(id = R.string.tambah_workout))
                    else
                        Text(text = "Edit Workout")
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),

                actions = {

                    IconButton(
                        onClick = {

                            if (
                                nama.isEmpty() ||
                                kategori.isEmpty() ||
                                durasi.isEmpty()
                            ) {

                                Toast.makeText(
                                    context,
                                    "Data tidak boleh kosong",
                                    Toast.LENGTH_LONG
                                ).show()

                                return@IconButton
                            }

                            if (id == null) {

                                viewModel.insert(
                                    nama,
                                    kategori,
                                    durasi
                                )

                            } else {

                                viewModel.update(
                                    id,
                                    nama,
                                    kategori,
                                    durasi
                                )
                            }

                            navController.popBackStack()
                        }
                    ) {

                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = null
                        )
                    }

                    if (id != null) {

                        DeleteAction {
                            showDialog = true
                        }
                    }
                }
            )
        }

    ) { padding ->

        FormWorkout(

            nama = nama,
            onNamaChange = {
                nama = it
            },

            kategori = kategori,
            onKategoriChange = {
                kategori = it
            },

            durasi = durasi,
            onDurasiChange = {
                durasi = it
            },

            modifier = Modifier.padding(padding)
        )

        if (id != null && showDialog) {

            DisplayAlertDialog(

                onDismissRequest = {
                    showDialog = false
                },

                onConfirmation = {

                    showDialog = false

                    viewModel.delete(id)

                    navController.popBackStack()
                }
            )
        }
    }
}

@Composable
fun FormWorkout(

    nama: String,
    onNamaChange: (String) -> Unit,

    kategori: String,
    onKategoriChange: (String) -> Unit,

    durasi: String,
    onDurasiChange: (String) -> Unit,

    modifier: Modifier

) {

    Column(

        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),

        verticalArrangement = Arrangement.spacedBy(16.dp)

    ) {

        OutlinedTextField(
            value = nama,
            onValueChange = {
                onNamaChange(it)
            },
            label = {
                Text("Nama Workout")
            },
            modifier = Modifier.fillMaxWidth()
        )

        val radioOptions = listOf(
            "Cardio",
            "Strength"
        )

        OutlinedCard(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                radioOptions.forEach { text ->

                    Row(

                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = (text == kategori),
                                onClick = {
                                    onKategoriChange(text)
                                },
                                role = Role.RadioButton
                            )
                            .padding(vertical = 4.dp),

                        verticalAlignment = Alignment.CenterVertically

                    ) {

                        RadioButton(
                            selected = (text == kategori),
                            onClick = null
                        )

                        Text(
                            text = text,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }
        }

        OutlinedTextField(
            value = durasi,
            onValueChange = {
                onDurasiChange(it)
            },
            label = {
                Text("Durasi")
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun DeleteAction(
    delete: () -> Unit
) {

    var expanded by remember {
        mutableStateOf(false)
    }

    IconButton(
        onClick = {
            expanded = true
        }
    ) {

        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(R.string.lainnya)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {

            DropdownMenuItem(

                text = {
                    Text(text = stringResource(id = R.string.hapus))
                },

                onClick = {

                    expanded = false

                    delete()
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun PreviewDetailScreen() {

    Assesment2Theme {

        DetailScreen(
            rememberNavController()
        )
    }
}