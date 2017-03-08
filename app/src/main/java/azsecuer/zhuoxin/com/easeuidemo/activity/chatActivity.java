package azsecuer.zhuoxin.com.easeuidemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.ui.EaseChatFragment;

import azsecuer.zhuoxin.com.easeuidemo.R;

public class chatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat2);
        EaseChatFragment chatFragment=new EaseChatFragment();
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.activity_chat2,chatFragment).commit();
    }
}
