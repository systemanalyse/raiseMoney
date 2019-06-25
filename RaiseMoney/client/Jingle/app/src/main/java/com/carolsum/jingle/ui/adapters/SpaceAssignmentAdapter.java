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
    if (assignment.getTaskType() == 1) {
      // paopao
      viewHolder.assignmentTypeImage.setImageResource(R.drawable.pao);
      viewHolder.startPos.setText(assignment.getStartPosition());
      viewHolder.endPos.setText(assignment.getEndPosition());
      viewHolder.ddl.setText(assignment.getDdl());
      viewHolder.paopaoInfoLayout.setVisibility(View.VISIBLE);
      viewHolder.diandianInfoLayout.setVisibility(View.GONE);
    } else {
      // diandian
      viewHolder.assignmentTypeImage.setImageResource(R.drawable.dian);
      viewHolder.progressBar.setSmoothPercent((float) assignment.getFinishNum() / assignment.getTotalNum());
      viewHolder.assignmentProgressInfo.setText(Integer.toString(assignment.getFinishNum()) + "/" + Integer.toString(assignment.getTotalNum()));
      viewHolder.paopaoInfoLayout.setVisibility(View.GONE);
      viewHolder.diandianInfoLayout.setVisibility(View.VISIBLE);
    }
    viewHolder.assignmentTitle.setText(assignment.getTitle());
    switch (assignment.getStatusCode()) {
      case 0:
        viewHolder.assignmentStatus.setImageResource(R.drawable.state_wait_for_order);
        break;
      case 1:
        viewHolder.assignmentStatus.setImageResource(R.drawable.state_running);
        break;
      case 2:
        viewHolder.assignmentStatus.setImageResource(R.drawable.state_unconfirm);
        break;
      case 3:
        viewHolder.assignmentStatus.setImageResource(R.drawable.state_finish);
        break;
      case 4:
        viewHolder.assignmentStatus.setImageResource(R.drawable.state_overdue);
        break;
      case 5:
        viewHolder.assignmentStatus.setImageResource(R.drawable.state_not_on_time);
        break;
    }
    viewHolder.assignmentTime.setText(assignment.getDdl());
  }

  @Override
  public int getItemCount() {
    return assignmentList.size();
  }

  static class ViewHolder extends RecyclerView.ViewHolder {
    ImageView assignmentTypeImage;
    TextView assignmentTitle;
    ImageView assignmentStatus;
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
