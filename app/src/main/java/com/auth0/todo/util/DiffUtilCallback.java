package com.auth0.todo.util;

import com.auth0.todo.ToDoItem;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class DiffUtilCallback extends DiffUtil.ItemCallback<ToDoItem> {

  @Override
  public boolean areItemsTheSame(@NonNull ToDoItem oldItem, @NonNull ToDoItem newItem) {
    return oldItem.getId().equals(newItem.getId());
  }

  @Override
  public boolean areContentsTheSame(@NonNull ToDoItem oldItem, @NonNull ToDoItem newItem) {

    return oldItem.getId().equals(newItem.getId()) &&
        oldItem.getMessage().equals(newItem.getMessage());
  }

}
