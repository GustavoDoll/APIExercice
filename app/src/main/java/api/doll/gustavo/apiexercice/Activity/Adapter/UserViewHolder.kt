package api.doll.gustavo.apiexercice.Activity.Adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import api.doll.gustavo.apiexercice.Activity.CircleTransformation
import api.doll.gustavo.apiexercice.Model.Users
import api.doll.gustavo.apiexercice.R
import com.squareup.picasso.Picasso
import java.util.*

class UserViewHolder( itemView : View): RecyclerView.ViewHolder(itemView) {

    val nameTitle: TextView = itemView.findViewById(R.id.name_title)
    val Image: ImageView = itemView.findViewById(R.id.thumb)
    val Ranking_title:TextView = itemView.findViewById(R.id.ranking)
    val ranking : Int = sorting(0,1000)

    fun bindModel(user: Users){

        Ranking_title.text = ranking.toString()
        nameTitle.text = user.name.first
        Picasso.get().load(user.picture.large).transform(CircleTransformation()).into(Image)

    }


    fun sorting(from: Int, to: Int):Int{
        val randomIndex  = Random()

        return randomIndex.nextInt(to - from) + from
    }
}