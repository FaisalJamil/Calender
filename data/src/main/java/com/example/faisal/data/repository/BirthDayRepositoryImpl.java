/*
 * Copyright (c) 2018. Faisal Jamil
 */

package com.example.faisal.data.repository;

import com.example.faisal.data.service.BirthDayRecordService;
import com.example.faisal.data.service.adapter.LocalDbHandler;
import com.example.faisal.data.service.adapter.RestFactory;
import com.example.faisal.domain.models.BirthDayRecord;
import com.example.faisal.domain.repository.BirthDayRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Single;


// Here we decide either to use the network API or local db.
// Since network api is not there yet, we'll be using local db, and when Webservices layer is ready we'll easily shift
// Db could be used even after Network layer is ready, to save extra network calls or cache purposes.
public class BirthDayRepositoryImpl implements BirthDayRepository {
    private final RestFactory api;
    private final String apiUrl;
    private final LocalDbHandler db; // singleton instance of db

    @Inject
    BirthDayRepositoryImpl(RestFactory api, @Named("ApiUrl") String apiUrl, LocalDbHandler db) {
        this.api = api;
        this.apiUrl = apiUrl;
        this.db = db;
    }

    @Override
    public Single<List<BirthDayRecord>> getAllRecords() {
        //In order to get list of birthday records from network we can easily use
        this.api.setBaseUrl(apiUrl); // Ability to change the API Url at any time.
        //return this.api.create(BirthDayRecordService.class).getItems();

        //But we have local db to get all the records for the time being
        return this.db.getAllRecords();
    }

    @Override
    public Single<Long> addRecord(BirthDayRecord birthDayRecord) {
        //In order to add a birthday record using network api we can easily use
        this.api.setBaseUrl(apiUrl); // Ability to change the API Url at any time.
        //return this.api.create(BirthDayRecordService.class).addRecord(birthDayRecord);

        //But we have local db to add records for the time being
        return this.db.addRecord(birthDayRecord);
    }
}
