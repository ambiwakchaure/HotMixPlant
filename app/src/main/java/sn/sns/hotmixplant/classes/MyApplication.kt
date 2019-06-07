package sn.sns.hotmixplant.classes

import android.app.Application
import android.content.Context
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.example.pacermirror.database.DBHelper

class MyApplication : Application() {


    companion object{

        lateinit var context : Context

        //initialize db instance
        var db: DBHelper? = null


        //for volley
        private val TAG = MyApplication::class.java.simpleName
        @get:Synchronized var instance: MyApplication? = null
            private set
    }
    override fun onCreate() {
        super.onCreate()

        context = applicationContext
        instance = this

        //check db instance already created else create db object
        //create db object
        if(db == null)
        {
            db = DBHelper(context)
            db!!.writableDatabase
        }

    }
    val requestQueue: RequestQueue? = null
        get()
        {
            if (field == null)
            {
                return Volley.newRequestQueue(applicationContext)
            }
            return field
        }
    fun <T> addToRequestQueue(request: Request<T>)
    {
        request.tag = TAG
        request.retryPolicy = DefaultRetryPolicy(30000, 0, 0F)
        requestQueue?.add(request)
    }
}