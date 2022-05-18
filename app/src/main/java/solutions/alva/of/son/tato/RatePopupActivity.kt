package solutions.alva.of.son.tato

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import solutions.alva.of.son.tato.databinding.ActivityMainBinding

class RatePopupActivity : AppCompatActivity() {

    lateinit var ratingBar : RatingBar
    lateinit var sendRating : Button
    lateinit var reviewsPerTech : TextView
    private lateinit var binding: RatePopupActivity
//    private lateinit var binding: RatePopupActivity

    //count numbers of clicks
    var count = 0
    var techAmountReviews = count

//    val user = techList[position]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rate_popup)


        val homeImageView = findViewById(R.id.homeImageView) as ImageView

        homeImageView.setOnClickListener {
            val intent = Intent(this, TechListingActivity::class.java)
            this.startActivity(intent)
        }

//        ratingBar = findViewById(R.id.ratingBar) as RatingBar
        sendRating = findViewById(R.id.rateTechBtn) as Button
        reviewsPerTech = findViewById(R.id.rateCounterTv) as TextView


        sendRating.setOnClickListener {
            onTapRate()
            Toast.makeText(baseContext, "Enviando evaluacion...", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, TechListingActivity::class.java)
            this.startActivity(intent)
        }

    }

    fun onTapRate() {

        // count increaser
        techAmountReviews++

        reviewsPerTech.text = "Reseñas totales: $techAmountReviews"
    }
}