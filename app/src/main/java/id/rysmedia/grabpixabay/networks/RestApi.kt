package id.rysmedia.cozmeedstore.networks

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestApi {
    companion object {
        private var retrofit: Retrofit? = null
        private val BASE_URL = "https://pixabay.com/"

        fun service(): Endpoint? {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
            }
            return retrofit?.create(Endpoint::class.java)
        }

    }
}
