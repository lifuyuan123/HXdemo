package azsecuer.zhuoxin.com.easeuidemo.fragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.hyphenate.chat.EMClient;

import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.NetUtils;

import java.util.ArrayList;
import java.util.List;

import azsecuer.zhuoxin.com.easeuidemo.ChatActivity;
import azsecuer.zhuoxin.com.easeuidemo.MyManager.Myhelper;
import azsecuer.zhuoxin.com.easeuidemo.R;
import azsecuer.zhuoxin.com.easeuidemo.activity.AddContactActivity;
import azsecuer.zhuoxin.com.easeuidemo.activity.NewFriendsActivity;
import azsecuer.zhuoxin.com.easeuidemo.adapter.UserAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/2.
 */

public class conversationfragment extends EaseContactListFragment {

    @BindView(R.id.add_tongzhi)
    LinearLayout addTongzhi;
    private Myhelper myhelper;
    private BroadcastReceiver mybroadcastReceiver;
    private List<EaseUser> selecta=new ArrayList<>();
    @SuppressLint("InflateParams")
    @Override
    protected void initView() {
        super.initView();
        myhelper=new Myhelper(getContext());
        @SuppressLint("InflateParams") View headerview = LayoutInflater.from(getContext()).inflate(R.layout.add_tongzhi, null);
        addTongzhi = (LinearLayout) headerview.findViewById(R.id.add_tongzhi);
        listView.addHeaderView(headerview);
        registerForContextMenu(listView);
        addTongzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), NewFriendsActivity.class);
                startActivity(i);
            }
        });
        getlist();
        registbroadcast();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EaseUser user = selecta.get(position-1);
                if (user != null) {
                    String username = user.getUsername();
                    // demo中直接进入聊天页面，实际一般是进入用户详情页
                    startActivity(new Intent(getActivity(), ChatActivity.class).putExtra("userId", username));
                }
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                showDeleteDialog(selecta.get(i-1).getUsername());
                return true;
            }
        });

        titleBar.setRightImageResource(R.drawable.em_add);
        titleBar.setRightLayoutClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                NetUtils.hasDataConnection(getActivity());
            }
        });
        super.setUpView();
        // 进入添加好友页
        titleBar.getRightLayout().setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddContactActivity.class));
            }
        });
    }

    private void showDeleteDialog(final String user) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("应用提示").setMessage("确定删除好友  " + user + " 吗？\n")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            //删除好友
                            EMClient.getInstance().contactManager().deleteContact(user);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                   getlist();
                                }
                            });
                        } catch (HyphenateException e) {
                            e.printStackTrace();

                        }
                    }


                });
        builder.show();


    }
    //获取好友列表
    private void getlist(){
           selecta.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<String> stringList;
                try {
                    stringList= EMClient.getInstance().contactManager().getAllContactsFromServer();
                    for (String s:stringList) {
                        EaseUser e=new EaseUser(s);
                        selecta.add(e);
                    }
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        contactListLayout.init(selecta);
                        contactListLayout.refresh();
                    }
                });
            }
        }).start();

    }

    private void registbroadcast() {
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("增加了联系人时回调此方法");
        if(mybroadcastReceiver==null){
            mybroadcastReceiver=new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Log.e("11111Test","on receiver:"+" "+intent.getAction());
                    if(intent.getAction().equals("增加了联系人时回调此方法")){
                        getlist();
                    }

                }
            };
        }
        getActivity().registerReceiver(mybroadcastReceiver,intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mybroadcastReceiver);
    }
    public void reflash(){
        getlist();
    }


}
