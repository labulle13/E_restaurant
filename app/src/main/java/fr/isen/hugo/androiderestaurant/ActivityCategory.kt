package fr.isen.hugo.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.hugo.androiderestaurant.ActivityCategory.Companion.ITEM_KEY
import fr.isen.hugo.androiderestaurant.databinding.ActivityCategoryBinding
import fr.isen.hugo.androiderestaurant.model.DataResult
import org.json.JSONObject
import java.nio.charset.Charset

class ActivityCategory : AppCompatActivity() {
    private lateinit var binding : ActivityCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCategoryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val actionBar = supportActionBar
        val categoryName = intent.getStringExtra("category")
        actionBar!!.title = categoryName
        binding.category.text = categoryName

        val items = resources.getStringArray(R.array.items_list).toList() as ArrayList

        binding.recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        binding.recyclerView.adapter = CategoryAdapter(items){
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra(ITEM_KEY, it)
            startActivity(intent)
        }

        getDataFromApi(intent.getStringExtra("Category")?:"")

    }
    companion object {
        val ITEM_KEY = "item"
    }
}

private fun getDataFromApi(category : String){
    val queue = Volley.newRequestQueue(this)
    val url = "http://test.api.catering.bluecodegames.com/menu"
    val json = JSONObject()
    json.put("id_shop", "1")
    json.toString()
    val requestBody = json.toString()

    val stringReq : StringRequest =
        object : StringRequest(Method.POST, url,
            { response ->
                // response
                val strResp = response.toString()
                Log.d("API", strResp)
                val dataResult = Gson().fromJson(strResp, DataResult::class.java)

                val items = dataResult.data.firstOrNull{ it.name_fr == category }?.items ?: arrayListOf()
                binding.itemsList.adapter = CategoryAdapter(items) {
                    val intent = Intent(this, DetailsActivity::class.java)
                    intent.putExtra(ITEM_KEY, it)// Recupere et stocke dans ITEM_KEY
                    startActivity(intent)
                }

            },
            Response.ErrorListener { error ->
                Log.d("API", "error => $error")
            }
        ){
            override fun getBody(): ByteArray {
                return requestBody.toByteArray(Charset.defaultCharset())
            }
        }
    queue.add(stringReq)
}