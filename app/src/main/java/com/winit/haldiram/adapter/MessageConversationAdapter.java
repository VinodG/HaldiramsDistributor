package com.winit.haldiram.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.winit.haldiram.BR;
import com.winit.haldiram.R;
import com.winit.haldiram.dataobject.MessageDO;

import java.util.List;

/**
 * Created by Srikanth on 5/9/2017.
 */

public class MessageConversationAdapter extends RecyclerView.Adapter<MessageConversationAdapter.ViewHolder> {

    private Context context;
    private List<MessageDO> messageDOs;
    String userId;

    public MessageConversationAdapter(Context context, List<MessageDO> messageDOs,String userId) {
        this.context = context;
        this.messageDOs = messageDOs;
        this.userId = userId;
    }

    @Override
    public MessageConversationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.from(parent.getContext()).inflate(R.layout.message_conversation_cell, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MessageConversationAdapter.ViewHolder holder, int position) {
        final MessageDO messageDO = messageDOs.get(position);
        if(messageDO.fromId.trim().equalsIgnoreCase(userId)){
            holder.llSent.setVisibility(View.VISIBLE);
            holder.llReceived.setVisibility(View.GONE);
        }
        else{
            holder.llSent.setVisibility(View.GONE);
            holder.llReceived.setVisibility(View.VISIBLE);
        }
        holder.tvMessageBodySent.setText(messageDO.message);
        holder.tvMessageTimeSent.setText(messageDO.messageDate);
        holder.tvMessageBodyReceived.setText(messageDO.message);
        holder.tvMessageTimeReceived.setText(messageDO.messageDate);
    }

    @Override
    public int getItemCount() {
        if (messageDOs != null)
            return messageDOs.size();
        return 0;
    }

    public void refresh(List<MessageDO> messageDOs) {
        this.messageDOs = messageDOs;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
//        private final ViewDataBinding binding;
        private TextView tvMessageBodySent,tvMessageTimeSent,tvMessageBodyReceived,tvMessageTimeReceived;
        private LinearLayout llSent,llReceived;


        public ViewHolder(View itemView){
            super(itemView);
            tvMessageBodySent = (TextView) itemView.findViewById(R.id.tvMessageBodySent);
            tvMessageTimeSent = (TextView) itemView.findViewById(R.id.tvMessageTimeSent);
            tvMessageBodyReceived = (TextView) itemView.findViewById(R.id.tvMessageBodyReceived);
            tvMessageTimeReceived = (TextView) itemView.findViewById(R.id.tvMessageTimeReceived);

            llSent = (LinearLayout) itemView.findViewById(R.id.ll_send);
            llReceived = (LinearLayout) itemView.findViewById(R.id.ll_receive);
//            binding = DataBindingUtil.bind(itemView);
        }

    }
}
