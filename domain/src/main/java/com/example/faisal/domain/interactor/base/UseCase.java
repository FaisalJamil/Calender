/*
 * Copyright (c) 2018. Faisal Jamil
 */

package com.example.faisal.domain.interactor.base;
/**
 * Created by faisal on 11/3/18.
 */

import com.example.faisal.domain.executor.PostExecutionThread;
import com.example.faisal.domain.executor.ThreadExecutor;

import dagger.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This interface represents a execution unit for different use cases (this means any use case
 * in the application should implement this contract).
 *
 * By convention each UseCase implementation will return the result using a {@link DisposableObserver}
 * that will execute its job in a background thread and will post the result in the UI thread.
 */
public abstract class UseCase<T, Params> {

  private final ThreadExecutor threadExecutor;
  private final PostExecutionThread postExecutionThread;
  private final CompositeDisposable disposables;
  private Consumer onSubscribe;
  private Action onTerminate;

  public UseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    this.threadExecutor = threadExecutor;
    this.postExecutionThread = postExecutionThread;
    this.disposables = new CompositeDisposable();
  }

  /**
   * Builds an {@link Observable} which will be used when executing the current {@link UseCase}.
   */
  public abstract Observable<T> buildUseCaseObservable(Params params);

  /**
   * Executes the current use case.
   *
   * @param observer {@link DisposableObserver} which will be listening to the observable build
   * by {@link #buildUseCaseObservable(Params)} ()} method.
   * @param params Parameters (Optional) used to build/execute this use case.
   */
  public void execute(DisposableObserver<T> observer, Params params) {
    Preconditions.checkNotNull(observer);
    final Observable<T> observable = this.buildUseCaseObservable(params)
            .subscribeOn(Schedulers.from(threadExecutor))
            .observeOn(postExecutionThread.getScheduler());

    if (onSubscribe != null) {
        observable.doOnSubscribe(onSubscribe);
    }
    if (onTerminate != null) {
        observable.doOnTerminate(onTerminate);
    }

    addDisposable(observable.subscribeWith(observer));
  }

  /**
   * Dispose from current {@link CompositeDisposable}.
   */
  public void dispose() {
    if (!disposables.isDisposed()) {
      disposables.dispose();
    }
  }

  /**
   * Dispose from current {@link CompositeDisposable}.
   */
  private void addDisposable(Disposable disposable) {
    Preconditions.checkNotNull(disposable);
    Preconditions.checkNotNull(disposables);
    disposables.add(disposable);
  }

  /**
   * Allows you to apply an Action to the Observable when it subscribes.
   *
   * @param action Action to be applied when the Observable subscribes.
   */
  final public void onSubscribe(final Consumer action) {
    this.onSubscribe = action;
  }

  /**
   * Allows you to apply an Action to the Observable when it terminates.
   *
   * This action will be fired always no mather whether an Exception happens.
   *
   * @param action Action to be applied when the Observable terminates.
   */
  final public void onTerminate(final Action action) {
    this.onTerminate = action;
  }
}