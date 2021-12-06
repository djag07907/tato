package solutions.alva.of.son.tato

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import solutions.alva.of.son.tato.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //Firebase variables
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Variable initialization
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Initialize Firebase Auth
        auth = Firebase.auth

//        binding.logoutBtn.setOnClickListener {
//            signOut()
//        }
        binding.updateProfileAppCompatButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            updateProfile(name)
        }

        updateUI()
    }


    private fun updateProfile(name : String) {
        val user = auth.currentUser

        val profileUpdates = userProfileChangeRequest{
            displayName = name
        }

        user!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(baseContext, "Se actualizo exitosamente",
                        Toast.LENGTH_SHORT).show()
                    updateUI()
                }
            }
    }


    private fun signOut() {
        Firebase.auth.signOut()
        val intent = Intent(this,SignInActivity::class.java)
        startActivity(intent)
    }

    private fun updateUI(){
        val user = auth.currentUser

        if (user != null){
            binding.emailTextView.text = user.email
            binding.nameTextView.text = user.displayName
            binding.nameEditText.setText(user.displayName)

            //GLIDE
            Glide
                .with(this)
                .load(user.photoUrl)
                .centerCrop()
                .placeholder(R.drawable.profile_photo)
                .into(binding.profileImageView)
            Glide
                .with(this)
                .load(user.photoUrl)
                .centerCrop()
                .placeholder(R.drawable.profile_photo)
                .into(binding.bgProfileImageView)
        }
    }

}