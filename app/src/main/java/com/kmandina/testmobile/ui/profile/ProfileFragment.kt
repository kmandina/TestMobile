package com.kmandina.testmobile.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kmandina.testmobile.R
import com.kmandina.testmobile.databinding.FragmentProfileBinding
import com.kmandina.testmobile.utils.InjectorUtils
import com.kmandina.testmobile.utils.MyUtils

class ProfileFragment : Fragment() {

    private val viewmodelU: ProfileViewModel by viewModels {
        InjectorUtils.provideUserViewModelFactory(requireContext())
    }

    private lateinit var uiBind: FragmentProfileBinding

    private var dialog: AlertDialog? = null

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

        val builder = AlertDialog.Builder(requireContext())
        builder.setCancelable(false)
        builder.setView(R.layout.layout_loading_dialog)
        dialog = builder.create()

        context ?: return uiBind.root

        return uiBind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewmodelU.update(requireContext(), dialog)

        uiBind.checkAction.setOnClickListener {
            if (MyUtils.isNetworkConnected(requireContext())) {
                viewmodelU.trx(requireContext(), dialog, uiBind.cardNumberInput.text.toString())
            }else{
                AlertDialog.Builder(requireContext())
                    .setTitle("Notification")
                    .setMessage("Connection Error")
                    .setPositiveButton("Accept") { _, _ ->  }
                    .show()
            }
        }
    }
}