package solutions.alva.of.son.tato

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RatingBar
import android.widget.Toast
import solutions.alva.of.son.tato.databinding.ActivityMainBinding

class RatePopupActivity : AppCompatActivity() {

    lateinit var ratingBar : RatingBar
    lateinit var sendRating : Button
    private lateinit var binding: RatePopupActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rate_popup)


        ratingBar = findViewById(R.id.ratingBar) as RatingBar
        sendRating = findViewById(R.id.rateTechBtn) as Button

        sendRating.setOnClickListener {
            Toast.makeText(this, "Enviando evaluacion...", Toast.LENGTH_SHORT).show()
        }


    }
}