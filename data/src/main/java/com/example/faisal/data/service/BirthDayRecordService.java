/*
 * Copyright (c) 2018. Faisal Jamil
 */

package com.example.faisal.data.service;

import com.example.faisal.domain.models.BirthDayRecord;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Single;

/*
    This class will be used when we've our backend webservices layer ready
    We'll only have to call webservices endpoints and store or retrieve
    information from an online database
 */
public interface BirthDayRecordService {
    String LIST_BIRTHDAYS_PATH = "some/path";
    String ADD_BIRTHDAY_PATH = "add/path";

    @GET(LIST_BIRTHDAYS_PATH)
    Single<List<BirthDayRecord>> getAllRecords();

    @FormUrlEncoded
    @POST(ADD_BIRTHDAY_PATH)
    Single<BirthDayRecord> addRecord(@Body BirthDayRecord birthDayRecord);

}
