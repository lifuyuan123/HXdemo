package azsecuer.zhuoxin.com.easeuidemo.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.NetUtils;

import azsecuer.zhuoxin.com.easeuidemo.ECapplication;
import azsecuer.zhuoxin.com.easeuidemo.R;
import azsecuer.zhuoxin.com.easeuidemo.fragment.SettingFragment;
import azsecuer.zhuoxin.com.easeuidemo.fragment.address_listfragment;
import azsecuer.zhuoxin.com.easeuidemo.fragment.conversationfragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity{

    @BindView(R.id.btn_conversation)
    RadioButton btnConversation;
    @BindView(R.id.btn_address_list)
    RadioButton btnAddressList;
    @BindView(R.id.btn_setting)
    RadioButton btnSetting;

    private SoundPool sp;//声明一个SoundPool
    private int music;//定义一个整型用load（）；来设置suondID

    private LocalBroadcastManager localBroadcastManager;
    int i=0;
    private BroadcastReceiver mybroadcastReceiver;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private address_listfragment addresslistfragment;
    private conversationfragment conversatiofragment;
    private SettingFragment settingFragment;
    private Fragment[] fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        addresslistfragment=new address_listfragment();
        conversatiofragment=new conversationfragment();
        settingFragment=new SettingFragment();
        fragment=new Fragment[]{addresslistfragment,conversatiofragment,settingFragment};
        fragmentManager=getSupportFragmentManager();
        transaction=fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container,addresslistfragment)
                .add(R.id.fragment_container,conversatiofragment)
                .add(R.id.fragment_container,settingFragment).commit();
        choicefragment(0);

        sp= new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);//第一个参数为同时播放数据流的最大个数，第二数据流类型，第三为声音质量
        music = sp.load(this, R.raw.pixiedust, 1); //把你的声音素材放到res/raw里，第2个参数即为资源文件，第3个为音乐的优先级

        localBroadcastManager=LocalBroadcastManager.getInstance(this);
        btnConversation.setChecked(true);
        btnConversation.setTextColor(getResources().getColor(R.color.common_botton_bar_blue));
        //注册一个监听连接状态的listener
        EMClient.getInstance().addConnectionListener(new MyConnectionListener());
        Log.v("11111","前");
        registbroadcast();
        Log.v("11111","后");
    }

    //广播的注册
    private void registbroadcast() {
        localBroadcastManager=LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("收到好友邀请");
        intentFilter.addAction("增加了联系人时回调此方法");
        Log.e("11111Test","on receiver:"+"+intent.getAction()");
            mybroadcastReceiver=new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Log.e("11111Test","on receiver:"+" "+intent.getAction());
                    if(intent.getAction().equals("收到好友邀请")){
                        btnAddressList.setTextColor(getResources().getColor(R.color.btn_green_pressed));
                        sp.play(music, 1, 1, 0, 0, 1);
                        showAgreedDialog(intent.getStringExtra("name"),intent.getStringExtra("s1"));
                    }else if(intent.getAction().equals("增加了联系人时回调此方法")){
                        Toast.makeText(MainActivity.this, "添加好友成功", Toast.LENGTH_SHORT).show();
                    }

                }
            };
        localBroadcastManager.registerReceiver(mybroadcastReceiver,intentFilter);
    }

    @OnClick({R.id.btn_conversation, R.id.btn_address_list, R.id.btn_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_conversation:
                choicefragment(0);
                btnConversation.setTextColor(getResources().getColor(R.color.common_botton_bar_blue));
                btnAddressList.setTextColor(getResources().getColor(R.color.btn_white_pressed));
                btnSetting.setTextColor(getResources().getColor(R.color.btn_white_pressed));
                break;
            case R.id.btn_address_list:
                btnConversation.setTextColor(getResources().getColor(R.color.btn_white_pressed));
                btnAddressList.setTextColor(getResources().getColor(R.color.common_botton_bar_blue));
                btnSetting.setTextColor(getResources().getColor(R.color.btn_white_pressed));
                choicefragment(1);
                break;
            case R.id.btn_setting:
                btnConversation.setTextColor(getResources().getColor(R.color.btn_white_pressed));
                btnAddressList.setTextColor(getResources().getColor(R.color.btn_white_pressed));
                btnSetting.setTextColor(getResources().getColor(R.color.common_botton_bar_blue));
                choicefragment(2);
                break;
        }
    }
    private void choicefragment(int index){
         hideall();
        transaction=fragmentManager.beginTransaction();
        switch (index){
            case 0:
                transaction.show(fragment[index]).commit();
                break;
            case 1:
                transaction.show(fragment[index]).commit();
                break;
            case 2:
                transaction.show(fragment[index]).commit();
                break;

        }
    }
    private void hideall(){
        transaction=fragmentManager.beginTransaction();
        for (int i = 0; i <fragment.length ; i++) {
            //判断不为空
            if(fragment[i]!=null){
                transaction.hide(fragment[i]);
            }
        }
            transaction.commit();
    }

    private void showAgreedDialog(final String user, String reason) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("应用提示").setMessage("用户 " + user + " 想要添加您为好友，是否同意？\n" + "验证信息：" + reason)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            EMClient.getInstance().contactManager().declineInvitation(user);
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            EMClient.getInstance().contactManager().acceptInvitation(user);
                            //删除好友后 刷新好友列表的操作
                            conversatiofragment.reflash();
                        } catch (HyphenateException e) {
                            e.printStackTrace();

                        }
                    }


                });
        builder.show();


    }
    private void showDeleteDialog(final String user) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("应用提示").setMessage("确定删除好友  " + user + " 吗？\n")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            //删除好友
                            EMClient.getInstance().contactManager().deleteContact(user);
                           runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                }
                            });
                        } catch (HyphenateException e) {
                            e.printStackTrace();

                        }
                    }


                });
        builder.show();


    }

    //实现ConnectionListener接口
    private class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {
        }
        @Override
        public void onDisconnected(final int error) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if(error == EMError.USER_REMOVED){
                        // 显示帐号已经被移除
                    }else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                        // 显示帐号在其他设备登录
                    } else {
                        if (NetUtils.hasNetwork(MainActivity.this)){}
                        //连接不到聊天服务器
                        else{}
                        //当前网络不可用，请检查网络设置
                    }
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("11111","销毁");
        localBroadcastManager.unregisterReceiver(mybroadcastReceiver);
    }
}
