package solutions.alva.of.son.tato

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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

        // Initialize Firebase Auth
        auth = Firebase.auth
        db = Firebase.firestore

        // Initialize usuarioActual


        val uID = auth.currentUser?.uid?: ""
        db.collection("users")
            .whereEqualTo("uid", uID)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.documents.isEmpty())
                    return@addOnSuccessListener
                val first = documents.documents[0]
                usuarioActual = Users(
                    uID,
                    first.getString("userName"),
                    first.getString("userType").toString(),
                    first.getString("userNum").toString(),
                    first.getString("userDep").toString(),
                    first.getString("techProf").toString()
                )
                //Hide UI elements
                hideUI()
            }


        binding.signOutImageView.setOnClickListener {
            signOut()
        }

        binding.techListArrowImage.setOnClickListener {
            techListView()
        }

        binding.profileArrowImage.setOnClickListener {
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

    private fun reviewView(){
        Log.i("tap tap","CHECKING RATE WINDOW")
        val intent = Intent(this, RatePopupActivity::class.java)
        this.startActivity(intent)
    }

    private fun signOut() {
        Firebase.auth.signOut()
        val intent = Intent(this,SignInActivity::class.java)
        startActivity(intent)
    }

    private fun hideUI(){
        val tipo = usuarioActual.userType
        if (tipo == "CLIENTE"){
            binding.techListArrowImage.setVisibility(View.VISIBLE)
        } else {
            binding.techListArrowImage.setVisibility(View.GONE)
        }

        if (tipo == "CLIENTE"){
            binding.imageTechList.setVisibility(View.VISIBLE)
        } else {
            binding.imageTechList.setVisibility(View.GONE)
        }

        if (tipo == "CLIENTE"){
            binding.techListTv.setVisibility(View.VISIBLE)
        } else {
            binding.techListTv.setVisibility(View.GONE)
        }
    }

}