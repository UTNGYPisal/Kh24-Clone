package com.learn.android.khmer24clone.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.learn.android.khmer24clone.R
import com.learn.android.khmer24clone.common.helper.printLog
import com.learn.android.khmer24clone.model.api.BaseResponse
import com.learn.android.khmer24clone.model.api.UnhandledResult
import com.learn.android.khmer24clone.model.entity.User
import com.learn.android.khmer24clone.ui.base.BaseFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.android.ext.android.inject


class LoginFragment : BaseFragment(R.layout.fragment_login) {

    lateinit var callbackManager: CallbackManager
    private lateinit var auth: FirebaseAuth
    private val viewModel by inject<LoginViewModel>()

    override fun onResume() {
        super.onResume()

        requireActivity().run {
            bottomNavView.isGone = true
            toolbar.isGone = true
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    private fun initListener(){
        iBtnClose.setOnClickListener {
            findNavController().setGraph(R.navigation.mobile_navigation)
        }

        btnFacebookCustom.setOnClickListener {
            facebookLoginButton.performClick()
        }
        initFacebookCallbacks()
    }

    private fun initFacebookCallbacks(){

        callbackManager = CallbackManager.Factory.create()
        auth = FirebaseAuth.getInstance()

        facebookLoginButton.fragment = this
        facebookLoginButton.setPermissions("email")
        facebookLoginButton.registerCallback(callbackManager, object: FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                printLog("LoginManager > onSuccess: ${result?.accessToken?.token}")
                if (result?.accessToken != null) {
                    handleFacebookAccessToken(result.accessToken)
                } else {
                    printLog("LoginManager > onSuccess but error")
                }
            }

            override fun onCancel() {
                printLog("LoginManager > onCancel")
            }

            override fun onError(error: FacebookException?) {
                printLog("LoginManager > onError: ${error}")
            }
        })
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        printLog("handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                printLog("signInWithCredential:success")
                val user = auth.currentUser
                if (user != null) {
                    user.getIdToken(true).addOnCompleteListener { idTokenResult ->
                        printLog("Id Token: ${idTokenResult.result?.token}")

                        val idToken = idTokenResult.result?.token
                        val photoUrl = user.photoUrl.toString() + "?width=512&height=512"

                        if (!idToken.isNullOrEmpty()) {
                            postAuthenticate(idToken, user.displayName, photoUrl)
                        } else {
                            printLog("signInWithCredential:failure no idToken ${idTokenResult.exception}")
                        }
                    }
                } else {
                    printLog("signInWithCredential:missing user data ${task.exception}")
                }
            } else {
                // If sign in fails, display a message to the user.
                printLog("signInWithCredential:failure ${task.exception}")
            }
        }
    }

    /**
     * Call to our API to authenticate (login | register) on server
     * @param idToken: Firebase auth idToken
     * @param name: optional when login, and mandatory when register
     */
    private fun postAuthenticate(
        idToken: String,
        name: String? = null,
        imageUrl: String? = null
    ) {
        mainViewModel.authenticate(idToken, name, imageUrl).observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                UnhandledResult.Loading -> {
                    progressBar.isVisible = true
                }
                is UnhandledResult.Error -> {
                    progressBar.isGone = true
                }
                is UnhandledResult.Success -> {
                    progressBar.isGone = true
                    if (response.data?.result != null && !response.data.token.isNullOrEmpty()) {
                        handleServerLoginSuccess(response.data)
                    } else {
                        printLog("Login at server failure: ${response.data?.message}")
                    }
                }
            }
        })
    }

    private fun handleServerLoginSuccess(response: BaseResponse<User>) {
        printLog("Login at server success: ${response.result}")
        printLog("Token: ${response.token}")

        User.current = response.result!!.copy(token = response.token)
        mainViewModel.isLoggedIn.value = true
        findNavController().navigateUp()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
}