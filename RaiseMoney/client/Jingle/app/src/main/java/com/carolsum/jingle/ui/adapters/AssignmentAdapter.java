package com.carolsum.jingle.ui.adapters;

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
    viewHolder.assignmentStatus.setText(assignment.getStatus());
    viewHolder.assignmentValue.setText(Integer.toString(assignment.getValue()));
    viewHolder.assignmentTime.setText(assignment.getTime());
    if (assignment.getType() == 0) {
      viewHolder.assignmentTypeImage.setImageResource(R.drawable.home);
    } else {
      viewHolder.assignmentTypeImage.setImageResource(R.drawable.publish);
    }
  }

  @Override
  public int getItemCount() {
    return assignmentList.size();
  }

  static class ViewHolder extends RecyclerView.ViewHolder {
    ImageView assignmentTypeImage;
    TextView assignmentTitle;
    TextView assignmentStatus;
    TextView assignmentValue;
    TextView assignmentTime;

    public ViewHolder(View view) {
      super(view);
      assignmentTypeImage = view.findViewById(R.id.assignment_type);
      assignmentTitle = view.findViewById(R.id.assignment_title);
      assignmentStatus = view.findViewById(R.id.assignment_status);
      assignmentValue = view.findViewById(R.id.assignment_value);
      assignmentTime = view.findViewById(R.id.assignment_time);
    }
  }

}
