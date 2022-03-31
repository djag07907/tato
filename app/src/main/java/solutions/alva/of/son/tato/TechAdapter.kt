package solutions.alva.of.son.tato

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import solutions.alva.of.son.tato.classes.Users

class TechAdapter(private val techList : ArrayList<Users>) : RecyclerView.Adapter<TechAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TechAdapter.MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item,
        parent,false)

        return MyViewHolder(itemView)
    }



    override fun onBindViewHolder(holder: TechAdapter.MyViewHolder, position: Int) {

        val user = techList[position]
        holder.techName.text = user.uId
        holder.userTypes.text = user.userType
        holder.userNumber.text = user.userNum


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
    }


}