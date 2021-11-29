package solutions.alva.of.son.tato

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import solutions.alva.of.son.tato.databinding.ActivitySignInBinding

class MainActivity : AppCompatActivity() {

    //Firebase variables
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Variable initialization
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Initialize Firebase Auth
        auth = Firebase.auth
    }


    //Login FUNCTION
    fun 
}