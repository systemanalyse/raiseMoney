package com.carolsum.jingle.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.carolsum.jingle.R;
import com.carolsum.jingle.model.Assignment;
import com.liulishuo.magicprogresswidget.MagicProgressBar;

import java.util.List;

public class AcceptorAdapter extends RecyclerView.Adapter<AcceptorAdapter.ViewHolder> {

    private List<Assignment> assignmentList;

    public AcceptorAdapter(List<Assignment> assignmentList) {
        this.assignmentList = assignmentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_assignment_list_item, viewGroup, false);
        AcceptorAdapter.ViewHolder holder = new AcceptorAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Assignment assignment = assignmentList.get(i);
        if (assignment.getType() == 0) {
            // paopao
            viewHolder.startPos.setText(assignment.getStartPos());
            viewHolder.endPos.setText(assignment.getEndPos());
            viewHolder.ddl.setText(assignment.getTime());
            viewHolder.paopaoInfoLayout.setVisibility(View.VISIBLE);
            viewHolder.diandianInfoLayout.setVisibility(View.GONE);
        } else {
            // diandian
            viewHolder.progressBar.setSmoothPercent((float) assignment.getFinishNum() / assignment.getTotalNum());
            viewHolder.assignmentProgressInfo.setText(Integer.toString(assignment.getFinishNum()) + "/" + Integer.toString(assignment.getTotalNum()));
            viewHolder.paopaoInfoLayout.setVisibility(View.GONE);
            viewHolder.diandianInfoLayout.setVisibility(View.VISIBLE);
        }
        viewHolder.assignmentTitle.setText(assignment.getTitle());
        if (i == assignmentList.size() - 1) {
            viewHolder.splitLine.setVisibility(View.GONE);
        }
        viewHolder.assignmentTime.setText(assignment.getTime());
        viewHolder.assignmentValue.setText(assignment.getValue() + "");

        if (onItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(viewHolder.getAdapterPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return assignmentList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView assignmentTitle;
        LinearLayout paopaoInfoLayout;
        LinearLayout diandianInfoLayout;
        TextView startPos;
        TextView endPos;
        TextView ddl;
        MagicProgressBar progressBar;
        TextView assignmentTime;
        TextView assignmentProgressInfo;
        TextView assignmentValue;
        View splitLine;

        public ViewHolder(View view) {
            super(view);
            assignmentTitle = view.findViewById(R.id.assignment_title);
            paopaoInfoLayout = view.findViewById(R.id.paopao_info);
            diandianInfoLayout = view.findViewById(R.id.diandian_info);
            startPos = view.findViewById(R.id.assignment_start_position);
            endPos = view.findViewById(R.id.assignment_end_position);
            ddl = view.findViewById(R.id.assignment_ddl);
            progressBar = view.findViewById(R.id.assignment_process);
            assignmentTime = view.findViewById(R.id.assignment_time);
            assignmentProgressInfo = view.findViewById(R.id.assignment_progress_info);
            assignmentValue = view.findViewById(R.id.tv_value);
            splitLine = view.findViewById(R.id.split_line);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

}




