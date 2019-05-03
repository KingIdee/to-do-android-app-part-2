package com.auth0.todo.network;

public class NetworkState {

  private Status status;

  NetworkState(Status status){
    this.status = status;
  }

  public Status getStatus() {
    return status;
  }

}
