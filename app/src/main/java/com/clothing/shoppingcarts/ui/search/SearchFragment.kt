package com.clothing.shoppingcarts.ui.search

import android.os.Bundle
import android.view.View
import com.clothing.shoppingcarts.R
import com.clothing.shoppingcarts.base.delegate.viewBinding
import com.clothing.shoppingcarts.base.fragment.BaseFragment
import com.clothing.shoppingcarts.databinding.FragmentSearchBinding
import com.clothing.shoppingcarts.di.SharePreferencesManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : BaseFragment(R.layout.fragment_search) {

    private val binding: FragmentSearchBinding by viewBinding()

    @Inject
    lateinit var sharePreferencesManager: SharePreferencesManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}