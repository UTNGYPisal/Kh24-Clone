package com.learn.android.khmer24clone

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.learn.android.khmer24clone.model.api.UnhandledResult
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val viewModel by inject<MainViewModel>()
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        window.setBackgroundDrawable(resources.getDrawable(R.color.colorWhite))

        FirebaseApp.initializeApp(this)

        val navView: BottomNavigationView = findViewById(R.id.navView)
        val navController = findNavController(R.id.navHostFragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_notification,
                R.id.navigation_post,
                R.id.navigation_chat,
                R.id.navigation_account
            ))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onStart() {
        super.onStart()
        auth = FirebaseAuth.getInstance()
        refreshToken()
    }

    private fun refreshToken(){
        auth.currentUser?.run {
            this.getIdToken(true).addOnCompleteListener { idTokenTask ->
                idTokenTask.result?.token?.run {
                    viewModel.authenticate(this).observe(this@MainActivity, Observer {
                        when (it) {
                            is UnhandledResult.Success -> {
                                it.data?.run {
                                    viewModel.handleServerLoginSuccess(it.data)
                                }
                            }
                        }
                    })
                }
            }
        }
    }
}
