package azsecuer.zhuoxin.com.easeuidemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.easeui.domain.EaseUser;

import java.util.Date;
import java.util.List;

import azsecuer.zhuoxin.com.easeuidemo.R;

/**
 * Created by Administrator on 2017/3/5.
 */

public class Listadapter extends BaseAdapter {
    private List<Demo> list;
    private Context context;

    public Listadapter(List<Demo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
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
            view= LayoutInflater.from(context).inflate(R.layout.addlistitem,null);
            viewholder.image= (ImageView) view.findViewById(R.id.item_iv);
            viewholder.id= (TextView) view.findViewById(R.id.tv_id);
            viewholder.msg= (TextView) view.findViewById(R.id.tv_masg);
            view.setTag(viewholder);
        }else {
            viewholder= (Viewholder) view.getTag();
        }
        viewholder.id.setText(list.get(i).name);
        viewholder.msg.setText(list.get(i).msg);
        return view;
    }

    class Viewholder{
        ImageView image;
        TextView id,msg;
    }


    //添加好友
    private void add(Demo demo){
        if(list!=null){
            list.add(demo);
            notifyDataSetChanged();
        }

    }
}
