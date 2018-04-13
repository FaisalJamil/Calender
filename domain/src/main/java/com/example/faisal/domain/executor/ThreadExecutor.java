/*
 * Copyright (c) 2018. Faisal Jamil
 */

package com.example.faisal.domain.executor;


import java.util.concurrent.Executor;

/**
 * Executor implementation can be based on different frameworks or techniques of asynchronous
 * execution, but every implementation will execute the executor out of the UI thread.
 */
public interface ThreadExecutor extends Executor {}