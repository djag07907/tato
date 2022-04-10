package solutions.alva.of.son.tato

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import solutions.alva.of.son.tato.classes.Users
import solutions.alva.of.son.tato.databinding.ActivityMainBinding

class TechAdapter(private val techList: ArrayList<Users>) : RecyclerView.Adapter<TechAdapter.MyViewHolder>() {
//    private lateinit var binding: ActivityMainBinding


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
//        val imageId : ImageView = itemView.findViewById(R.id.userImageView)
    }


}