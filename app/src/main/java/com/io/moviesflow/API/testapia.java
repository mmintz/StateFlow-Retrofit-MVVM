package com.io.moviesflow.API;

import io.reactivex.observers.DisposableObserver;

public class testapia {

    DisposableObserver observer = new DisposableObserver<String>(){
        @Override public void onNext(String s){}
        @Override public void onComplete() {}
        @Override public void onError(Throwable t) {}
    };
}
