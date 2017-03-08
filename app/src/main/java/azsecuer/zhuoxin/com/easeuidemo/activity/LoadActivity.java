package azsecuer.zhuoxin.com.easeuidemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

import azsecuer.zhuoxin.com.easeuidemo.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoadActivity extends AppCompatActivity {


    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.regist)
    Button regist;
    @BindView(R.id.login)
    Button login;
    private String name,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(R.layout.activity_load);
        ButterKnife.bind(this);
    }

    //判断是否自动登陆
    private void init() {
        if(EMClient.getInstance().isLoggedInBefore()){
            Intent i = new Intent(LoadActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

//登陆方法
    private void login(final String name, String pass) {
        EMClient.getInstance().login(name, pass, new EMCallBack() {
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                new EMOptions().setAutoLogin(true);
                Intent i = new Intent(LoadActivity.this, MainActivity.class);
                i.putExtra("name", name);
                startActivity(i);
                finish();
            }

            @Override
            public void onError(int i, String s) {
                Log.i("tag", "登陆失败" + s);
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    @OnClick({R.id.regist, R.id.login})
    public void onClick(View view) {
        name=username.getText().toString().trim();
        pass=password.getText().toString().trim();
        switch (view.getId()) {
            case R.id.regist:
                Intent i = new Intent(LoadActivity.this, RegistActivity.class);
                startActivity(i);
                break;
            case R.id.login:
                login(name,pass);
                break;
        }
    }
}
