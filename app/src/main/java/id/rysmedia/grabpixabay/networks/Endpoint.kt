package id.rysmedia.cozmeedstore.networks

import id.rysmedia.grabpixabay.models.RArray
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Endpoint {

    @GET("api/")
    fun getImage(
            @Query("key") key: String,
            @Query("q") q: String,
            @Query("image_type") image_type: String,
            @Query("pretty") pretty: String
    ): Call<RArray>
}