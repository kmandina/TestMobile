package com.kmandina.testmobile.ui.dashboard

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.kmandina.testmobile.data.model.MovieMedia
import com.kmandina.testmobile.data.model.RouteSize
import com.kmandina.testmobile.databinding.ItemMovieBinding

class MovieAdapter(private val routes: List<RouteSize>) : ListAdapter<MovieMedia, RecyclerView.ViewHolder>(
    MovieDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return MovieViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movie = getItem(position)
        (holder as MovieViewHolder).bind(movie)
        if(movie.medias.isNotEmpty()){
            for(media in movie.medias){
                if(media.code == "poster"){
                    if(routes.isNotEmpty()){

                            for(route in routes){

                                if(route.route.code == "poster"){

                                    if(route.sizes.isNotEmpty()){

                                        for(size in route.sizes){

                                            if(size.medium != ""){

                                                Glide.with(holder.binding.root.context)
                                                    .load(size.medium + media.resource)
                                                    .transition(DrawableTransitionOptions.withCrossFade())
                                                    .into(holder.binding.movieImage)

                                                break
                                            }

                                        }

                                    }
                                    break
                                }

                            }

                        }
                }
            }
        }
    }

    class MovieViewHolder(
        var binding: ItemMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {

            binding.setClickListener {
                binding.movie?.let { trip ->
                    navigateToMovie(trip, it)
                }
            }

        }

        private fun navigateToMovie(
            movie: MovieMedia,
            view: View
        ) {
            val direction =
                DashboardFragmentDirections.actionNavigationDashboardToDetailFragment(
                    movie.movie.id
                )
            view.findNavController().navigate(direction)
        }

        fun bind(item: MovieMedia) {
            binding.apply {
                movie = item
                executePendingBindings()
            }

        }
    }

}

class MovieDiffCallback : DiffUtil.ItemCallback<MovieMedia>() {

    override fun areItemsTheSame(oldItem: MovieMedia, newItem: MovieMedia): Boolean {
        return oldItem.movie.id == newItem.movie.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: MovieMedia, newItem: MovieMedia): Boolean {
        return oldItem == newItem
    }
}