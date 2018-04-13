/*
 * Copyright (c) 2018. Faisal Jamil
 */

package com.example.faisal.domain.interactor;


import io.reactivex.observers.DisposableObserver;

public class TestDisposableObserver<T> extends DisposableObserver<T> {
    private int valuesCount = 0;

    @Override public void onNext(T value) {
        valuesCount++;
    }

    @Override public void onError(Throwable e) {
        // no-op by default.
    }

    @Override public void onComplete() {
        // no-op by default.
    }
}