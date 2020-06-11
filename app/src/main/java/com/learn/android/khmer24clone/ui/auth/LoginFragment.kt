package com.learn.android.khmer24clone.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.navigation.fragment.findNavController
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.LoginStatusCallback
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.learn.android.khmer24clone.R
import com.learn.android.khmer24clone.common.helper.printLog
import com.learn.android.khmer24clone.ui.base.BaseFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : BaseFragment(R.layout.fragment_login) {

    lateinit var facebookLoginCallback: LoginStatusCallback
    lateinit var callbackManager: CallbackManager

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
        facebookLoginButton.fragment = this
        facebookLoginButton.setPermissions("email")
//        facebookLoginCallback = object: LoginStatusCallback {
//            override fun onFailure() {
//                printLog("LoginStatusCallback > onFailure")
//            }
//
//            override fun onError(exception: Exception?) {
//                printLog("LoginStatusCallback > onError: ${exception?.localizedMessage}")
//            }
//
//            override fun onCompleted(accessToken: AccessToken?) {
//                printLog("LoginStatusCallback > onCompleted: ${accessToken?.token}")
//            }
//        }
        facebookLoginButton.registerCallback(callbackManager, object: FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                printLog("LoginManager > onSuccess: ${result}")
            }

            override fun onCancel() {
                printLog("LoginManager > onCancel")
            }

            override fun onError(error: FacebookException?) {
                printLog("LoginManager > onError: ${error}")
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
}