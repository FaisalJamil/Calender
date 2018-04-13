/*
 * Copyright (c) 2017. Faisal Jamil
 */

package com.example.faisal.interviewtest.util;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.MonthDisplayHelper;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/* Used as a mix of several smaller utilities to avoid large bunch of codes */
public class Utils {


    public static final String[] CALENDER_READ_PERMISSIONS = {Manifest.permission.READ_CALENDAR};
    public static final int CALENDER_READ_PERMISSIONS_REQUEST_CODE = 9919;

    public static long getLastEventId(ContentResolver cr, Context context) {
        Cursor cursor;
        try{
                cursor = cr.query(CalendarContract.Events.CONTENT_URI, new String [] {"MAX(_id) as max_id"}, null, null, "_id");
                cursor.moveToFirst();
                long max_val = cursor.getLong(cursor.getColumnIndex("max_id"));
                return max_val;
        }catch (SecurityException exception){
            // error handling.
        }
        return 0;
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
