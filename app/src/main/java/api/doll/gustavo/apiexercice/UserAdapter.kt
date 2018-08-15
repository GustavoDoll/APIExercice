package api.doll.gustavo.apiexercice

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class UserAdapter : RecyclerView.Adapter<UserViewHolder>(){

    val users:MutableList<Users> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):UserViewHolder  {

        val inflater = LayoutInflater.from(parent?.context)
        return UserViewHolder(inflater.inflate(R.layout.user_item,parent,false))
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bindModel(users[position])
    }

    fun setUser(results: List<Users>) {
        users.addAll(results)
        notifyDataSetChanged()
    }
}

