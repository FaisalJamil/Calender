/*
 * Copyright (c) 2017. Faisal Jamil
 */

package com.example.faisal.interviewtest.feature.list;

import com.example.faisal.domain.models.BirthDayRecord;
import com.example.faisal.interviewtest.internal.mvp.contract.Presentable;
import com.example.faisal.interviewtest.internal.mvp.contract.Viewable;

import java.util.List;

public interface ListView<T extends Presentable> extends Viewable<T> {

    void render(List<BirthDayRecord> bdList);
}
