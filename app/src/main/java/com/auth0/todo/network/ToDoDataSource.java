package com.auth0.todo.network;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.auth0.todo.ToDoItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

public class ToDoDataSource extends PageKeyedDataSource<String, ToDoItem> {

  private int pageCount = 1;
  private String url = "http://10.0.2.2:3001";
  private RequestQueue queue;
  public MutableLiveData<Status> status = new MutableLiveData<>();
  private Consumer retry;


  public ToDoDataSource(Context context) {
    queue = Volley.newRequestQueue(context);
  }

  @Override
  public void loadInitial(@NonNull PageKeyedDataSource.LoadInitialParams<String> params, @NonNull final LoadInitialCallback<String, ToDoItem> callback) {

    status.postValue(Status.LOADING);
    JsonArrayRequest microPostsRequest = new JsonArrayRequest(url, response -> {
      pageCount++;
      callback.onResult(transformResponse(response), null, String.valueOf(pageCount));
      status.postValue(Status.SUCCESS);


    }, error -> {
      status.postValue(Status.FAILED);
      retry = x -> loadInitial(params, callback);
    });

    queue.add(microPostsRequest);

  }

  @Override
  public void loadBefore(@NonNull LoadParams<String> params, @NonNull final LoadCallback<String, ToDoItem> callback) {
  }

  @Override
  public void loadAfter(@NonNull LoadParams<String> params, @NonNull final LoadCallback<String, ToDoItem> callback) {

    status.postValue(Status.LOADING);
    String updatedUrl = url + "?page=" + pageCount;
    JsonArrayRequest microPostsRequest = new JsonArrayRequest(updatedUrl, response -> {
      pageCount++;
      callback.onResult(transformResponse(response), String.valueOf(pageCount));
      status.postValue(Status.SUCCESS);

    }, error -> {
      status.postValue(Status.FAILED);
      retry = x -> loadAfter(params, callback);
    });

    queue.add(microPostsRequest);


  }

  private List<ToDoItem> transformResponse(JSONArray response) {

    List<ToDoItem> toDoItems = new ArrayList<>(response.length());
    for (int i = 0; i < response.length(); i++) {
      JSONObject item;
      String id = null;
      String message = null;
      try {
        item = response.getJSONObject(i);
        id = item.getString("_id");
        message = item.getString("message");
      } catch (JSONException e) {
        e.printStackTrace();
      }

      toDoItems.add(new ToDoItem(id, message));
    }

    return toDoItems;

  }

  public void retryFailedRequest() {
    Consumer previousRetry = retry;
    retry = null;
    if (previousRetry != null) {
      previousRetry.accept(null);
    }

  }


}
