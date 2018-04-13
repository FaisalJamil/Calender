/*
 * Copyright (c) 2017. Faisal Jamil
 */

package com.example.faisal.interviewtest.internal.di.modules;

import android.content.Context;
import android.support.annotation.VisibleForTesting;

import com.example.faisal.data.repository.BirthDayRepositoryImpl;
import com.example.faisal.data.service.adapter.RestFactory;
import com.example.faisal.data.service.adapter.RetrofitFactory;
import com.example.faisal.domain.executor.JobExecutor;
import com.example.faisal.domain.executor.PostExecutionThread;
import com.example.faisal.domain.executor.ThreadExecutor;
import com.example.faisal.domain.repository.BirthDayRepository;
import com.example.faisal.interviewtest.AndroidApplication;
import com.example.faisal.interviewtest.BuildConfig;
import com.example.faisal.data.service.adapter.LocalDbHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static android.support.annotation.VisibleForTesting.PACKAGE_PRIVATE;

@Module
public class ApplicationModule {
    private final AndroidApplication application;

    public ApplicationModule(AndroidApplication application) {
        this.application = application;
    }

    @Provides @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides @Singleton @Named("SubscriberThread")
    ThreadExecutor provideSubscriberThread(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides @Singleton @Named("ObserverThread")
    PostExecutionThread provideObserverThread() {
        return AndroidSchedulers::mainThread;
    }

    @VisibleForTesting(otherwise = PACKAGE_PRIVATE)
    @Provides @Singleton @Named("ConcurrentExecutor")
    public PostExecutionThread provideConcurrentExecutor(@Named("AvailableProcessors") int threads) {
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        return () -> Schedulers.from(executor);
    }

    @Provides @Singleton @Named("AvailableProcessors")
    int provideAvailableProcessors() {
        return Runtime.getRuntime().availableProcessors() + 1;
    }

    @Provides @Singleton
    RestFactory provideRestFactory(RetrofitFactory factory) {
        return factory;
    }

    @Provides @Singleton @Named("ApiUrl")
    String provideApiUrl() {
        return BuildConfig.API_URL;
    }

    @Provides @Singleton
    BirthDayRepository providebirthDayRepository(BirthDayRepositoryImpl birthDayRepository){
        return birthDayRepository;
    }

//    @Provides @Singleton
//    LocalDbHandler provideDbHandler(LocalDbHandler localDbHandler){
//        return localDbHandler;
//    }
}
