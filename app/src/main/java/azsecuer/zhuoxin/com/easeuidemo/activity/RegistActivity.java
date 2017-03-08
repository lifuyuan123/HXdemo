package azsecuer.zhuoxin.com.easeuidemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import azsecuer.zhuoxin.com.easeuidemo.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistActivity extends AppCompatActivity {
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.confirm_password)
    EditText confirmPassword;
    @BindView(R.id.regist)
    Button regist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        ButterKnife.bind(this);
    }

    //注册的方法  异步操作，需要new一个线程出来
    private void regist(final String name, final String pass) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(name, pass);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegistActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(RegistActivity.this,SuccessActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                } catch (HyphenateException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegistActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Log.i("tag", "注册失败" + e.getErrorCode() + "," + e.getMessage());
                }
            }
        }).start();
    }

    @OnClick(R.id.regist)
    public void onClick() {
        regist(username.getText().toString().trim(), password.getText().toString().trim());
    }
}
