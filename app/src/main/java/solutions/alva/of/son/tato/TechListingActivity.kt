package solutions.alva.of.son.tato

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tech_listing)

        recyclerview = findViewById(R.id.techRecyclerView)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.setHasFixedSize(true)

        techArrayList = arrayListOf()

        techAdapter = TechAdapter(techArrayList)

        recyclerview.adapter = techAdapter


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