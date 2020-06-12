package com.learn.android.khmer24clone.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.navigation.fragment.findNavController
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.learn.android.khmer24clone.R
import com.learn.android.khmer24clone.common.helper.printLog
import com.learn.android.khmer24clone.ui.base.BaseFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : BaseFragment(R.layout.fragment_login) {

    lateinit var callbackManager: CallbackManager
    private lateinit var auth: FirebaseAuth

    override fun onResume() {
        super.onResume()

        requireActivity().run {
            navView.isGone = true
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
            } else {
                // If sign in fails, display a message to the user.
                printLog("signInWithCredential:failure ${task.exception}")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
}