package com.kmandina.testmobile.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kmandina.testmobile.R
import com.kmandina.testmobile.databinding.FragmentProfileBinding
import com.kmandina.testmobile.utils.InjectorUtils

class ProfileFragment : Fragment() {

    private val viewmodelU: ProfileViewModel by viewModels {
        InjectorUtils.provideUserViewModelFactory(requireContext())
    }

    private lateinit var uiBind: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        uiBind = DataBindingUtil.inflate<FragmentProfileBinding>(
            inflater, R.layout.fragment_profile, container, false
        ).apply {
            viewmodel = viewmodelU
            lifecycleOwner = viewLifecycleOwner
        }
        setHasOptionsMenu(true)

        context ?: return uiBind.root

        return uiBind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewmodelU.update(requireContext())
    }
}