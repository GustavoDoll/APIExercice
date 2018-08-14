package api.doll.gustavo.apiexercice

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var userAdapter:UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_main)

        userAdapter = UserAdapter()
        list_user.adapter = userAdapter


        val retrofit:Retrofit = Retrofit.Builder()
                .baseUrl("https://randomuser.me")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        val api = retrofit.create(APIInterface::class.java)
        api.getUsers()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    userAdapter.setUser(it.results)},

                        {
                   Log.e("FAIL",it.message)
                })



    }
    inner class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>(){

        private val users:MutableList<Users> = mutableListOf()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
          return UserViewHolder(layoutInflater.inflate(R.layout.user_item,parent,false))
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
            val headerTitle: TextView = header
            headerTitle.text = users[0].name.first
            
        }


        inner class UserViewHolder( itemView : View): RecyclerView.ViewHolder(itemView){




            val nameTitle: TextView = itemView.findViewById(R.id.name_title)
            val Image: ImageView = itemView.findViewById(R.id.thumb)


                    fun bindModel(user:Users){
                        nameTitle.text = user.name.first
                        Picasso.get().load(user.picture.large).into(Image)
                    }
        }
    }


}
