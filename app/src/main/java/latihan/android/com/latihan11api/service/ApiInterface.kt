package latihan.android.com.latihan11api.service

import latihan.android.com.latihan11api.model.Movie
import latihan.android.com.latihan11api.model.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("movie/latest")
    fun getMovieLatest(@Query("api_key") apikey : String): Call<Movie>
    @GET("movie/popular")
    fun getPopularMovie(@Query("api_key")apikey: String): Call<MovieResponse>
}