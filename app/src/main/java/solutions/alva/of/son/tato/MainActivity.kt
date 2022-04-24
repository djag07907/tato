package solutions.alva.of.son.tato

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import solutions.alva.of.son.tato.classes.Users
import solutions.alva.of.son.tato.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //Firebase variables
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    private val fileResult = 1
    private lateinit var db : FirebaseFirestore
    private lateinit var usuarioActual : Users


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // OnStart functions

        //Variable initialization
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Initialize Firebase Auth
        auth = Firebase.auth

        binding.homeImageView.setOnClickListener {
            val intent = Intent(this, MenuSelectionActivity::class.java)
            this.startActivity(intent)
        }

        binding.signOutImageView.setOnClickListener {
            signOut()
        }
        binding.updateProfileAppCompatButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val userNum = binding.numberEditText.text.toString()
            updateProfile(name, userNum)

        }

        binding.profileImageView.setOnClickListener {
            fileManager()
        }

        binding.updatePasswordTextView.setOnClickListener {
            val intent = Intent(this,UpdatePasswordActivity::class.java)
            startActivity(intent)
        }

        binding.deleteAccountTextView.setOnClickListener {
            val intent = Intent(this,DeleteAccountActivity::class.java)
            startActivity(intent)
        }


        db = Firebase.firestore

        //remove update user if not working
        val uID = auth.currentUser?.uid?: ""

        Log.w("DEBUG HEEEEREEEEEEEE",  uID)

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
                    first.getString("techProf").toString(),
                    first.getString("callPref").toString()
                    )


                binding.nameTextView.text = usuarioActual.userName
                //changed usertype to userprof
                binding.usertypeTextview.text = usuarioActual.techProf
                //
                binding.userNumberTextview.text = usuarioActual.userNum
                binding.userCallPrefTv.text = usuarioActual.callPref
                binding.userDepTv.text = usuarioActual.userDep
                hideCallPref()
                hideUserType()
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }

        updateUI()

//        binding.testbtn.setOnClickListener {
//            test()
//        }
    }

    private fun fileManager() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, fileResult)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == fileResult) {
            if (resultCode == RESULT_OK && data != null) {
                val uri = data.data

                uri?.let { imageUpload(it) }

            }
        }
    }

    private fun imageUpload(mUri: Uri) {

        val user = auth.currentUser
        val folder: StorageReference = FirebaseStorage.getInstance().reference.child("Users")
        val fileName: StorageReference = folder.child("img"+user!!.uid)

        fileName.putFile(mUri).addOnSuccessListener {
            fileName.downloadUrl.addOnSuccessListener { uri ->

                val profileUpdates = userProfileChangeRequest {
                    photoUri = Uri.parse(uri.toString())
                }

                user.updateProfile(profileUpdates)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Se realizaron los cambios correctamente.",
                                Toast.LENGTH_SHORT).show()
                            updateUI()
                        }
                    }
            }
        }.addOnFailureListener {
            Log.i("TAG", "file upload error")
        }
    }

    private fun updateProfile(name: String, userNum: String) {
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

    private fun test(){
        val intent = Intent(this, MenuSelectionActivity::class.java)
        this.startActivity(intent)
    }

    private fun signOut() {
        Firebase.auth.signOut()
        val intent = Intent(this,SignInActivity::class.java)
        startActivity(intent)
    }

    // Set functions to firestore data instead of firebase only
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

    private fun hideCallPref(){
        val tipo = usuarioActual.userType
        if (tipo == "CLIENTE"){
            binding.userCallPrefTv.setVisibility(View.GONE)
        } else {
            binding.userCallPrefTv.setVisibility(View.VISIBLE)
        }
    }

    private fun hideUserType(){
        val tipo = usuarioActual.userType
        if (tipo == "CLIENTE"){
            binding.usertypeTextview.setVisibility(View.GONE)
        } else {
            binding.usertypeTextview.setVisibility(View.VISIBLE)
        }
    }


}