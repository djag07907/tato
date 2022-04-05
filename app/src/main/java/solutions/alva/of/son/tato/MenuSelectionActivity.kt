package solutions.alva.of.son.tato

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import solutions.alva.of.son.tato.classes.Users
import solutions.alva.of.son.tato.databinding.ActivityMainBinding
import solutions.alva.of.son.tato.databinding.ActivityMenuSelectionBinding

class MenuSelectionActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMenuSelectionBinding
    private val fileResult = 1
    private lateinit var db : FirebaseFirestore
    private lateinit var usuarioActual : Users

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Variable initialization
        binding = ActivityMenuSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setContentView(R.layout.activity_menu_selection)

//        val techDisplayBtn = findViewById<Button>(R.id.techDisplayBtn)
//        val profileBtn = findViewById<Button>(R.id.profileBtn)


        binding.techDisplayBtn.setOnClickListener {
            techListView()
        }

        binding.profileBtn.setOnClickListener {
            profileView()
        }

    }

    private fun profileView(){
        Log.i("tap tap","TYPING PROF VIEW HERE")
        val intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)
    }

    private fun techListView(){
        Log.i("tap tap","TYPING TECH LIST HERE")
        val intent = Intent(this, TechListingActivity::class.java)
        this.startActivity(intent)
    }

}