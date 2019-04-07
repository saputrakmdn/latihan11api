package latihan.android.com.latihan11api.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.movie_list.view.*
import latihan.android.com.latihan11api.R
import latihan.android.com.latihan11api.model.Movie
import java.lang.StringBuilder

class MovieAdapter(val movies: ArrayList<Movie>):
RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(){
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies.get(position))
    }

    override fun getItemCount() = movies.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_list, parent, false)
        return MovieViewHolder(view)

    }
    class MovieViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{
        private var view : View = itemView
        private var movie : Movie? = null
        override fun onClick(p0: View?) {
            Toast.makeText(view.context, "Item Diklik", Toast.LENGTH_LONG).show()
        }
        init {
            itemView.setOnClickListener(this)
        }
        fun bind(movie: Movie){
            this.movie = movie
            val imageUrl = StringBuilder()
            imageUrl.append(view.context.getString(R.string.base_path_poster)).append(movie.posterPath)
            view.mvTitle.setText(movie.originalTitle)
            Glide.with(view.context).load(imageUrl.toString()).into(view.mvPoster)

        }
    }
}