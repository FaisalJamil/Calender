/*
 * Copyright (c) 2017. Faisal Jamil
 */

package com.example.faisal.domain.interactor;

import com.example.faisal.domain.executor.PostExecutionThread;
import com.example.faisal.domain.models.BirthDayRecord;
import com.example.faisal.domain.executor.ThreadExecutor;
import com.example.faisal.domain.interactor.base.UseCase;
import com.example.faisal.domain.repository.BirthDayRepository;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Use case adds an {@link BirthDayRecord }.
 */
public class AddBirthDay extends UseCase<Long, BirthDayRecord> {

    private final BirthDayRepository birthDayRepository;

    @Inject
    public AddBirthDay(BirthDayRepository birthDayRepository,
        @Named("SubscriberThread") ThreadExecutor threadExecutor,
        @Named("ObserverThread") PostExecutionThread postExecutionThread) {
            super(threadExecutor, postExecutionThread);
        this.birthDayRepository = birthDayRepository;
    }

    /**
     * We leave the repository the responsibility of the request, and once we get the added record
     * we just return.
     *
     * @return Observable of the record to be added
     */
    @Override
    public Observable<Long> buildUseCaseObservable(BirthDayRecord birthDayRecord) {
        Preconditions.checkNotNull(birthDayRecord);
        //if(birthDayRecord == null) return Observable.error(new Throwable());
        Single<Long> result = this.birthDayRepository.addRecord(birthDayRecord);
        if(result != null)
            return result.toObservable();
        else
            return null;
    }
}
