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

import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseConversationListFragment;

import azsecuer.zhuoxin.com.easeuidemo.R;
import azsecuer.zhuoxin.com.easeuidemo.activity.chatActivity;

/**
 * Created by Administrator on 2017/3/2.
 */

public class address_listfragment extends EaseConversationListFragment {
    @Override
    protected void initView() {
        super.initView();
        query.setVisibility(View.GONE);
        clearSearch.setVisibility(View.GONE);
       setConversationListItemClickListener(listener);
    }

    EaseConversationListItemClickListener listener=new EaseConversationListItemClickListener() {
        @Override
        public void onListItemClicked(EMConversation conversation) {
            Intent intent=new Intent(getContext(),chatActivity.class);
            intent.putExtra(EaseConstant.EXTRA_USER_ID, conversation.conversationId());
            intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EMMessage.ChatType.Chat);
            startActivity(intent);
        }

//        @Override
//        public void onListItemLongClicked(EMConversation conversation) {
//
//            conversationList.remove(conversation);
//        }
    };

}
