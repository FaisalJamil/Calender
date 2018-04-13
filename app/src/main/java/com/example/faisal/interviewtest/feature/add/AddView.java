/*
 * Copyright (c) 2018. Faisal Jamil
 */

package com.example.faisal.interviewtest.feature.add;

import com.example.faisal.domain.models.BirthDayRecord;
import com.example.faisal.interviewtest.internal.mvp.contract.Presentable;
import com.example.faisal.interviewtest.internal.mvp.contract.Viewable;

import java.util.List;

public interface AddView<T extends Presentable> extends Viewable<T> {
    public void add (BirthDayRecord birthDayRecord);
}
