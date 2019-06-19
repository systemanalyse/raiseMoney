package com.carolsum.jingle.ui.adapters;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.carolsum.jingle.R;
import com.carolsum.jingle.model.JNotification;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private List<JNotification> notificationList;

    public NotificationAdapter(List<JNotification> notificationList) {
        this.notificationList = notificationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notification_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        JNotification notification = notificationList.get(i);
        // 将 notification 实例上的数据绑定到 viewHolder 中
        switch (notification.getOrigin()) {
          case 0:
            viewHolder.originImage.setImageResource(R.drawable.publish_icon);
            break;
          case 1:
            viewHolder.originImage.setImageResource(R.drawable.accept_msg_icon);
            break;
          case 2:
            viewHolder.originImage.setImageResource(R.drawable.wallet_msg_icon);
            break;
        }

        viewHolder.titleText.setText(notification.getTitle());
        if (notification.isRead()) {
          viewHolder.titleText.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        } else {
          viewHolder.titleText.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        }

        if (notification.getType() == 0) {
          viewHolder.typeImage.setImageResource(R.drawable.pao);
        } else if (notification.getType() == 1) {
          viewHolder.typeImage.setImageResource(R.drawable.dian);
        } else {
          viewHolder.typeImage.setVisibility(View.GONE);
        }

        viewHolder.descText.setText(notification.getDesc());
        // todo@lijiehong 这里日期格式需要使用helper做一个转换
        viewHolder.dateText.setText(notification.getDate());
        viewHolder.markView.setVisibility(notification.isRead() ? View.INVISIBLE: View.VISIBLE);

        if (i == notificationList.size() - 1) {
          viewHolder.divider.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
          ImageView originImage;
          TextView titleText;
          ImageView typeImage;
          TextView descText;
          TextView dateText;
          View markView;
          View divider;

          public ViewHolder(View view) {
            super(view);
            originImage = view.findViewById(R.id.notification_origin);
            titleText = view.findViewById(R.id.message_title);
            typeImage = view.findViewById(R.id.message_type);
            descText = view.findViewById(R.id.message_desc);
            dateText = view.findViewById(R.id.message_date);
            markView = view.findViewById(R.id.new_message_mark);
            divider = view.findViewById(R.id.item_divider);
          }
      }
}
