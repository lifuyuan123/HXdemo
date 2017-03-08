package azsecuer.zhuoxin.com.easeuidemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.List;

import azsecuer.zhuoxin.com.easeuidemo.R;
import azsecuer.zhuoxin.com.easeuidemo.adapter.Demo;
import azsecuer.zhuoxin.com.easeuidemo.adapter.Listadapter;

public class NewFriendsActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView image;
    private ListView list;
    private List<String> stringList;
    private List<Demo> lists;
    private Listadapter listadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friends);
        init();

    }

    private void init() {
        image= (ImageView) findViewById(R.id.image);
        list= (ListView) findViewById(R.id.list);
        image.setOnClickListener(this);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        setdata();
        listadapter=new Listadapter(lists,this);
        list.setAdapter(listadapter);

    }

    private void setdata() {
           lists=new ArrayList<>();
        try {
            stringList=EMClient.getInstance().contactManager().getAllContactsFromServer();
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.image:
                finish();
                break;
        }
    }
}
