package sn.sns.hotmixplant.adapters

import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import sn.sns.hotmixplant.R
import sn.sns.hotmixplant.pojo.SmsHistoryInfo

class SmsHistoryAdapter (val userList: ArrayList<SmsHistoryInfo>) : RecyclerView.Adapter<SmsHistoryAdapter.ViewHolder>()
{
    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.sms_history_row, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.bindItems(userList[position])
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int
    {
        return userList.size
    }
    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        fun bindItems(user: SmsHistoryInfo)
        {
            val name_tv = itemView.findViewById(R.id.name_tv) as TextView
            val mobile_tv = itemView.findViewById(R.id.mobile_tv) as TextView
            val message_tv = itemView.findViewById(R.id.message_tv) as TextView
            val message_timestamp_tv = itemView.findViewById(R.id.message_timestamp_tv) as TextView

            name_tv.setText(Html.fromHtml("<b>Name : </b>"+user.name))
            mobile_tv.setText(Html.fromHtml("<b>Mobile : </b>"+user.mobile))
            message_tv.setText(Html.fromHtml("<b>Message : </b>"+user.message))
            message_timestamp_tv.setText(Html.fromHtml("<b>Date Time : </b>"+user.timestamp))
        }
    }
}