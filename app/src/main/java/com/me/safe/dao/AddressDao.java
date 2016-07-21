package com.me.safe.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 归属地查询
 *
 * @auther yjh
 * @date 2016/7/17
 */
public class AddressDao {

    public static String getAddress(Context context, String phone) {
        String address = "未知号码";

        if (phone.matches("^1\\d{10}$")) {
            SQLiteDatabase db = SQLiteDatabase.openDatabase(context.getFilesDir().getAbsolutePath() + "/address.db", null, SQLiteDatabase.OPEN_READONLY);
            Cursor cursor = db.rawQuery("select location from data2 where id=(select outkey from data1 where id=?)", new String[]{phone.substring(0, 7)});

            if (cursor.moveToFirst()) {
                address = cursor.getString(0);
            }
            cursor.close();
            db.close();
        }
        return address;
    }
}
