package fr.isen.hugo.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.TextView
import android.widget.Toast

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val textClickable = findViewById<TextView>(R.id.Desserts)
        textClickable.setOnClickListener {

            val myIntent = Intent(this, Menu::class.java)
            startActivity(myIntent)

            textClickable.movementMethod = LinkMovementMethod.getInstance();



            Toast.makeText(
                this@MainActivity2,
                "Redirection vers la page d'accueil",
                Toast.LENGTH_SHORT
            ).show()


            val textClickable = findViewById<TextView>(R.id.Plats)
            textClickable.setOnClickListener {

                val myIntent = Intent(this, MainActivity::class.java)
                startActivity(myIntent)

                textClickable.movementMethod = LinkMovementMethod.getInstance();
            }
        }
    }
}