/*
 * Copyright (c) 2017. Faisal Jamil
 */

package com.example.faisal.interviewtest.internal.di.components;

import android.content.Context;

import com.example.faisal.domain.executor.PostExecutionThread;
import com.example.faisal.domain.executor.ThreadExecutor;
import com.example.faisal.domain.repository.BirthDayRepository;
import com.example.faisal.interviewtest.DispatcherActivity;
import com.example.faisal.interviewtest.internal.di.modules.ApplicationModule;
import com.example.faisal.interviewtest.util.Navigator;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component( modules = ApplicationModule.class )
public interface ApplicationComponent {
    void inject(DispatcherActivity activity);
    Context context();
    Navigator navigator();
    @Named("SubscriberThread") ThreadExecutor provideSubscriberThread();
    @Named("ObserverThread") PostExecutionThread provideObserverThread();
    @Named("ConcurrentExecutor") PostExecutionThread provideConcurrentExecutor();
    @Named("AvailableProcessors") int provideAvailableProcessors();
    BirthDayRepository BirthDayRepository();
}
