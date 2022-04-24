package solutions.alva.of.son.tato

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.metrics.Event
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import solutions.alva.of.son.tato.classes.Users
import java.util.*
import kotlin.collections.ArrayList
import android.content.Intent

class TechAdapter(private val techList: ArrayList<Users>) : RecyclerView.Adapter<TechAdapter.MyViewHolder>() {

    private lateinit var db : FirebaseFirestore
    private lateinit var usuarioActual : Users
    private lateinit var binding: TechListingActivity

    private lateinit var mListener : onItemClickListener



       interface onItemClickListener {

        fun onItemClick(position: Int)

    }

    fun onItemClickListener(listener : onItemClickListener){

        mListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TechAdapter.MyViewHolder {


        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item,
        parent,false)

        return MyViewHolder(itemView, mListener)
    }



    override fun onBindViewHolder(holder: TechAdapter.MyViewHolder, position: Int) {

        val user = techList[position]
        holder.techName.text = user.userName
        holder.userTypes.text = user.userType
        holder.userNumber.text = user.userNum
        holder.userDeps.text= user.userDep
        holder.userProfs.text = user.techProf
//        holder.callPrefs.text = user.callPref
        holder.itemBtn.text = user.callPref
        holder.rateBtn.text = "Calificar"
//        holder.previewRate.rating = user.techRate
        holder.bindButtons(listener = mListener)


//        val uri = data.data
//
//        uri?.let { imageUpload(it)
//        val folder: StorageReference = FirebaseStorage.getInstance().reference.child("Users")
//        val fileName: StorageReference = folder.child("img"+user!!.uId)
//        val photoUrl = Uri.parse(uri.toString())
//        user.imageId = Glide.with(itemView.getContext()).load(photoUrl).into(imageView)


//        techList[position].imageId?.let { holder.imageId.setImageResource(it) }


    }



    override fun getItemCount(): Int {

        // Return size of the user list
        return techList.size

    }


    public class MyViewHolder(itemView : View, listener : onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        // Fill recyclerview with data from firestore
        val intent: Intent? = null
        val techName : TextView = itemView.findViewById(R.id.techNametv)
        val userTypes : TextView = itemView.findViewById(R.id.userTypetv)
        val userNumber : TextView = itemView.findViewById(R.id.userNumtv)
        val userDeps : TextView = itemView.findViewById(R.id.userDepTv)
        val userProfs : TextView = itemView.findViewById(R.id.userProfTv)
//        val userImage : ImageView = itemView.findViewById(R.id.userImageView)
        var itemBtn = itemView.findViewById<Button>(R.id.contactBtn)
        val rateBtn = itemView.findViewById<Button>(R.id.rateBtn)
        var previewRate = itemView.findViewById<RatingBar>(R.id.techPreviewRating)


        fun bindButtons(listener : onItemClickListener){
            itemBtn.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }


            rateBtn.setOnClickListener {
//
                val intent = Intent(itemView.context, RatePopupActivity::class.java)
                itemView.context.startActivity(intent)
                Log.i("Evaluation error", "Not working")
            }


        }


    }

}
