package solutions.alva.of.son.tato

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*
import solutions.alva.of.son.tato.classes.Users


class TechListingActivity : AppCompatActivity() {

    private lateinit var recyclerview : RecyclerView
    private lateinit var techArrayList : ArrayList<Users>
    private lateinit var techAdapter : TechAdapter
    private lateinit var db : FirebaseFirestore
    private lateinit var usuarioActual : Users
    private val PERMISSION_SEND_SMS = 123


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tech_listing)

        recyclerview = findViewById(R.id.techRecyclerView)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.setHasFixedSize(true)

        techArrayList = arrayListOf()

        techAdapter = TechAdapter(techArrayList)

        recyclerview.adapter = techAdapter

        // START CALL SELECTION
        techAdapter.onItemClickListener(object : TechAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                Log.i("result here","BUTTON WORKING, clicking on: $position")
                Toast.makeText(this@TechListingActivity,"Clicking on: $position",Toast.LENGTH_SHORT).show()

                val user = techArrayList[position]
                val contactChoice = user.callPref
                val contactNumber = user.userNum
                val REQUEST_PHONE_CALL = 1
                val callIntent = Intent(Intent.ACTION_CALL)

                if (contactChoice == "LLAMADA"){
//                    @SuppressLint("MissingPermission")
                    val callIntent = Intent(Intent.ACTION_CALL)
                    callIntent.data = Uri.parse("tel:" + "+504"+contactNumber)

                    if (ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this@TechListingActivity,arrayOf(Manifest.permission.CALL_PHONE),
                            REQUEST_PHONE_CALL
                        )
                    }
                    startActivity(callIntent)


                }

                if (contactChoice == "WHATSAPP") {
                    val uri = Uri.parse("smsto:" + "+504"+contactNumber)
                    val intent = Intent(Intent.ACTION_SENDTO,uri)
                    intent.setPackage("com.whatsapp")
                        if (ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(this@TechListingActivity,arrayOf(Manifest.permission.SEND_SMS),
                                PERMISSION_SEND_SMS
                            )
                        }
                    startActivity(intent)
                } else {
                    Log.i("Pref error","No contact pref selected")
                }

//                @SuppressLint("MissingPermission")
                fun startCall(){
                    val callIntent = Intent(Intent.ACTION_CALL)
                    callIntent.data = Uri.parse("tel:" + contactNumber)
                    startActivity(callIntent)

                }

                fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray){
                    if(requestCode == REQUEST_PHONE_CALL)startCall()
                }
            }




        })


        EventChangeListener()


    }

    private fun EventChangeListener() {

        // Fetch data from firestore and set into techArrayList
        db = FirebaseFirestore.getInstance()
            db.collection("users").whereEqualTo("userType","TECNICO").
            addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(
                    value: QuerySnapshot?,
                    error: FirebaseFirestoreException?
                ) {
                    if (error != null) {
                        Log.e("Firestore error", error.message.toString())
                        return
                    }
                    for (dc: DocumentChange in value?.documentChanges!!) {

                        if (dc.type == DocumentChange.Type.ADDED) {
                            techArrayList.add(dc.document.toObject(Users::class.java))
                        }
                    }

                    techAdapter.notifyDataSetChanged()

                }

            })
    }
}