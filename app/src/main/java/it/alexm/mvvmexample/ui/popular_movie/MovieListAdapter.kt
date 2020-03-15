package it.alexm.mvvmexample.ui.popular_movie

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import it.alexm.mvvmexample.BuildConfig
import it.alexm.mvvmexample.R
import it.alexm.mvvmexample.data.beans.MovieResult
import it.alexm.mvvmexample.data.repository.NetworkState
import it.alexm.mvvmexample.data.setVisible
import it.alexm.mvvmexample.ui.single_movie_details.SingleMovieActivity
import kotlinx.android.synthetic.main.activity_single_movie.view.*
import kotlinx.android.synthetic.main.layout_movie_item.view.*
import kotlinx.android.synthetic.main.network_state_item.view.*

class MovieListAdapter(private val context: Context) :
    PagedListAdapter<MovieResult, RecyclerView.ViewHolder>(MovieDiffCallback()) {

    private var networkstate: NetworkState? = null

    companion object {
        private const val MOVIE_TYPE = 1
        private const val NETWORK_TYPE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        LayoutInflater.from(parent.context).let { layoutInflater ->
            if (viewType == MOVIE_TYPE) MovieItemViewHolder(
                layoutInflater.inflate(R.layout.layout_movie_item, parent, false)
            ) else NetworkstateViewHolder(
                layoutInflater.inflate(R.layout.network_state_item, parent, false)
            )
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieItemViewHolder -> holder.bind(getItem(position), context)
            is NetworkstateViewHolder -> holder.bind(networkstate)
            else -> {
                //nothing
            }
        }
    }

    private fun hasExtraRow() = networkstate != null && !(networkstate?.isSuccess() ?: false)

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) NETWORK_TYPE
        else MOVIE_TYPE
    }

    class MovieDiffCallback : DiffUtil.ItemCallback<MovieResult>() {
        override fun areItemsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean =
            oldItem == newItem
    }

    private inner class MovieItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: MovieResult?, context: Context) {
            itemView.movie_title.text = movie?.title
            itemView.item_movie_release_year.text = movie?.releaseDate

            Glide.with(itemView.context)
                .load(BuildConfig.POSTER_HOST + movie?.posterPath)
                .into(itemView.item_image)

            itemView.setOnClickListener {
                context.startActivity(
                    Intent(context, SingleMovieActivity::class.java).apply {
                        putExtra("id", movie?.id)
                    }
                )
            }
        }
    }

    private inner class NetworkstateViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(networkstate: NetworkState?) {
            itemView.progress_bar_item.setVisible(networkstate?.isLoading())

            itemView.txt_error_item.setVisible(networkstate?.isError()) {
                it.text = networkstate?.message
            }
        }
    }

    fun setNetworkState(newNetworkState: NetworkState) {
        val previus = networkstate
        val hadExtraRow = hasExtraRow()
        networkstate = newNetworkState
        val hasExtraRow = hasExtraRow()

        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previus != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }
}