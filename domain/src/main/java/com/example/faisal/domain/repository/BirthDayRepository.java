/*
 * Copyright (c) 2017. Faisal Jamil
 */

package com.example.faisal.domain.repository;

import com.example.faisal.domain.models.BirthDayRecord;

import java.util.List;

import io.reactivex.Single;


public interface BirthDayRepository {
    /**
     * Gets a {@link Single} which will emit a List of {@link BirthDayRecord}s.
     *
     * @return Single List
     */
    Single<List<BirthDayRecord>> getAllRecords();

    /**
     * Gets a {@link Single} which will emit a Long {@link Long}s.
     *
     * @return Single Long
     */

    Single<Long> addRecord(BirthDayRecord birthDayRecord);
}
