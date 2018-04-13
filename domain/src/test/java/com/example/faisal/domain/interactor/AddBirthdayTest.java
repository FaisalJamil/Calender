/*
 * Copyright (c) 2018. Faisal Jamil
 */

package com.example.faisal.domain.interactor;

import com.example.faisal.domain.executor.PostExecutionThread;
import com.example.faisal.domain.models.BirthDayRecord;
import com.example.faisal.domain.executor.ThreadExecutor;
import com.example.faisal.domain.repository.BirthDayRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.Observable;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Testing AddBirthDay UseCase.
 *
 * We only make sure the business logic of the UseCase is correct. We don't mind on objects outside our scope,
 * which is the Domain layer, thus testing a Repository or a Network request belongs to the Data layer.
 */
@RunWith(MockitoJUnitRunner.class)
public class AddBirthdayTest {

    private AddBirthDay addBithDayUseCase;
    @Rule public ExpectedException expectedException = ExpectedException.none();

    @Mock private ThreadExecutor mockThreadExecutor;
    @Mock private PostExecutionThread mockPostExecutionThread;
    @Mock private BirthDayRepository mockBirthdayRepository;

    @Before
    public void setUp() {
        // AddBirthDay is the class we want to test
        addBithDayUseCase = new AddBirthDay(mockBirthdayRepository, mockThreadExecutor, mockPostExecutionThread);
    }

    /**
     * Checks whether AddBirthDay UseCase is working as expected and the behaviour of the code have not changed.
     */
    @Test
    public void testAddBirthDayObservableCase() {
        // Preconditions
        BirthDayRecord record = mock(BirthDayRecord.class);

        addBithDayUseCase.buildUseCaseObservable(record);

        // Checks whether this method from inner objects are called.
        verify(mockBirthdayRepository).addRecord(record);
        // Ensures there aren't new changes on the Repository calls and UseCase structure
        verifyNoMoreInteractions(mockBirthdayRepository);
        verifyZeroInteractions(mockPostExecutionThread);
        verifyZeroInteractions(mockThreadExecutor);
    }

    /**
     * Checks whether AddBirthDay UseCase throws appropriate exception in case empty params are given
     */
    @Test
    public void testFailWithEmptyParameters() {
        expectedException.expect(NullPointerException.class);
        addBithDayUseCase.buildUseCaseObservable(null);
    }
}