/*
 * Copyright (c) 2017. Faisal Jamil
 */

package com.example.faisal.data.repository;

import android.test.RenamingDelegatingContext;
import android.test.mock.MockContext;

import com.example.faisal.data.MockResponseDispatcher;
import com.example.faisal.data.RxJavaTestRunner;
import com.example.faisal.data.service.BirthDayRecordService;
import com.example.faisal.data.service.adapter.LocalDbHandler;
import com.example.faisal.data.service.adapter.RetrofitFactory;
import com.example.faisal.domain.models.BirthDayRecord;
import com.example.faisal.domain.repository.BirthDayRepository;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;

import java.util.List;
import java.util.Objects;

import io.reactivex.observers.TestObserver;
import io.reactivex.subscribers.TestSubscriber;
import okhttp3.mockwebserver.RecordedRequest;
import retrofit2.HttpException;

import static java.security.AccessController.getContext;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Testing BirthDayRecord repository and service.
 *
 * As part of the Data layer, we will test that the behaviour of Repositories and Responses
 * from Service requests are working as expected.
 */
@RunWith(RxJavaTestRunner.class)
public class ItemServiceTest extends BaseTestCase {

    @Spy RetrofitFactory retrofitFactory;

    private BirthDayRepository repository;
    private String storageDir;
    private LocalDbHandler db;
    @Before
    public void setUp() throws Exception {
        super.setUp();
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
        db = new LocalDbHandler(context);
        MockResponseDispatcher.reset();
        server.setDispatcher(MockResponseDispatcher.DISPATCHER);
        server.start();
        repository = new BirthDayRepositoryImpl(retrofitFactory, server.url("").url().toString(), db);
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Checks whether the BirthDayRecord repository and service is working as expected and the behaviour of the code have not changed.
     */
    @Test
    public void testRepositoryGotResponseOkForGetBirthdays() throws InterruptedException {
        // Unit test goes here
    }

    /**
     * Checks whether the BirthDayRecord repository and service is working as expected and the behaviour of the code have not changed.
     */
    @Test
    public void testRepositoryGotResponseOkForAddBirthday() throws InterruptedException {
        // Unit test goes here
    }

}
