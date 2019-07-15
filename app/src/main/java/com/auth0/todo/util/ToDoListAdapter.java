package com.auth0.todo.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.auth0.todo.R;
import com.auth0.todo.ToDoItem;
import com.auth0.todo.network.Status;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class ToDoListAdapter extends PagedListAdapter<ToDoItem,RecyclerView.ViewHolder> {

    private Status networkState;
    private Consumer retryFunction;

    public ToDoListAdapter(@NonNull DiffUtil.ItemCallback<ToDoItem> diffCallback, Consumer function) {
        super(diffCallback);
        this.retryFunction = function;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType){

            case R.layout.to_do_item:
                View jobItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.to_do_item,parent,false);
                return new ToDoViewHolder(jobItemView);

            case R.layout.loading_item:
                View loadingItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_item,parent,false);
                return new LoaderViewHolder(loadingItemView,retryFunction);

        }

        throw new IllegalArgumentException("Wrong view type");

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {

            case R.layout.loading_item:
                ((LoaderViewHolder) holder).bind(networkState);
                break;

            case R.layout.to_do_item:
                ((ToDoViewHolder) holder).bind(getItem(position));
                break;


        }

    }

    private Boolean hasExtraRow(){
        return networkState!=null && networkState != Status.SUCCESS;
    }

    @Override
    public int getItemViewType(int position) {
        if(hasExtraRow() && position == getItemCount() - 1){
            return R.layout.loading_item;
        } else {
            return R.layout.to_do_item;
        }
    }

    @Override
    public int getItemCount() {
        int count = super.getItemCount();
        if(hasExtraRow()){
            count++;
        }
        return count;
    }


    public void updateNetworkState(Status networkState) {
        Status previousState = this.networkState;
        Boolean hadExtraRow = hasExtraRow();

        this.networkState = networkState;
        Boolean hasExtraRow = hasExtraRow();

        if (hadExtraRow!=hasExtraRow) {
            if (hadExtraRow){
                notifyDataSetChanged();
            } else {
                notifyItemInserted(super.getItemCount());
            }
        } else if (hasExtraRow && previousState!=networkState){
            notifyItemChanged(getItemCount() - 1);
        }

    }

}