package azsecuer.zhuoxin.com.easeuidemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import azsecuer.zhuoxin.com.easeuidemo.MyManager.*;
import azsecuer.zhuoxin.com.easeuidemo.MyManager.Demo;
import azsecuer.zhuoxin.com.easeuidemo.R;

/**
 * Created by Administrator on 2017/3/5.
 */

public class UserAdapter extends BaseAdapter {
    List<Demo> easeUserList;

    public List<Demo> getEaseUserList() {
        return easeUserList;
    }

    Context context;

    public UserAdapter(List<Demo> easeUserList, Context context) {
        this.easeUserList = easeUserList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return easeUserList.size();
    }

    @Override
    public Object getItem(int i) {
        return easeUserList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Viewholder viewholder=null;
        if(view==null){
            viewholder=new Viewholder();
            view= LayoutInflater.from(context).inflate(R.layout.userlist,null);
            viewholder.textView= (TextView) view.findViewById(R.id.text);
            view.setTag(viewholder);
        }else {
            viewholder= (Viewholder) view.getTag();
        }
        Demo demo=easeUserList.get(i);
        viewholder.textView.setText(demo.getName());
        return view;
    }

    public void setEaseUserList(List<Demo> easeUserList) {
        this.easeUserList = easeUserList;
    }

    class Viewholder{
        TextView textView;
        ImageView imageView;
    }

    public void delete(Demo demo){
        easeUserList.remove(demo);
        notifyDataSetChanged();
    }
    public void add(Demo demo){
        easeUserList.add(demo);
        notifyDataSetChanged();
    }
}
