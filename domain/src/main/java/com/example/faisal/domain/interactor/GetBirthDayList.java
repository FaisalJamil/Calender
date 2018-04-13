/*
 * Copyright (c) 2017. Faisal Jamil
 */

package com.example.faisal.domain.interactor;

import com.example.faisal.domain.executor.PostExecutionThread;
import com.example.faisal.domain.models.BirthDayRecord;
import com.example.faisal.domain.executor.ThreadExecutor;
import com.example.faisal.domain.interactor.base.UseCase;
import com.example.faisal.domain.repository.BirthDayRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Use case that retrieves a List of {@link BirthDayRecord}s.
 */
public class GetBirthDayList extends UseCase<List<BirthDayRecord>, Void> {

    private final BirthDayRepository birthDayRepository;

    @Inject
    public GetBirthDayList(BirthDayRepository birthDayRepository,
                           @Named("SubscriberThread") ThreadExecutor threadExecutor,
                           @Named("ObserverThread") PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.birthDayRepository = birthDayRepository;
    }

    /**
     * We make a call to the birthday records endpoint, and we get the list of all items,
     *
     * @param aVoid Void
     * @return Observable of Posts
     */
    @Override
    public Observable<List<BirthDayRecord>> buildUseCaseObservable(Void aVoid) {
        Single<List<BirthDayRecord>> listOfBirthDays = birthDayRepository.getAllRecords();
        if(listOfBirthDays != null)
            return birthDayRepository.getAllRecords().toObservable();
        else
            return null;
    }
}