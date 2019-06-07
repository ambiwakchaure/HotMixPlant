package com.example.pacermirror.database

import android.content.ContentValues
import sn.sns.hotmixplant.classes.MyApplication
import sn.sns.hotmixplant.classes.T
import sn.sns.hotmixplant.pojo.SmsHistoryInfo


class TABLE_SMS_HISTORY
{
    companion object
    {
        val TABLE_NAME : String = "table_sms_history"

        val ID : String = "id"
        val U_NAME : String = "u_name"
        val U_MOBILE : String = "u_mobile"
        val U_MESSAGE : String = "u_message"
        val MSG_SENT_TIME_STAMP : String = "msq_sent_timestamp"


        var CREATE_NEW_TABLE = ("CREATE TABLE " + TABLE_NAME
                + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + U_NAME + " TEXT, "
                + U_MOBILE + " TEXT, "
                + U_MESSAGE + " TEXT, "
                + MSG_SENT_TIME_STAMP + " TEXT)")


        //function for insert new message details
        fun addNewSms(u_name: String,
                      u_mobile: String,
                      u_message: String )
        {

            val db = MyApplication.db!!.getWritableDatabase()

            val values = ContentValues()
            values.put(U_NAME, u_name)
            values.put(U_MOBILE, u_mobile)
            values.put(U_MESSAGE, u_message)
            values.put(MSG_SENT_TIME_STAMP, T.getSystemDateTime())

            db.insert(TABLE_NAME, null, values)

        }

        fun selectSmsDetails(): ArrayList<SmsHistoryInfo> {

            var SMS_DATA = ArrayList<SmsHistoryInfo>()

            try
            {
                val db = MyApplication.db!!.getReadableDatabase()
                val uQuery = "SELECT * FROM $TABLE_NAME ORDER BY $ID DESC"
                var cursor = db.rawQuery(uQuery, null)

                if(cursor!!.count > 0)
                {
                    while (cursor!!.moveToNext())
                    {
                        var ID = cursor.getString(cursor.getColumnIndex(ID))
                        var U_NAME = cursor.getString(cursor.getColumnIndex(U_NAME))
                        var U_MOBILE = cursor.getString(cursor.getColumnIndex(U_MOBILE))
                        var U_MESSAGE = cursor.getString(cursor.getColumnIndex(U_MESSAGE))
                        var MSG_SENT_TIME_STAMP = cursor.getString(cursor.getColumnIndex(MSG_SENT_TIME_STAMP))

                        SMS_DATA.add(SmsHistoryInfo(ID,U_NAME,U_MOBILE,U_MESSAGE,MSG_SENT_TIME_STAMP))
                    }
                }


            } catch (e: Exception) {
                e.printStackTrace()
            }

            return SMS_DATA!!
        }
        /*//function for select prescription id

        fun updateKey(key : String, boardNumber : String, presc_id: String)
        {
            try
            {
                val db = MyApplication.db!!.getReadableDatabase()
                val deleteQuery = "UPDATE $TABLE_NAME SET $KEY_DATA = '"+key+"' WHERE $BOARD_NUMBER = '"+boardNumber+"' AND $PRESCRIPTION_ID = '"+presc_id+"'"
                db.execSQL(deleteQuery)

            } catch (e: Exception) {

            }

        }

        //function for check dulplicate board id
        fun checkDuplicateBoardId(boardNumber: String,presc_id: String): String {
            var status = "2"
            var cursor: Cursor? = null

            try
            {
                val db = MyApplication.db!!.getReadableDatabase()
                val uQuery = "SELECT COUNT(*) AS  '"+RETURN_COUNT+"' FROM $TABLE_NAME WHERE $BOARD_NUMBER = '"+boardNumber+"' AND $PRESCRIPTION_ID = '"+presc_id+"'"
                cursor = db.rawQuery(uQuery, null)

                if (cursor!!.moveToNext())
                {
                    val counterCheck = cursor.getString(cursor.getColumnIndex(RETURN_COUNT))

                    val ddd = Integer.valueOf(counterCheck)

                    if (ddd > 0)
                    {
                        status = "1"
                    }
                    else
                    {
                        status = "0"
                    }
                }


            } catch (e: Exception) {
                e.printStackTrace()
            }

            return status
        }
        //function for select board id
        fun getKey(boardNumber: String): String {
            var keyData : String? = null
            var cursor: Cursor? = null

            try
            {
                val db = MyApplication.db!!.getReadableDatabase()
                val uQuery = "SELECT $KEY_DATA FROM $TABLE_NAME WHERE $BOARD_NUMBER = '"+boardNumber+"'"
                cursor = db.rawQuery(uQuery, null)

                if (cursor!!.moveToNext())
                {
                    keyData = cursor.getString(cursor.getColumnIndex(KEY_DATA))


                }


            } catch (e: Exception) {
                e.printStackTrace()
            }

            return keyData!!
        }*/


    }
}