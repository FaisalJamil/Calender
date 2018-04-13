/*
 * Copyright (c) 2018. Faisal Jamil
 */

package com.example.faisal.interviewtest.feature.add;

import com.example.faisal.domain.interactor.AddBirthDay;
import com.example.faisal.domain.interactor.GetBirthDayList;
import com.example.faisal.domain.models.BirthDayRecord;
import com.example.faisal.interviewtest.R;
import com.example.faisal.interviewtest.internal.di.PerFragment;
import com.example.faisal.interviewtest.internal.mvp.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;
import rx.SingleSubscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

@PerFragment
public class AddPresenter extends BasePresenter<AddView> {

    private AddBirthDay addBirthDayUseCase;

    @Inject
    public AddPresenter(AddBirthDay addBirthDay) {
        this.addBirthDayUseCase = addBirthDay;
    }

    @Override
    public void onViewCreated() {
    }

    public void addRecord(String name, String date) {
        BirthDayRecord birthDayRecord = new BirthDayRecord().with(name, date);

        addBirthDayUseCase.execute(new DisposableObserver<Long>() {
            @Override
            public void onNext(Long data) {
                if(data > 0){
                    getView().add(birthDayRecord);
                }
            }

            @Override
            public void onError(Throwable e) {
                getView().displayError(R.string.common_error);
            }

            @Override
            public void onComplete() {

            }
        }, birthDayRecord);
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
