package com.auth0.todo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.auth0.todo.identity.AuthAwareActivity;
import com.auth0.todo.network.Status;
import com.auth0.todo.network.ToDoDataSourceFactory;
import com.auth0.todo.util.DiffUtilCallback;
import com.auth0.todo.util.ToDoListAdapter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AuthAwareActivity {

    private ToDoListAdapter toDoListAdapter;
    ToDoDataSourceFactory factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        factory =  new ToDoDataSourceFactory(this);

        // create and configure the adapter
        this.toDoListAdapter = new ToDoListAdapter(new DiffUtilCallback(), x -> factory.todoDataSource.getValue().retryFailedRequest());
        RecyclerView microPostsListView = findViewById(R.id.to_do_items);
        microPostsListView.setLayoutManager(new LinearLayoutManager(this));
        microPostsListView.setAdapter(toDoListAdapter);

        // setup paged-list
        PagedList.Config config = new PagedList.Config.Builder()
            .setPageSize(15)
            .setEnablePlaceholders(false)
            .build();

        LivePagedListBuilder livePagedListBuilder = new LivePagedListBuilder<>(factory, config);
        LiveData<PagedList> listLiveData = livePagedListBuilder.build();

        listLiveData.observe(this, toDoListAdapter::submitList);

        LiveData<Status> networkStateLiveData =
            Transformations.switchMap(factory.todoDataSource, input -> input.status);

        networkStateLiveData.observe(this, toDoListAdapter::updateNetworkState);

    }


    public void openToDoForm(View view) {
        if (authenticationHandler.hasValidCredentials()) {
            startActivity(new Intent(this, ToDoFormActivity.class));
        }
    }
}