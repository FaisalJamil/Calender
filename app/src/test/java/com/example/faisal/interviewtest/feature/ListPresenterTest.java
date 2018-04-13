/*
 * Copyright (c) 2017. Faisal Jamil
 */

package com.example.faisal.interviewtest.feature;

import com.example.faisal.domain.executor.PostExecutionThread;
import com.example.faisal.domain.executor.ThreadExecutor;
import com.example.faisal.domain.interactor.AddBirthDay;
import com.example.faisal.domain.interactor.GetBirthDayList;
import com.example.faisal.domain.models.BirthDayRecord;
import com.example.faisal.domain.repository.BirthDayRepository;
import com.example.faisal.interviewtest.feature.list.ListPresenter;
import com.example.faisal.interviewtest.feature.list.ListView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 * Testing AddPresenter.
 *
 * As a unit test, we only focus on testing the behaviour of the Presenter is working as expected.
 */
@RunWith(MockitoJUnitRunner.class)
public class ListPresenterTest extends BaseUnitTestCase {

    @Mock private ThreadExecutor mockThreadExecutor;
    @Mock private PostExecutionThread postExecutionThread;
    @Mock private BirthDayRepository mockItemRepository;

    private GetBirthDayList birthdayListModel;
    private AddBirthDay addBirthDayModel;
    private ListView view;
    private ListPresenter presenter;

    @Before
    public void setup() throws Exception {
        super.setUp();
        view = mock(ListView.class);
        birthdayListModel = spy(new GetBirthDayList(mockItemRepository, mockThreadExecutor, postExecutionThread));
        addBirthDayModel = spy(new AddBirthDay(mockItemRepository, mockThreadExecutor, postExecutionThread));

        // ListPresenter is the class we want to test
        presenter = new ListPresenter(birthdayListModel);
        presenter.attachView(view);

        //noinspection unchecked
        doCallRealMethod().when(birthdayListModel).execute(any(DisposableObserver.class), eq(null));
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Checks that either the Model and the View are fired when the Presenter starts its execution.
     */
    @Test
    public void testPresenterOk() {
        // Preconditions
        List<BirthDayRecord> itemList = mock(List.class);
        TestScheduler testScheduler = new TestScheduler();
        given(postExecutionThread.getScheduler()).willReturn(testScheduler);
        doReturn(Observable.just(itemList)).when(birthdayListModel).buildUseCaseObservable(any(Void.class));

        // Simulates the onViewCreated method is called from the BaseFragment class
        presenter.onViewCreated();
        testScheduler.triggerActions();

        // Checks model and view methods are fired respectively
        //noinspection unchecked
        verify(birthdayListModel).execute(any(DisposableObserver.class), eq(null));
        verify(birthdayListModel).buildUseCaseObservable(any(Void.class));
    }
}
