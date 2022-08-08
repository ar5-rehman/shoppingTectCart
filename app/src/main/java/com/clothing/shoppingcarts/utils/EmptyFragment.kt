package com.clothing.shoppingcarts.utils

import android.os.Bundle
import android.view.View
import com.clothing.shoppingcarts.R
import com.clothing.shoppingcarts.base.delegate.viewBinding
import com.clothing.shoppingcarts.base.fragment.BaseFragment
import com.clothing.shoppingcarts.databinding.FragmentEmptyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmptyFragment : BaseFragment(R.layout.fragment_empty) {

    private val binding: FragmentEmptyBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}