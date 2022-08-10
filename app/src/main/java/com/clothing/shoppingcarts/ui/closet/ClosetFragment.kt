package com.clothing.shoppingcarts.ui.closet

import android.content.Intent
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
import com.clothing.shoppingcarts.ui.unity.UnityActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ClosetFragment : BaseFragment(R.layout.fragment_closet) {

    private val binding: FragmentClosetBinding by viewBinding()

    @Inject
    lateinit var sharePreferencesManager: SharePreferencesManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var vfrString: String = ("").toString()
        val intent = Intent(requireActivity(), UnityActivity::class.java)
       /* vfrString = vfrString + item!!.id.toString() + ";" + item!!.defaultColorway.id.toString() + ";"
        intent.putExtra("vfrString", vfrString)*/
        intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
        startActivity(intent)

    }
}