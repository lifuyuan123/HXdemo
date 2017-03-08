package azsecuer.zhuoxin.com.easeuidemo.MyManager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/3/6.
 */

public class MyDb extends SQLiteOpenHelper {
    public MyDb(Context context) {
        super(context, "user.db", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
     sqLiteDatabase.execSQL("create table if not exists myuser(name text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
