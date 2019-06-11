package com.carolsum.jingle.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.carolsum.jingle.R;
import com.carolsum.jingle.model.Assignment;
import com.liulishuo.magicprogresswidget.MagicProgressBar;

import java.util.List;

public class SpaceAssignmentAdapter extends RecyclerView.Adapter<SpaceAssignmentAdapter.ViewHolder> {

  private List<Assignment> assignmentList;

  public SpaceAssignmentAdapter(List<Assignment> assignmentList) {
    this.assignmentList = assignmentList;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.space_assignment_item, viewGroup, false);
    SpaceAssignmentAdapter.ViewHolder holder = new SpaceAssignmentAdapter.ViewHolder(view);
    return holder;
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
    Assignment assignment = assignmentList.get(i);
    if (assignment.getType() == 0) {
      // paopao
      viewHolder.assignmentTypeImage.setImageResource(R.drawable.home);
      viewHolder.startPos.setText(assignment.getStartPos());
      viewHolder.endPos.setText(assignment.getEndPos());
      viewHolder.ddl.setText(assignment.getTime());
      viewHolder.paopaoInfoLayout.setVisibility(View.VISIBLE);
      viewHolder.diandianInfoLayout.setVisibility(View.GONE);
    } else {
      // diandian
      viewHolder.assignmentTypeImage.setImageResource(R.drawable.publish);
      viewHolder.progressBar.setSmoothPercent((float) assignment.getFinishNum() / assignment.getTotalNum());
      viewHolder.assignmentProgressInfo.setText(Integer.toString(assignment.getFinishNum()) + "/" + Integer.toString(assignment.getTotalNum()));
      viewHolder.paopaoInfoLayout.setVisibility(View.GONE);
      viewHolder.diandianInfoLayout.setVisibility(View.VISIBLE);
    }
    viewHolder.assignmentTitle.setText(assignment.getTitle());
    viewHolder.assignmentStatus.setText(assignment.getStatus());
    viewHolder.assignmentTime.setText(assignment.getTime());

  }

  @Override
  public int getItemCount() {
    return assignmentList.size();
  }

  static class ViewHolder extends RecyclerView.ViewHolder {
    ImageView assignmentTypeImage;
    TextView assignmentTitle;
    TextView assignmentStatus;
    LinearLayout paopaoInfoLayout;
    LinearLayout diandianInfoLayout;
    TextView startPos;
    TextView endPos;
    TextView ddl;
    MagicProgressBar progressBar;
    TextView assignmentTime;
    TextView assignmentProgressInfo;

    public ViewHolder(View view) {
      super(view);
      assignmentTypeImage = view.findViewById(R.id.assignment_type);
      assignmentTitle = view.findViewById(R.id.assignment_title);
      assignmentStatus = view.findViewById(R.id.assignment_status);
      paopaoInfoLayout = view.findViewById(R.id.paopao_info);
      diandianInfoLayout = view.findViewById(R.id.diandian_info);
      startPos = view.findViewById(R.id.assignment_start_position);
      endPos = view.findViewById(R.id.assignment_end_position);
      ddl = view.findViewById(R.id.assignment_ddl);
      progressBar = view.findViewById(R.id.assignment_process);
      assignmentTime = view.findViewById(R.id.assignment_time);
      assignmentProgressInfo = view.findViewById(R.id.assignment_progress_info);
    }
  }
}
