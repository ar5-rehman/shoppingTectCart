package com.clothing.shoppingcarts.ui.closet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.clothing.shoppingcarts.R
import com.clothing.shoppingcarts.base.delegate.viewBinding
import com.clothing.shoppingcarts.base.fragment.BaseFragment
import com.clothing.shoppingcarts.databinding.FragmentClosetBinding
import com.clothing.shoppingcarts.di.SharePreferencesManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ClosetFragment : BaseFragment(R.layout.fragment_closet) {

    private val binding: FragmentClosetBinding by viewBinding()

    @Inject
    lateinit var sharePreferencesManager: SharePreferencesManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}