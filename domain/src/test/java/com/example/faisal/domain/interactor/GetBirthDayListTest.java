/*
 * Copyright (c) 2017. Faisal Jamil
 */

package com.example.faisal.domain.interactor;

import com.example.faisal.domain.executor.PostExecutionThread;
import com.example.faisal.domain.executor.ThreadExecutor;
import com.example.faisal.domain.models.BirthDayRecord;
import com.example.faisal.domain.repository.BirthDayRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Testing GetBirthDayList UseCase.
 *
 * We  make sure the business logic of the UseCase is correct. We don't mind on objects outside our scope,
 * which is the Domain layer, thus testing a Repository or a Network request belongs to the Data layer.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetBirthDayListTest {

    private GetBirthDayList getBirthdayListUseCase;

    @Mock private ThreadExecutor mockThreadExecutor;
    @Mock private PostExecutionThread postExecutionThread;
    @Mock private BirthDayRepository mockBirthdayRepository;

    @Before
    public void setUp() {
        // GetBirthDayList is the class we want to test
        getBirthdayListUseCase = new GetBirthDayList(mockBirthdayRepository,
                mockThreadExecutor, postExecutionThread);
    }

    /**
     * Checks whether GetBirthDayList UseCase is working as expected and the behaviour of the code have not changed.
     */
    @Test
    public void testGetBirthDayListObservableCase() {

        // Attaches the subscriber and executes the Observable.
        getBirthdayListUseCase.buildUseCaseObservable(null);

        // Checks whether this methods from inner objects are called.
        verify(mockBirthdayRepository).getAllRecords();

        // Ensures there aren't new changes on the Repository calls and UseCase structure
        verifyNoMoreInteractions(mockBirthdayRepository);
        verifyZeroInteractions(mockThreadExecutor);
        verifyZeroInteractions(postExecutionThread);
    }
}
