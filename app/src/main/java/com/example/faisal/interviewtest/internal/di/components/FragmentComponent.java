/*
 * Copyright (c) 2017. Faisal Jamil
 */

package com.example.faisal.interviewtest.internal.di.components;

import com.example.faisal.interviewtest.feature.add.AddFragment;
import com.example.faisal.interviewtest.feature.list.ListFragment;
import com.example.faisal.interviewtest.internal.di.PerFragment;
import com.example.faisal.interviewtest.internal.di.modules.FragmentModule;

import dagger.Component;

@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    void inject(ListFragment fragment);
    void inject(AddFragment fragment);
}
