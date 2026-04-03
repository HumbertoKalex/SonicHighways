package com.sonichighways

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sonichighways.core.ui.theme.SonicHighwaysTheme
import com.sonichighways.navigation.SonicHighwaysNavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            SonicHighwaysTheme {
                SonicHighwaysNavGraph()
            }
        }
    }
}