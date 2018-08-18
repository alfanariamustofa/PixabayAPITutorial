package id.rysmedia.grabpixabay.models

import com.google.gson.annotations.SerializedName

data class RArray(

		@field:SerializedName("hits")
	val hits: List<Gambar>? = null,

		@field:SerializedName("totalHits")
	val totalHits: Int = 0,

		@field:SerializedName("total")
	val total: Int? = null
)