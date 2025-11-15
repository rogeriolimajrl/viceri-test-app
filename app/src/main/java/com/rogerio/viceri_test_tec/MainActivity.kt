package com.rogerio.viceri_test_tec

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import com.rogerio.viceri_test_tec.ui.theme.ViceriTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ViceriTheme {
                AppNavHost()
            }
        }
    }
}
