package com.contacts.app.ui.splash

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import app.common.presentation.ui.frag.BaseFrag
import com.contacts.app.R
import com.contacts.app.databinding.FragmentSplashBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Sha on 7/28/20.
 */

class SplashFrag : BaseFrag<FragmentSplashBinding, SplashViewModel>() {
    override val vm: SplashViewModel by viewModel()

    override fun inflate(inflater: LayoutInflater, container: ViewGroup?): FragmentSplashBinding {
        return FragmentSplashBinding.inflate(inflater, container, false)
    }

    override fun onResume() {
        super.onResume()
        setupFlow()
    }

    private fun setupFlow() {
        Handler(Looper.getMainLooper()).postDelayed({
            showHome()
        }, 2000)
    }

    private fun showHome() {
        findNavController().navigate(R.id.action_splashFrag_to_homeFrag)
    }

}
