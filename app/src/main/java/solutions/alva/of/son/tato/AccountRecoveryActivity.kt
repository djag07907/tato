package solutions.alva.of.son.tato

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import solutions.alva.of.son.tato.databinding.ActivityAccountRecoveryBinding

class AccountRecoveryActivity : AppCompatActivity() {

    //Instantiate components from design
    private lateinit var binding : ActivityAccountRecoveryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Initialize variables
        binding = ActivityAccountRecoveryBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.sendEmailAppCompatButton.setOnClickListener {
            val emailAddress = binding.emailEditText.text.toString()
            Firebase.auth.sendPasswordResetEmail(emailAddress).addOnCompleteListener { task ->
                if (task.isSuccessful){
                    val intent = Intent(this, SignInActivity::class.java)
                    this.startActivity(intent)
                } else {
                    Toast.makeText(
                        baseContext, "Ingrese un correo de una cuenta valida",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}