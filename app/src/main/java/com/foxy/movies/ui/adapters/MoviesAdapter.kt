package com.foxy.movies.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.foxy.movies.App
import com.foxy.movies.R
import com.foxy.movies.data.GenreWrapper
import com.foxy.movies.data.Movie
import com.foxy.movies.mvp.presenter.MoviesListPresenter
import com.squareup.picasso.Picasso

class MoviesAdapter(genres: List<GenreWrapper>, movies: List<Movie>, val presenter: MoviesListPresenter) :
    RecyclerView.Adapter<MoviesAdapter.GenericViewHolder>() {

    var movies: List<Movie> = movies
        set(movies) {
            field = movies
            notifyDataSetChanged()
        }

    var genres: List<GenreWrapper> = genres
        set(genres) {
            field = genres
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder {
        val view: View
        return when (viewType) {
            HEADER_GENRE_VIEW -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_list_header, parent, false)
                GenresHeaderHolder(view)
            }
            ITEM_GENRE_VIEW -> {
                view =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_genre, parent, false)
                GenreHolder(view)
            }
            HEADER_MOVIES_VIEW -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_list_header, parent, false)
                MoviesHeaderHolder(view)
            }
            else -> {
                // ITEM_MOVIE_VIEW
                view =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
                MoviesHolder(view)
            }
        }
    }

    override fun getItemCount(): Int {
        return when {
            genres.isEmpty() && movies.isEmpty() || genres.isNotEmpty() && movies.isEmpty() -> 0
            genres.isNotEmpty() && movies.isNotEmpty() -> genres.size + 1 + movies.size + 1
            genres.isEmpty() && movies.isNotEmpty() -> movies.size + 1
            else -> 0
        }
    }

    override fun onBindViewHolder(holder: GenericViewHolder, position: Int) {
        holder.bindView(position)
    }

    override fun getItemViewType(position: Int): Int {
        if (genres.isEmpty() && movies.isEmpty() || genres.isNotEmpty() && movies.isEmpty())
            return super.getItemViewType(position)

        if (genres.isNotEmpty() && movies.isNotEmpty()) {
            if (position == 0) return HEADER_GENRE_VIEW
            if (position == genres.size + 1) return HEADER_MOVIES_VIEW
            if (position > genres.size + 1) return ITEM_MOVIE_VIEW
            if (position > 0 && position <= genres.size) return ITEM_GENRE_VIEW
        }

        if (genres.isEmpty() && movies.isNotEmpty()) {
            return if (position == 0) HEADER_MOVIES_VIEW
            else ITEM_MOVIE_VIEW
        }
        return super.getItemViewType(position);
    }

    abstract class GenericViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        abstract fun bindView(position: Int)
    }

    private inner class GenresHeaderHolder(itemView: View) : GenericViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tv_header)

        override fun bindView(position: Int) {
            title.text = "Genres"
        }
    }

    private inner class GenreHolder(itemView: View) : GenericViewHolder(itemView) {
        val genre: TextView = itemView.findViewById(R.id.tv_genre)

        override fun bindView(position: Int) {
            genre.text = genres[position - 1].name

            when(genres[position - 1].selected) {
                true -> genre.setTextColor(App.get().resources.getColor(R.color.colorAccent))
                else -> genre.setTextColor(App.get().resources.getColor(android.R.color.black))
            }

            itemView.setOnClickListener { presenter.filterByGenre(genres[position - 1]) }
        }
    }

    private inner class MoviesHeaderHolder(itemView: View) : GenericViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tv_header)

        override fun bindView(position: Int) {
            title.text = "Movies"
        }
    }

    private inner class MoviesHolder(itemView: View) : GenericViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.img_movie)
        val name: TextView = itemView.findViewById(R.id.tv_movie_name)

        override fun bindView(position: Int) {
            val i = if (genres.isEmpty()) {
                position - 1
            } else position - genres.size - 2

            name.text = movies[i].localizedName

            Picasso.with(itemView.context)
                .load(movies[i].imgUrl)
                .placeholder(R.drawable.ic_error)
                .into(image)

            itemView.setOnClickListener { presenter.showDetails(movies[i]) }
        }
    }


    companion object {
        const val HEADER_GENRE_VIEW = 1
        const val ITEM_GENRE_VIEW = 2
        const val HEADER_MOVIES_VIEW = 3
        const val ITEM_MOVIE_VIEW = 4
    }


}