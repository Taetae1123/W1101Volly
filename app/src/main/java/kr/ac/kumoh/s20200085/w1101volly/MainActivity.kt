package kr.ac.kumoh.s20200085.w1101volly

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.MessageQueue
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kr.ac.kumoh.s20200085.w1101volly.databinding.ActivityMainBinding
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var queue: RequestQueue
    private val list = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        queue = Volley.newRequestQueue(application)

        binding.btnConnect.setOnClickListener{
            val url = "https://yts.torrentbay.to/api/v2/list_movies.json?sort=rating&limit=30"
            val request = JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                {
                    parseJson(it)
//                    Toast.makeText(application,
//                        list.toString().replace(",", "\n")
//                            .replace("[\\[\\]]".toRegex(), ""),//정규식
//                            //.replace("]", ""),
//                        Toast.LENGTH_LONG).show()
                    binding.listMovies.adapter = ArrayAdapter<String>(application, android.R.layout.simple_list_item_1,list)
                },
                {
                    Toast.makeText(application, it.toString(), Toast.LENGTH_LONG).show()
                }
            )
            queue.add(request)

        }
    }
    private fun parseJson(obj: JSONObject){
        val data = obj.getJSONObject("data")
        val movies = data.getJSONArray("movies")
        for(i in 0 until movies.length()){
            val movie: JSONObject = movies[i] as JSONObject
            val title = movie.getString("title_long")
            //list.add("-$title")
            list.add(title)
        }
    }
}

//{}: 객체=>JsonObject
//[]: array=>JsonArray
//?: null 일수도 있다

//volly에서는 Json
//Json에서는 JSON

//array는 숫자를 정해주는거, List는 안 정해줘도 됨