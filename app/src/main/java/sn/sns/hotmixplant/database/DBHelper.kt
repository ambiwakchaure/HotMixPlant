package com.example.pacermirror.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)
{
    override fun onCreate(db: SQLiteDatabase)
    {
        try
        {
            db.execSQL(TABLE_SMS_HISTORY.CREATE_NEW_TABLE)

        } catch (e: SQLiteException) {

        }

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int)
    {

    }

    companion object
    {
        internal val DATABASE_NAME = "ePill.db"
        //static final int DATABASE_VERSION = 1;
        internal val DATABASE_VERSION = 1

    }

}