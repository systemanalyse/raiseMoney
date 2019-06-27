package com.carolsum.jingle.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.carolsum.jingle.R;
import com.carolsum.jingle.model.Assignment;
import com.carolsum.jingle.model.Receipt;
import com.carolsum.jingle.net.HttpClient;
import com.liulishuo.magicprogresswidget.MagicProgressBar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AcceptorAdapter extends RecyclerView.Adapter<AcceptorAdapter.ViewHolder> {

  private Context context;
  private List<Receipt> receiptList;
  private Boolean select = false;

  DateFormat dateFormatTime = new SimpleDateFormat("HH:mm");

  public AcceptorAdapter(Context context, List<Receipt> receiptList) {
    this.context = context;
    this.receiptList = receiptList;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.receipt_list_item, viewGroup, false);
    AcceptorAdapter.ViewHolder holder = new AcceptorAdapter.ViewHolder(view);
    return holder;
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
    Receipt receipt = receiptList.get(i);


    if (select && receipt.getWhetherConfirm() == 0) {
      viewHolder.checkBox.setVisibility(View.VISIBLE);
    } else {
      viewHolder.checkBox.setVisibility(View.GONE);
    }
    if (receipt.getUserinfo().getAvatarURL() != null && !receipt.getUserinfo().getAvatarURL().equals("") && !receipt.getUserinfo().getAvatarURL().equals("undefined")) {
      // 加载用户头像
      Glide.with(context).load(HttpClient.getPictureBaseUrl + receipt.getUserinfo().getAvatarURL()).into(viewHolder.userAvatar);
    } else {
      // 加载默认头像
      Glide.with(context).load(R.drawable.default_avatar).into(viewHolder.userAvatar);
    }
    viewHolder.finishtime.setText(dateFormatTime.format(Long.parseLong(receipt.getFinishtime())));


//    if (onItemClickListener != null) {
//      viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//          onItemClickListener.onItemClick(viewHolder.getAdapterPosition());
//        }
//      });
//    }
  }

  @Override
  public int getItemCount() {
    return receiptList.size();
  }

  static class ViewHolder extends RecyclerView.ViewHolder {
    CheckBox checkBox;
    CircleImageView userAvatar;
    TextView username;
    TextView finishtime;

    public ViewHolder(View view) {
      super(view);
      checkBox = view.findViewById(R.id.checkbox);
      userAvatar = view.findViewById(R.id.user_avatar);
      username = view.findViewById(R.id.username);
      finishtime = view.findViewById(R.id.finishtime);
    }
  }

//  public interface OnItemClickListener{
//    void onItemClick(int position);
//  }
//
//  private OnItemClickListener onItemClickListener;
//
//  public void setOnItemClickListener(OnItemClickListener onItemClickListener){
//    this.onItemClickListener = onItemClickListener;
//  }

}




