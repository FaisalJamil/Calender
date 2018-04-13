/*
 * Copyright (c) 2018. Faisal Jamil
 */

package com.example.faisal.data.service.adapter;

import okhttp3.OkHttpClient;

/**
 * Factory contract that creates a Client Rest Services.
 *
 * We establish the Client to be based on OkHttp 3.
 */
public interface RestFactory {

    <S> S create(Class<S> service);

    OkHttpClient getClient();

    void setBaseUrl(String apiUrl);
}
