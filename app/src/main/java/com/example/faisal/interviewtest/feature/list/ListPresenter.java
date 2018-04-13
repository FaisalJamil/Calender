/*
 * Copyright (c) 2017. Faisal Jamil
 */

package com.example.faisal.interviewtest.feature.list;

import com.example.faisal.domain.models.BirthDayRecord;
import com.example.faisal.domain.interactor.AddBirthDay;
import com.example.faisal.domain.interactor.GetBirthDayList;
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
public class ListPresenter extends BasePresenter<ListView> {

    private GetBirthDayList getBirthDayListUseCase;

    @Inject
    public ListPresenter(GetBirthDayList getBirthDayList) {
        this.getBirthDayListUseCase = getBirthDayList;
        attachLoading(this.getBirthDayListUseCase);
    }

    @Override
    public void onViewCreated() {
        getBirthDayListUseCase.execute(new DisposableObserver<List<BirthDayRecord>>() {
            @Override
            public void onNext(List<BirthDayRecord> birthDayRecords) {
                getView().render(birthDayRecords);
            }

            @Override
            public void onError(Throwable error) {

                getView().displayError(R.string.common_error);
            }

            @Override
            public void onComplete() {

            }
        }, null);
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
