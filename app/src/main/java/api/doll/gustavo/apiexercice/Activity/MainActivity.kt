package api.doll.gustavo.apiexercice.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import api.doll.gustavo.apiexercice.API.APIInterface
import api.doll.gustavo.apiexercice.Activity.Adapter.UserAdapter
import api.doll.gustavo.apiexercice.R
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userAdapter = UserAdapter()
        list_user.adapter = userAdapter



        /*Retrofit Response*/

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
                    userAdapter.setUser(it.results)
                    loadHeaderData()
                }, {
                   Log.e("FAIL",it.message)
                })




    }




    /*Sorting the header User*/
    val randomIndex  = Random()
    fun sorting(from: Int, to: Int):Int{
            return randomIndex.nextInt(to - from) + from
    }

    fun loadHeaderData(){
        /*Load Header attributes*/
        val headerTitle: TextView = header
        val ImageHeader: ImageView = Imageheader
        val index:Int = sorting(0,userAdapter.users.size)
        val headerMessage: TextView = message

        Picasso.get().load(userAdapter.users[index].picture.large).transform(CircleTransformation()).into(ImageHeader)


        headerTitle.text = userAdapter.users[index].name.first
        headerMessage.text = "Parabéns você está na " + userAdapter.users.indexOf(userAdapter.users[index + 1]) + "º" + " posição"
    }




}
