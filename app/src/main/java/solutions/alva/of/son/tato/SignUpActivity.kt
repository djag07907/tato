package solutions.alva.of.son.tato

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import solutions.alva.of.son.tato.classes.Users
import solutions.alva.of.son.tato.databinding.ActivitySignUpBinding
import java.util.regex.Pattern


class SignUpActivity : AppCompatActivity() {

    //Firebase variables
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var db : FirebaseFirestore
//    private lateinit var spinnerOptions: Spinner


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Variable initialization
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Initialize Firebase Auth
        auth = Firebase.auth
        db = Firebase.firestore


        binding.signUpButton.setOnClickListener {
            val mEmail = binding.emailEditText.text.toString()
            val mPassword = binding.passwordEditText.text.toString()
            val mRepeatPassword = binding.repeatPasswordEditText.text.toString()
            val selected = binding.radioGroup.checkedRadioButtonId
            val userNum = binding.numberEditText.text.toString()
            val userName = binding.uNameEditText.text.toString()
//            val userDep = binding.depSpinner.onItemSelectedListener



            val passwordRegex = Pattern.compile(
                "^" +
                        "(?=.*[-@#$%^&+=])" +       //Al menos 1 caracter especial
                        ".{6,}" +               //Al menos 6 caracteres
                        "$"
            )

            //Validations
            if (mEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
                Toast.makeText(
                    baseContext, "Ingrese correo valido.",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (mPassword.isEmpty() || !passwordRegex.matcher(mPassword).matches()) {
                Toast.makeText(
                    baseContext, "La contraseña es muy debil, debe contener al menos" +
                            "1 caracter especial y 6 caracteres en total",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (mPassword != mRepeatPassword) {
                Toast.makeText(
                    baseContext, "La contraseña no coincide.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                createAccount(mEmail, mPassword, userNum, userName)
                Toast.makeText(
                    baseContext, "Usuario creado exitosamente",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.backImageView.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }


//        val depList = listOf("Atlántida","Choluteca","Colón","Comayagua","Copán",
//            "Cortés","El Paraíso","Francisco Morazán","Gracias a Dios","Intibucá",
//            "Islas de la Bahía","La Paz","Lempira","Ocotepeque","Olancho","Santa Bárbara","Valle",
//            "Yoro")
//        spinnerOptions = findViewById(R.id.depSpinner)
//
//        val adaptador = ArrayAdapter(this,android.R.layout.simple_spinner_item,depList)
//        spinnerOptions.adapter = adaptador
//
//        spinnerOptions.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
//            override fun onItemSelected(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long) {
//
//            }
//
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//            }
//
//        }

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


    private fun createAccount(email: String, password: String, userNum: String, userName: String) {
        val userType = if (binding.radioGroup.checkedRadioButtonId == R.id.radio_client) "CLIENTE" else "TECNICO"
//        val userDep = binding.depSpinner.onItemSelectedListener

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    //grabar en db en reg el user y establecer el tipo de use
                    val uID = task.getResult()?.user?.uid ?: ""
                        if (uID == ""){
                            Toast.makeText(baseContext, "No se pudo crear el usuario.",
                                Toast.LENGTH_SHORT).show()
                            return@addOnCompleteListener
                        }
                    val userToCreate = Users(
                        uID,
                        userName,
                        userType,
                        userNum,
                        "",
                        ""
                    )
                    db.collection("users")
                        .add(userToCreate)
                        .addOnSuccessListener { documentReference ->
                            Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                        }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Error adding document", e)
                        }

                    val intent = Intent(this, CheckEmailActivity::class.java)
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "No se pudo crear el usuario.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun reload() {
        val intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)
    }

}

