package fr.isen.hugo.androiderestaurant

import android.content.ClipData
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.hugo.androiderestaurant.ActivityCategory.Companion.ITEM_KEY
import fr.isen.hugo.androiderestaurant.databinding.ActivityDetailsBinding
import fr.isen.hugo.androiderestaurant.model.Item

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val item = intent.getSerializableExtra(ITEM_KEY) as Item
        binding.detailTitle.text = item.name_fr

        val carouselAdaptater = CarousselAdaptater(this, item.images)
        binding.detailSlider.adaptater = carouselAdaptater



        setContentView(R.layout.activity_details)
    }

}








