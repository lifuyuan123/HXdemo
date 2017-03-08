package azsecuer.zhuoxin.com.easeuidemo.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseConstant;

import azsecuer.zhuoxin.com.easeuidemo.R;
import azsecuer.zhuoxin.com.easeuidemo.activity.LoadActivity;
import azsecuer.zhuoxin.com.easeuidemo.activity.chatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/2.
 */

public class SettingFragment extends Fragment {
    @BindView(R.id.ll_user_profile)
    LinearLayout llUserProfile;
    @BindView(R.id.btn_logout)
    Button btnLogout;
    @BindView(R.id.user_id)
    EditText userId;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.lin)
    LinearLayout lin;
    private String name;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        text.setText(EMClient.getInstance().getCurrentUser());

    }

    @OnClick({R.id.ll_user_profile, R.id.btn_logout,R.id.lin})
    public void onClick(View view) {
        name = userId.getText().toString().trim();
        switch (view.getId()) {
            case R.id.ll_user_profile:
                if (name.equals(EMClient.getInstance().getCurrentUser())) {
                    Toast.makeText(getContext(), "不能和自己聊天", Toast.LENGTH_SHORT).show();
                } else {
                    //聊天页面的跳转  传入参数  和谁聊  单聊还是群聊
                    Intent i = new Intent(getContext(), chatActivity.class);
                    i.putExtra(EaseConstant.EXTRA_USER_ID, name);
                    i.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EMMessage.ChatType.Chat);
                    if (!name.equals("") && name != null) {
                        startActivity(i);
                    }
                }
                break;
            case R.id.btn_logout:
                logout();
                break;
            case R.id.lin:
                //点击其他地方隐藏输入框
                InputMethodManager imm = (InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                break;

        }
    }

    private void logout() {
        EMClient.getInstance().logout(false, new EMCallBack() {
            @Override
            public void onSuccess() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "退出登陆", Toast.LENGTH_SHORT).show();
                    }
                });
                new EMOptions().setAutoLogin(false);
                Intent i = new Intent(getContext(), LoadActivity.class);
                startActivity(i);
                getActivity().finish();

            }

            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

}
