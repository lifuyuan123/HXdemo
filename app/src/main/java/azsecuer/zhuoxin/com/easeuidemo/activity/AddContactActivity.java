package azsecuer.zhuoxin.com.easeuidemo.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.widget.EaseAlertDialog;
import com.hyphenate.exceptions.HyphenateException;

import azsecuer.zhuoxin.com.easeuidemo.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddContactActivity extends AppCompatActivity {

    @BindView(R.id.search)
    Button search;
    @BindView(R.id.edit_note)
    EditText editNote;
    @BindView(R.id.indicator)
    Button indicator;
    @BindView(R.id.ll_user)
    RelativeLayout llUser;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.activity_add_contact)
    LinearLayout activityAddContact;
    private ProgressDialog progressDialog;
    private String serch, username;
    boolean contains;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.search, R.id.indicator,R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search:
                serch = editNote.getText().toString().trim();
                if(serch.equals("")||serch==null){
                    return;
                }else {
                    name.setText(serch);
                    llUser.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.indicator:
                addContact();
                break;
            case R.id.back:
                finish();
                llUser.setVisibility(View.GONE);
                break;
        }
    }

    private void addContact() {
        if (EMClient.getInstance().getCurrentUser().equals(serch)) {
            new EaseAlertDialog(this, R.string.not_add_myself).show();
            return;
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    contains = EMClient.getInstance().contactManager().getAllContactsFromServer().contains(serch);
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(contains){
                            new EaseAlertDialog(AddContactActivity.this, "已经是你的好友").show();
                            return;
                        }else {
                            progressDialog = new ProgressDialog(AddContactActivity.this);
                            String stri = getResources().getString(R.string.Is_sending_a_request);
                            progressDialog.setMessage(stri);
                            progressDialog.setCanceledOnTouchOutside(false);
                            progressDialog.show();
                            new Thread(new Runnable() {
                                public void run() {

                                    try {
                                        //demo use a hardcode reason here, you need let user to input if you like
                                        String s = getResources().getString(R.string.Add_a_friend);
                                        EMClient.getInstance().contactManager().addContact(serch, s);
                                        runOnUiThread(new Runnable() {
                                            public void run() {
                                                progressDialog.dismiss();
                                                String s1 = getResources().getString(R.string.send_successful);
                                                Toast.makeText(getApplicationContext(), s1, Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    } catch (final Exception e) {
                                        runOnUiThread(new Runnable() {
                                            public void run() {
                                                progressDialog.dismiss();
                                                String s2 = getResources().getString(R.string.Request_add_buddy_failure);
                                                Toast.makeText(getApplicationContext(), s2 + e.getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }
                                }
                            }).start();
                        }
                    }
                });
            }
        }).start();

    }

}


