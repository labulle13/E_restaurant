package fr.isen.hugo.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import fr.isen.hugo.androiderestaurant.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val actionBar = supportActionBar
        actionBar!!.title = "Accueil" // titre accueil


        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.entreeText.setOnClickListener {
            goToCategory(getString(R.string.text_entree))
        }

        binding.platText.setOnClickListener {
            goToCategory(getString(R.string.text_plats))
        }


        binding.dessertText.setOnClickListener {
            goToCategory(getString(R.string.text_desserts))
        }
    }

    private fun goToCategory(category: String) {
        val myIntent = Intent(this, ActivityCategory::class.java)
        Toast.makeText(
            this@HomeActivity,
            category,
            Toast.LENGTH_SHORT
        ).show()
        myIntent.putExtra("category", category)
        startActivity(myIntent)
    }


}
