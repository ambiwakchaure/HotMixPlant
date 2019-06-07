package sn.sns.hotmixplant.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.example.pacermirror.database.TABLE_SMS_HISTORY
import kotlinx.android.synthetic.main.activity_sms_history.*
import sn.sns.hotmixplant.R
import sn.sns.hotmixplant.adapters.SmsHistoryAdapter
import sn.sns.hotmixplant.classes.MyApplication
import sn.sns.hotmixplant.classes.T

class SmsHistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sms_history)

        go_back.setOnClickListener {

            finish()
        }

        //get local sms details
        var SMS_INFO = TABLE_SMS_HISTORY.selectSmsDetails()
        if(SMS_INFO.isEmpty())
        {
            T.t("Oops ! sms details not found")
        }
        else
        {
            message_history_rv.layoutManager = LinearLayoutManager(MyApplication.context, LinearLayout.VERTICAL, false)
            val adapter = SmsHistoryAdapter(SMS_INFO);
            message_history_rv.adapter = adapter
            adapter.notifyDataSetChanged()

        }




    }
}
