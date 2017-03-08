package azsecuer.zhuoxin.com.easeuidemo.MyManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hyphenate.easeui.domain.EaseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import azsecuer.zhuoxin.com.easeuidemo.adapter.*;

/**
 * Created by Administrator on 2017/3/6.
 */

public class Myhelper {
    private Context context;
    private SQLiteDatabase sqLiteDatabase;
    private MyDb myDb;

    public Myhelper(Context context) {
        this.context = context;
        myDb=new MyDb(context);
    }

    public void adduser(Demo user){
        sqLiteDatabase=myDb.getReadableDatabase();
        List<Demo> selectall = selectall();
        for (Demo d:selectall) {
            if (!d.getName().equals(user.getName())){
                ContentValues contentValues=new ContentValues();
                contentValues.put("name",user.getName());
                sqLiteDatabase.insert("myuser",null,contentValues);
            }
        }

        sqLiteDatabase.close();
    }
    public void deleteuser(String user){
        sqLiteDatabase=myDb.getReadableDatabase();
        sqLiteDatabase.delete("myuser","nemw=?",new String[]{user});
    }
    public List<Demo> selectall(){
        sqLiteDatabase=myDb.getReadableDatabase();
        List<Demo> list=new ArrayList<>();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from myuser",null);
        while (cursor.moveToNext()){
            String name=cursor.getString(cursor.getColumnIndex("name"));
            Demo demo=new Demo(name);
            list.add(demo);
        }
        return list;
    }
}
