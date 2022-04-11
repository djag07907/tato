package solutions.alva.of.son.tato

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.metrics.Event
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import solutions.alva.of.son.tato.classes.Users
import solutions.alva.of.son.tato.databinding.ActivityMainBinding

class TechAdapter(private val techList: ArrayList<Users>) : RecyclerView.Adapter<TechAdapter.MyViewHolder>() {
//    private lateinit var binding: ActivityMainBinding

    private lateinit var db : FirebaseFirestore
    private lateinit var usuarioActual : Users
    private lateinit var binding: TechListingActivity



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TechAdapter.MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item,
        parent,false)

//        val imageView : ImageView = itemView.findViewById(R.id.userImageView)
        return MyViewHolder(itemView)
    }



    override fun onBindViewHolder(holder: TechAdapter.MyViewHolder, position: Int) {

        val user = techList[position]
        holder.techName.text = user.userName
        holder.userTypes.text = user.userType
        holder.userNumber.text = user.userNum
        holder.userDeps.text= user.userDep
        holder.userProfs.text = user.techProf
        holder.callPrefs.text = user.callPref
        user.imageId = user.imageId


//        techList[position].imageId?.let { holder.imageId.setImageResource(it) }


    }



    override fun getItemCount(): Int {

        // Return size of the user list
        return techList.size

    }


    public class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        // Fill recyclerview with data from firestore
        val techName : TextView = itemView.findViewById(R.id.techNametv)
        val userTypes : TextView = itemView.findViewById(R.id.userTypetv)
        val userNumber : TextView = itemView.findViewById(R.id.userNumtv)
        val userDeps : TextView = itemView.findViewById(R.id.userDepTv)
        val userProfs : TextView = itemView.findViewById(R.id.userProfTv)
        val callPrefs : TextView = itemView.findViewById(R.id.userContactTv)
        val userImage : ImageView = itemView.findViewById(R.id.userImageView)
        val contactEvent : Button = itemView.findViewById(R.id.contactBtn)
//        val imageId : ImageView = itemView.findViewById(R.id.userImageView)

//        fun contactDecision(){
//            contactEvent.setOnClickListener{
//                Log.i("result here","BUTTON WORKING")
//            }
//        }

    }

//    private fun contactDecision(event : Event, position: Int){
//
//        val user = techList[position]
//        val contactChoice = user.callPref
//        val contactNumber = user.userNum.toString()
//        val REQUEST_PHONE_CALL = 1
//        val callIntent = Intent(Intent.ACTION_CALL)
//
//        if (contactChoice == "LLAMADA"){
//            callIntent.data = Uri.parse("tel:" + contactNumber)
//            startActivity(callIntent)
//        }
//
//        if (contactChoice == "WHATSAPP") {
//
//        } else {
//            Log.i("Pref error","No contact pref selected")
//        }
//    }
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray){
//        if(requestCode == REQUEST_PHONE_CALL)startCall()
//    }


}

//    contactBtn.setOnClickListener {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), REQUEST_PHONE_CALL)
//        } else {
//
//        }
//    }