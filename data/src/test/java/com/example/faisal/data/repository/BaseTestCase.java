/*
 * Copyright (c) 2017. Faisal Jamil
 */

package com.example.faisal.data.repository;

import android.support.annotation.CallSuper;
import android.test.AndroidTestCase;

import java.util.concurrent.Callable;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.mockwebserver.MockWebServer;

class BaseTestCase extends AndroidTestCase{

    MockWebServer server;

    @CallSuper
    public void setUp() throws Exception {
        server = new MockWebServer();
        // Emit immediately
//        RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook() {
//            @Override
//            public Scheduler getMainThreadScheduler() {
//                return Schedulers.immediate();
//            }
//        });
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(
                __ -> Schedulers.trampoline());
    }

    @CallSuper
    public void tearDown() throws Exception {
        server.shutdown();
        //RxAndroidPlugins.getInstance().reset();
        RxAndroidPlugins.reset();
    }
}
