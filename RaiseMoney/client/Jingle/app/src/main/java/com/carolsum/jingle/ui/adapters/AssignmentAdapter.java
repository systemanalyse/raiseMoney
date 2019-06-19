package com.carolsum.jingle.ui.adapters;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.carolsum.jingle.R;
import com.carolsum.jingle.model.Assignment;

import java.util.List;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.ViewHolder> {

  private List<Assignment> assignmentList;

  public AssignmentAdapter(List<Assignment> assignmentList) {
    this.assignmentList = assignmentList;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_item, viewGroup, false);
    AssignmentAdapter.ViewHolder holder = new AssignmentAdapter.ViewHolder(view);
    return holder;
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
    Assignment assignment = assignmentList.get(i);
    viewHolder.assignmentTitle.setText(assignment.getTitle());
    switch (assignment.getStatus()) {
      case 0:
        viewHolder.assignmentStatus.setImageResource(R.drawable.state_unconfirm);
        break;
      case 1:
        viewHolder.assignmentStatus.setImageResource(R.drawable.state_wait_for_order);
        break;
      case 2:
        viewHolder.assignmentStatus.setImageResource(R.drawable.state_accepted);
        break;
      case 3:
        viewHolder.assignmentStatus.setImageResource(R.drawable.state_running);
        break;
      case 4:
        viewHolder.assignmentStatus.setImageResource(R.drawable.state_finish);
        break;
      case 5:
        viewHolder.assignmentStatus.setImageResource(R.drawable.state_overdue);
        break;
      case 6:
        viewHolder.assignmentStatus.setImageResource(R.drawable.state_not_on_time);
        break;
    }
    int value = assignment.getValue();
    if (value < 0) {
      viewHolder.assignmentValue.setText("- " + Integer.toString(-value));
    } else {
      viewHolder.assignmentValue.setText("+ " + Integer.toString(value));
    }
    viewHolder.assignmentTime.setText(assignment.getTime());
    if (assignment.getType() == 0) {
      viewHolder.assignmentTypeImage.setImageResource(R.drawable.pao);
    } else {
      viewHolder.assignmentTypeImage.setImageResource(R.drawable.dian);
    }

    if (i == assignmentList.size() - 1) {
      viewHolder.divider.setVisibility(View.GONE);
    }
  }

  @Override
  public int getItemCount() {
    return assignmentList.size();
  }

  static class ViewHolder extends RecyclerView.ViewHolder {
    ImageView assignmentTypeImage;
    TextView assignmentTitle;
    ImageView assignmentStatus;
    TextView assignmentValue;
    TextView assignmentTime;
    View divider;

    public ViewHolder(View view) {
      super(view);
      assignmentTypeImage = view.findViewById(R.id.assignment_type);
      assignmentTitle = view.findViewById(R.id.assignment_title);
      assignmentStatus = view.findViewById(R.id.assignment_status);
      assignmentValue = view.findViewById(R.id.assignment_value);
      assignmentTime = view.findViewById(R.id.assignment_time);
      divider = view.findViewById(R.id.assignment_divider);
    }
  }
}
