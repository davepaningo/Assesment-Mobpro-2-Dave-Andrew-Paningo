package com.dave0004.assesment2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.dave0004.assesment2.navigation.SetupNavGraph
import com.dave0004.assesment2.ui.theme.Assesment2Theme
import com.dave0004.assesment2.util.SettingsDataStore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val dataStore = SettingsDataStore(this)
            val selectedTheme by dataStore.themeFlow.collectAsState(initial = "Purple")
            Assesment2Theme(selectedTheme = selectedTheme) {
                SetupNavGraph()
            }
        }
    }
}