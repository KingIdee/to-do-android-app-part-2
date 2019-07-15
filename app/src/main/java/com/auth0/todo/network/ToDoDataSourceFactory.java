package com.auth0.todo.network;

import android.content.Context;

import com.auth0.todo.ToDoItem;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

public class ToDoDataSourceFactory extends DataSource.Factory<String, ToDoItem> {

  public MutableLiveData<ToDoDataSource> todoDataSource = new MutableLiveData<>();
  private ToDoDataSource source;

  public ToDoDataSourceFactory(Context context){
    source = new ToDoDataSource(context);
  }

  @NonNull
  @Override
  public DataSource<String, ToDoItem> create() {
    todoDataSource.postValue(source);
    return source;
  }
}