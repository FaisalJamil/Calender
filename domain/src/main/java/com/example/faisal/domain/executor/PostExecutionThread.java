/*
 * Copyright (c) 2018. Faisal Jamil
 */

package com.example.faisal.domain.executor;

/**
 * Created by faisal on 11/3/18.
 */

import io.reactivex.Scheduler;

/**
 * Thread abstraction created to change the execution context from any thread to any other thread.
 * Useful to encapsulate a UI Thread for example, since some job will be done in background, an
 * implementation of this interface will change context and update the UI.
 */
public interface PostExecutionThread {
    Scheduler getScheduler();
}