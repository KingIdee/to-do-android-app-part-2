package com.auth0.todo.util;

import android.view.View;
import android.widget.TextView;

import com.auth0.todo.R;
import com.auth0.todo.ToDoItem;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class ToDoViewHolder extends RecyclerView.ViewHolder {

  private TextView textView;

  public ToDoViewHolder(@NonNull View itemView) {
    super(itemView);
    textView = itemView.findViewById(R.id.to_do_message);
  }

  public void bind(ToDoItem toDoItem){
    textView.setText(toDoItem.getMessage());
  }

}