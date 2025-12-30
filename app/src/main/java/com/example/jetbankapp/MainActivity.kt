package com.example.jetbankapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.jetbankapp.common.NetworkStatusChecker
import com.example.jetbankapp.ui.navigation.AppNavigation
import com.example.jetbankapp.ui.theme.JetBankAppTheme
import com.example.jetbankapp.util.NoInternetDialog

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val internetConnectionFlow = NetworkStatusChecker.networkChecker(context)
            NoInternetDialog(internetConnectionFlow = internetConnectionFlow)

            TurkiyeBankDatabaseTheme {
                MainContent()
            }
        }
    }
}

@Composable
private fun MainContent() {
    Scaffold { contentPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()
            AppNavigation(navController)
        }
    }

}