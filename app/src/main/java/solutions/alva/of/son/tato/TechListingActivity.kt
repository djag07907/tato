package solutions.alva.of.son.tato

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.View.inflate
import android.widget.*
import androidx.appcompat.resources.Compatibility.Api21Impl.inflate
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ColorStateListInflaterCompat.inflate
import androidx.core.content.res.ComplexColorCompat.inflate
import androidx.core.graphics.drawable.DrawableCompat.inflate
import androidx.core.view.isEmpty
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import solutions.alva.of.son.tato.classes.Users
import solutions.alva.of.son.tato.databinding.ActivityAccountRecoveryBinding.inflate
import solutions.alva.of.son.tato.databinding.ActivityMainBinding
import solutions.alva.of.son.tato.databinding.ActivityTechListingBinding
import java.util.*
import kotlin.collections.ArrayList


class TechListingActivity : AppCompatActivity() {

    private lateinit var recyclerview : RecyclerView
    private lateinit var techArrayList : ArrayList<Users>
    private lateinit var newArrayList : ArrayList<Users>
    private lateinit var binding: ActivityTechListingBinding
    private lateinit var techAdapter : TechAdapter
    private lateinit var db : FirebaseFirestore
    private lateinit var usuarioActual : Users
    private val PERMISSION_SEND_SMS = 123
//    private lateinit var searchFilterView: SearchView
    private lateinit var startRating : Button
    private lateinit var menuButton : ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tech_listing)

//        val searchFilterView = findViewById(R.id.searchFilterView) as SearchView
//        var searchText = searchFilterView
//

//        val itemView = LayoutInflater.from(this).inflate(R.layout.list_item,
//            parent,false)


        binding = ActivityTechListingBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        startRating = findViewById<Button>(R.id.rateBtn)
//
//        startRating.setOnClickListener {
//            startRatingEvent()
//        }

        val homeImageView = findViewById(R.id.homeImageView) as ImageView

        homeImageView.setOnClickListener {
            val intent = Intent(this, MenuSelectionActivity::class.java)
            this.startActivity(intent)
        }


        recyclerview = findViewById(R.id.techRecyclerView)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.setHasFixedSize(true)

        techArrayList = arrayListOf()

        newArrayList = arrayListOf()


        techAdapter = TechAdapter(techArrayList)

        recyclerview.adapter = techAdapter


        techArrayList.addAll(newArrayList)

//        val adapter = TechAdapter(techArrayList)


        // START CALL SELECTION
        techAdapter.onItemClickListener(object : TechAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                Log.i("result here", "BUTTON WORKING, clicking on: $position")
//                Toast.makeText(this@TechListingActivity,"Clicking on: $position",Toast.LENGTH_SHORT).show()

                val user = techArrayList[position]
                val contactChoice = user.callPref
                val contactNumber = user.userNum
                val REQUEST_PHONE_CALL = 1
                val callIntent = Intent(Intent.ACTION_CALL)

                if (contactChoice == "LLAMADA") {
//                    @SuppressLint("MissingPermission")
                    val callIntent = Intent(Intent.ACTION_CALL)
                    callIntent.data = Uri.parse("tel:" + "+504" + contactNumber)

                    if (ActivityCompat.checkSelfPermission(
                            applicationContext,
                            Manifest.permission.CALL_PHONE
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(
                            this@TechListingActivity, arrayOf(Manifest.permission.CALL_PHONE),
                            REQUEST_PHONE_CALL
                        )
                    }
                    Log.i("Pref error", "Llamando a:" + contactNumber)
                    startActivity(callIntent)


                }

                if (contactChoice == "WHATSAPP") {
                    val uri = Uri.parse("smsto:" + "+504" + contactNumber)
                    val intent = Intent(Intent.ACTION_SENDTO, uri)
                    intent.setPackage("com.whatsapp")
                    if (ActivityCompat.checkSelfPermission(
                            applicationContext,
                            Manifest.permission.SEND_SMS
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(
                            this@TechListingActivity, arrayOf(Manifest.permission.SEND_SMS),
                            PERMISSION_SEND_SMS
                        )
                    }
                    Log.i("Pref error", "Abriendo whatsapp de:" + contactNumber)
                    startActivity(intent)
                } else {
                    Log.i("Pref error", "No contact pref selected")
                }

                //                @SuppressLint("MissingPermission")
                fun startCall() {
                    val callIntent = Intent(Intent.ACTION_CALL)
                    callIntent.data = Uri.parse("tel:" + contactNumber)
                    startActivity(callIntent)

                }

                fun onRequestPermissionsResult(
                    requestCode: Int,
                    permissions: Array<out String>,
                    grantResults: IntArray
                ) {
                    if (requestCode == REQUEST_PHONE_CALL) startCall()
                }
            }


        })


        EventChangeListener()


    }


    private fun EventChangeListener() {
        // Fetch data from firestore and set into techArrayLis

        db = FirebaseFirestore.getInstance()
//        if (searchFilterView.isEmpty()) {
            db.collection("users").whereEqualTo("userType", "TECNICO")
                .addSnapshotListener(object : EventListener<QuerySnapshot> {
                    override fun onEvent(
                        value: QuerySnapshot?,
                        error: FirebaseFirestoreException?
                    ) {
                        if (error != null) {
                            Log.e("Firestore error", error.message.toString())
                            return
                        }
                        techArrayList.clear()
                        for (dc: DocumentChange in value?.documentChanges!!) {

                            if (dc.type == DocumentChange.Type.ADDED) {
                                techArrayList.add(dc.document.toObject(Users::class.java))
                            }
                        }

                        techAdapter.notifyDataSetChanged()

                    }

                })
//        } else {
            Log.i("INFO","WORKING ON THIS")
//                db
//                .collection("users")
//                    .whereEqualTo("techProf", searchFilterView)
//                    .addSnapshotListener(object : EventListener<QuerySnapshot> {
//                        override fun onEvent(
//                            value: QuerySnapshot?,
//                            error: FirebaseFirestoreException?
//                        ) {
//                            if (error != null) {
//                                Log.e("Firestore error", error.message.toString())
//                                return
//                            }
//                            techArrayList.clear()
//                            for (dc: DocumentChange in value?.documentChanges!!) {
//
//                                if (dc.type == DocumentChange.Type.ADDED) {
//                                    techArrayList.add(dc.document.toObject(Users::class.java))
//                                }
//                            }
//
//                            techAdapter.notifyDataSetChanged()
//
//                        }
//
//                    })

//        }
    }
//    fun startRatingEvent(){
//        val intent = Intent(this,RatePopupActivity::class.java)
//        this.startActivity(intent)
//    }
}
