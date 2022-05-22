package com.kmandina.testmobile.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kmandina.testmobile.R
import com.kmandina.testmobile.databinding.FragmentDashboardBinding
import com.kmandina.testmobile.utils.InjectorUtils

class DashboardFragment : Fragment() {

    private val viewmodel: DashboardViewModel by viewModels {
        InjectorUtils.provideDashboardViewModelFactory(requireContext())
    }

    private lateinit var uiBind: FragmentDashboardBinding

    private var dialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        uiBind = FragmentDashboardBinding.inflate(inflater, container, false)

        val builder = AlertDialog.Builder(requireContext())
        builder.setCancelable(false)
        builder.setView(R.layout.layout_loading_dialog)
        dialog = builder.create()

        context ?: return uiBind.root

        return uiBind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewmodel.updateMovie(requireContext(), dialog)
        subscribeUiRoute()

    }

    private fun subscribeUiRoute() {
        viewmodel.routes.observe(viewLifecycleOwner) { routes ->
            val adapter = MovieAdapter(routes)
            uiBind.movieList.adapter = adapter
            subscribeUiGrid(adapter)
        }
    }

    private fun subscribeUiGrid(adapter: MovieAdapter) {
        viewmodel.movies.observe(viewLifecycleOwner) { movies ->
            adapter.submitList(movies)
        }
    }
}