package sn.sns.hotmixplant.activity

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.text.Html
import android.view.WindowManager
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.example.pacermirror.database.TABLE_SMS_HISTORY
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import org.json.JSONTokener
import sn.sns.hotmixplant.R
import sn.sns.hotmixplant.classes.MyApplication
import sn.sns.hotmixplant.classes.T
import sn.sns.hotmixplant.interfaces.Constants

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)


        title_tv.setText(Html.fromHtml("<b>H</b>ot<b>M</b>ix<b>P</b>lant"))
        fab_send.setOnClickListener {

            checkNetwork()
        }
        smshistory_img.setOnClickListener {

            startActivity(Intent(this,SmsHistoryActivity::class.java))
        }
    }

    private fun checkNetwork() {

        if(T.isNetworkAvailable())
        {
            if(!T.editextEmpty(name_edt,"Enter name"))
            {
                return
            }
            if(!T.editextEmpty(mobile_edt,"Enter mobile"))
            {
                return
            }
            if(!T.editextEmpty(message_edt,"Enter message"))
            {
                return
            }
            proceedSendMessage();
        }
        else
        {
            T.t("Oops ! check your internet connection")
        }
    }
    private fun proceedSendMessage()
    {
        try
        {
            var progressDialog = ProgressDialog(this);
            progressDialog.setMessage("Sending message...")
            progressDialog.setCancelable(false)
            progressDialog.show()
            val stringRequest = object : StringRequest
                (
                Request.Method.POST,
                Constants.WEB_URL,
                Response.Listener<String>
                {
                        response ->
                    progressDialog.dismiss()
                    parseResponse(response)

                },
                object : Response.ErrorListener
                {
                    override fun onErrorResponse(volleyError: VolleyError)
                    {
                        progressDialog.dismiss()
                        if(volleyError is TimeoutError)
                        {
                            val snackbar = Snackbar
                                .make(parrentLayout, "Oops ! request timed out", Snackbar.LENGTH_LONG)
                                .setAction("Try again") {
                                    proceedSendMessage();
                                }
                            snackbar.show()
                        }
                        else
                        {
                            val snackbar = Snackbar
                                .make(parrentLayout, "Oops ! something went wrong", Snackbar.LENGTH_LONG)
                                .setAction("Try again") {
                                    proceedSendMessage();
                                }
                            snackbar.show()
                        }

                    }
                }
            )
            {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String>
                {
                    val params = HashMap<String, String>()
                    params.put("name", name_edt.text.toString())
                    params.put("mobile", mobile_edt.text.toString())
                    params.put("msg", message_edt.text.toString())



                    return params
                }
            }
            //adding request to queue
            MyApplication.instance!!.addToRequestQueue(stringRequest)
        }
        catch (c : Exception)
        {
            T.t(""+c)
        }

    }
    private fun parseResponse(response: String?) {

        try
        {
            //check empty json
            if(response!!.length > 0 && response != null)
            {
                if(response.trim().equals("1"))
                {
                    T.t("Message sent successfully")
                    //insert data into local table
                    TABLE_SMS_HISTORY.addNewSms(
                        name_edt.text.toString(),
                        mobile_edt.text.toString(),
                        message_edt.text.toString())
                    //set field empty
                    name_edt.setText("")
                    mobile_edt.setText("")
                    message_edt.setText("")
                }
                else if(response.trim().equals("2"))
                {
                    T.t("Oops ! mobile number not found")
                }
                else if(response.trim().equals("0"))
                {
                    T.t("Oops ! Something went wrong")
                }
            }
            else
            {
                T.t("Incorrect Json")
            }
        }
        catch (e : Exception)
        {

        }
    }
}
