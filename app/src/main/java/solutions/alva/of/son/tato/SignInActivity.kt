package solutions.alva.of.son.tato

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import solutions.alva.of.son.tato.classes.Users
import solutions.alva.of.son.tato.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {

    //Firebase variables
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignInBinding
//    private val fileResult = 1
    private lateinit var db : FirebaseFirestore
    private lateinit var usuarioActual : Users

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Variable initialization
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Initialize Firebase Auth
        auth = Firebase.auth

        //Instantiate Firebase login component
        binding.signInAppCompatButton.setOnClickListener {
            val mEmail = binding.emailEditText.text.toString()
            val mPassword = binding.passwordEditText.text.toString()

            //Login Validations
            when {
                mEmail.isEmpty() || mPassword.isEmpty() -> {
                    Toast.makeText(baseContext, "Correo o contraseña inválida", Toast.LENGTH_SHORT).show()
                } else -> {
                    SignIn(mEmail, mPassword)
                }
            }
        }

        //start register activity
        binding.signUpTextView.setOnClickListener{
            val intent = Intent(this,SignUpActivity::class.java)
            this.startActivity(intent)
        }

        //start recovery activity
        binding.recoveryAccountTextView.setOnClickListener {
            val intent = Intent(this,AccountRecoveryActivity::class.java)
            this.startActivity(intent)
        }

        db = Firebase.firestore

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null){
            if (currentUser.isEmailVerified){
                reload()
            } else {
                val intent = Intent(this,CheckEmailActivity::class.java)
                startActivity(intent)
            }
        }
    }


    //Login FUNCTION
    private fun SignIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithEmail:success")
                    reload()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Correo o contraseña inválida",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun reload() {
//        if(usuarioActual.userType == "TECNICO") {
            val intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)
//        }
//        if (usuarioActual.userType == "CLIENTE") {
//            val intent = Intent(this,TechListingActivity::class.java)
//            this.startActivity(intent)
//        }
    }

//    fun verType(){
//        if(usuarioActual.userType == "CLIENT"){
//            val intent = Intent(this,TechListingActivity::class.java)
//            this.startActivity(intent)
//        } else {
//            if(usuarioActual.userType == "TECNICO"){
//                val intent = Intent(this, MainActivity::class.java)
//                this.startActivity(intent)
//            }
//        }
//
//    }
}