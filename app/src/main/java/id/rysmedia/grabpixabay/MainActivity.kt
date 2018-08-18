package id.rysmedia.grabpixabay

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.gson.Gson
import id.rysmedia.cozmeedstore.networks.RestApi
import id.rysmedia.grabpixabay.models.Gambar
import id.rysmedia.grabpixabay.models.RArray
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    private var callImage: Call<RArray>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        mSectionsPagerAdapter = SectionsPagerAdapter(this,supportFragmentManager)
        container.adapter = mSectionsPagerAdapter
        callImage = RestApi.service()?.getImage(
                "9794852-57d205c6a8d68d1c687983435",
                "yellow+flowers",
                "photo",
                "true")
        callImage?.enqueue(object : Callback<RArray> {
            override fun onFailure(call: Call<RArray>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<RArray>?, response: Response<RArray>?) {
                if (response?.isSuccessful ?: false) {
                    try {
                        val data = response?.body()
                        Log.d("RESPONSE", Gson().toJson(data))
                        if (data?.hits != null && data.hits.size > 0) {
                            hits.text = data.totalHits.toString()
                            total.text = data.hits.size.toString()
                            mSectionsPagerAdapter?.addData(data.hits)
                        } else {
                            showMessage("Tidak ada gambar yang bisa ditampilkan")
                        }
                    } catch (e: Exception) {
                        showMessage("Parsing data json gagal")
                    }
                } else {
                    showMessage("Response error with code " + response?.code())
                }
            }
        })
    }

    private fun showMessage(message: String) {
        Snackbar.make(main_content, message, Snackbar.LENGTH_LONG).show()
    }

    inner class SectionsPagerAdapter(private val activity: MainActivity, fm: FragmentManager) : FragmentPagerAdapter(fm) {

        private var data: List<Gambar> = arrayListOf()

        fun addData(data: List<Gambar>) {
            this.data = data
            notifyDataSetChanged()
        }

        override fun getItem(position: Int) : Fragment{
            activity.supportActionBar?.title = data.get(position).tags
            return PlaceholderFragment.newInstance(data.get(position))
        }
        override fun getCount() = data.size
    }

    class PlaceholderFragment : Fragment() {

        var imgUrl: String = ""
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.fragment_main, container, false)
            Glide.with(context!!)
                    .load(imgUrl)
                    .thumbnail(0.1f)
                    .into(rootView.image)
            return rootView
        }

        companion object {

            fun newInstance(gambar: Gambar): PlaceholderFragment {
                val fragment = PlaceholderFragment()
                fragment.imgUrl = gambar.largeImageURL!!
                return fragment
            }
        }
    }
}
