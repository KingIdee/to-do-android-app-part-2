package com.auth0.todo.util;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.auth0.todo.R;
import com.auth0.todo.network.NetworkState;
import com.auth0.todo.network.Status;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.RecyclerView;


public class LoaderViewHolder extends RecyclerView.ViewHolder {

    private ProgressBar progressBar = itemView.findViewById(R.id.progress_bar);
    private Button retryButton = itemView.findViewById(R.id.retry_button);

    public LoaderViewHolder(@NonNull View itemView, Consumer retryFunction) {
        super(itemView);
        retryButton.setOnClickListener(v -> retryFunction.accept(null));
    }

    public void bind(NetworkState networkState){

        if(networkState.getStatus()==Status.LOADING){
            progressBar.setVisibility(View.VISIBLE);
            retryButton.setVisibility(View.GONE);
        } else if(networkState.getStatus()== Status.SUCCESS){
            progressBar.setVisibility(View.GONE);
            retryButton.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            retryButton.setVisibility(View.VISIBLE);
        }

    }

}
