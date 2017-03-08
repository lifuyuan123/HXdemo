package azsecuer.zhuoxin.com.easeuidemo;

import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.exceptions.HyphenateException;

import azsecuer.zhuoxin.com.easeuidemo.MyManager.Demo;
import azsecuer.zhuoxin.com.easeuidemo.MyManager.Myhelper;

/**
 * Created by Administrator on 2017/3/2.
 */

public class ECapplication extends Application {
    private LocalBroadcastManager localBroadcastManager;
    @Override
    public void onCreate() {
        super.onCreate();
        EMOptions options = new EMOptions();
// 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        options.setAutoLogin(true);
//        EaseUI.getInstance().init(getApplicationContext(),options);
        if(EaseUI.getInstance().init(getApplicationContext(),options)){
          localBroadcastManager=LocalBroadcastManager.getInstance(getApplicationContext());
          EMClient.getInstance().contactManager().setContactListener(new EMContactListener() {
            @Override
            public void onContactAdded(String s) {
                //增加了联系人时回调此方法
                Log.v("11111","增加了联系人时回调此方法");
                localBroadcastManager.sendBroadcast(new Intent("增加了联系人时回调此方法"));
            }

            @Override
            public void onContactDeleted(String s) {
                //被删除时回调此方法
                Log.v("11111","被删除时回调此方法");
                localBroadcastManager.sendBroadcast(new Intent("被删除时回调此方法"));
            }

            @Override
            public void onContactInvited(final String s, final String s1) {
                //收到好友邀请
                Log.v("11111","收到好友邀请");
                //此线程时延时操作  发送广播再注册广播之后，不然无法产生广播的回调
                yanshiThread(s,s1);
            }

            @Override
            public void onFriendRequestAccepted(String s) {
                //接受添加
                Log.v("11111","接受添加");
                localBroadcastManager.sendBroadcast(new Intent("接受添加"));
            }

            @Override
            public void onFriendRequestDeclined(String s) {
                //拒绝添加
                Log.v("11111","拒绝添加");
                localBroadcastManager.sendBroadcast(new Intent("拒绝添加"));
            }
        });
            //设置日志
            EMClient.getInstance().setDebugMode(true);
        }

    }

    private void yanshiThread(final String name, final String reson){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    Log.v("11111","收到好友邀请111");
                    localBroadcastManager.sendBroadcast(new Intent("收到好友邀请").putExtra("name",name).putExtra("s1",reson));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
