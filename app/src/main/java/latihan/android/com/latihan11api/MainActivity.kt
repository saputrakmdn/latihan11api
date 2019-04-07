package latihan.android.com.latihan11api

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.widget.GridLayout
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import latihan.android.com.latihan11api.adapter.MovieAdapter
import latihan.android.com.latihan11api.model.Movie
import latihan.android.com.latihan11api.model.MovieResponse
import latihan.android.com.latihan11api.service.ApiClient
import latihan.android.com.latihan11api.service.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder
import java.nio.channels.spi.AbstractSelectionKey


class MainActivity : AppCompatActivity() {
    private val TAG : String = MainActivity::class.java.canonicalName
    private lateinit var movies : ArrayList<Movie>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        rvMovies.layoutManager = GridLayoutManager(applicationContext, 2)
        val apiKey = getString(R.string.api_key)
        val apiInterface : ApiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        getLatestMovie(apiInterface, apiKey)
        getPopularMovies(apiInterface, apiKey)
        collapseImage.setOnClickListener {
            Toast.makeText(applicationContext, "poster gede", Toast.LENGTH_SHORT).show()
        }
    }
    fun getPopularMovies(apiInterface: ApiInterface, apiKey: String){
        val call : Call<MovieResponse> = apiInterface.getPopularMovie(apiKey)
        call.enqueue(object : Callback<MovieResponse>{
            override fun onFailure(call: Call<MovieResponse>, t: Throwable?) {
               Log.d("$TAG", "Gagal fetch popular movie")
            }

            override fun onResponse(call: Call<MovieResponse>?, response: Response<MovieResponse>?) {
                movies = response!!.body()!!.results
                Log.d("$TAG", "Movie Size ${movies.size}")
                rvMovies.adapter = MovieAdapter(movies)
            }
        })
    }
    fun getLatestMovie(apiInterface: ApiInterface, apiKey: String) : Movie?{
        var movie : Movie? = null
        val call : Call<Movie> = apiInterface.getMovieLatest(apiKey)
        call.enqueue(object : Callback<Movie>{
            override fun onFailure(call: Call<Movie>?, t: Throwable?) {
                Log.d("$TAG", "Gagal fetch popular movie")
            }

            override fun onResponse(call: Call<Movie>?, response: Response<Movie>?) {
                if (response != null){
                    var originalTitle : String? = response.body()?.originalTitle
                    var posterPath : String? = response.body()?.posterPath
                    collapseToolbar.title = originalTitle
                    if(posterPath == null){
                        collapseImage.setImageResource(R.drawable.icon_no_image)
                    }else{
                        val imageUrl = StringBuilder()
                        imageUrl.append(getString(R.string.base_path_poster)).append(posterPath)
                        Glide.with(applicationContext).load(imageUrl.toString()).into(collapseImage)
                    }
                }
            }
        })
        return movie
    }
}
