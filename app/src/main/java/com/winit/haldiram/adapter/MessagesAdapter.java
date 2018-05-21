package com.winit.haldiram.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.winit.haldiram.BR;
import com.winit.haldiram.R;
import com.winit.haldiram.dataobject.MessageDO;
import com.winit.haldiram.ui.activities.MessageConversationDetail;
import com.winit.haldiram.ui.activities.MessagesActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Srikanth on 5/9/2017.
 */

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

    private Context context;
    private List<MessageDO> messageDOs;
    private HashMap<String,ArrayList<MessageDO>> hmMessages;
    private String toId;

//    public MessagesAdapter(Context context, List<MessageDO> messageDOs) {
    public MessagesAdapter(Context context,HashMap<String,ArrayList<MessageDO>> hmMessages) {
        this.context = context;
//        this.messageDOs = messageDOs;
        this.hmMessages = hmMessages;
    }

    @Override
    public MessagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.messages_cell, parent, false);
        View view = binding.getRoot();
        view.setTag(getMessage(viewType));
        return new MessagesAdapter.ViewHolder(view);
    }

    private MessageDO getMessage(int position){
        ArrayList<String> keySet = new ArrayList<String>(hmMessages.keySet());
        String key = keySet.get(position);
        final MessageDO messageDO;
        if(hmMessages.containsKey(key) && hmMessages.get(key).size()>0)
            messageDO = hmMessages.get(key).get(0);
        else
            messageDO = new MessageDO();
        return messageDO;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(MessagesAdapter.ViewHolder holder, int position) {
        holder.bind(getMessage(position));
    }

    @Override
    public int getItemCount() {
//        if (messageDOs != null)
//            return messageDOs.size();
        if (hmMessages != null && hmMessages.size()>0)
            return hmMessages.size();
        return 0;
    }

//    public void refresh(List<MessageDO> messageDOs) {
    public void refresh(HashMap<String,ArrayList<MessageDO>> hmMessages) {
//        this.messageDOs = messageDOs;
        this.hmMessages = hmMessages;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;

//        public ViewHolder(ViewDataBinding binding) {
//            super(binding.getRoot());
//            this.binding = binding;
//        }

        public ViewHolder(final View itemView){
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Call MessageConversationDetail
                    MessageDO messageDO = (MessageDO) itemView.getTag();
                    Intent intent = new Intent(context,MessageConversationDetail.class);
                    intent.putExtra("messageDO",messageDO);
                    if(hmMessages!=null && hmMessages.containsKey(messageDO.toId))
                        intent.putExtra("arrMessages",hmMessages.get(messageDO.toId));
//                    intent.putExtra("obj",(CustomerMessageDO)v.getTag());
                    ((MessagesActivity)context).startActivityForResult(intent, 0);
                }
            });
        }

        public void bind(MessageDO messageDO) {
            binding.setVariable(BR.messageDO, messageDO);
            binding.executePendingBindings();
        }
    }
}
