/*
 * Copyright (c) 2017. Faisal Jamil
 */

package com.example.faisal.interviewtest.feature.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.faisal.domain.models.BirthDayRecord;
import com.example.faisal.interviewtest.AndroidApplication;
import com.example.faisal.interviewtest.R;
import com.example.faisal.interviewtest.feature.add.AddFragment;
import com.example.faisal.interviewtest.feature.list.adapter.ListAdapter;
import com.example.faisal.interviewtest.internal.di.components.DaggerFragmentComponent;
import com.example.faisal.interviewtest.internal.di.components.FragmentComponent;
import com.example.faisal.interviewtest.internal.di.modules.FragmentModule;
import com.example.faisal.interviewtest.internal.mvp.BaseFragment;
import com.example.faisal.interviewtest.util.Navigator;
import com.example.faisal.interviewtest.view.decorator.ItemClickSupport;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class ListFragment extends BaseFragment<ListPresenter> implements ListView<ListPresenter> {

    @BindView(R.id.birthday_list)
    RecyclerView bdList;
    @BindView(R.id.loading)
    ProgressBar loading;

    @Inject
    Navigator navigator;
    ListAdapter birthDaysAdapter;

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInjector();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle(R.string.bd_list);
        initList();
    }

    private void initInjector() {
        FragmentComponent component = DaggerFragmentComponent.builder()
                .applicationComponent(AndroidApplication.getComponent(getContext()))
                .fragmentModule(new FragmentModule(this)) // Support for future providers
                .build();

        component.inject(this);
    }

    private void initList() {
        birthDaysAdapter = new ListAdapter(getContext());
        bdList.setAdapter(birthDaysAdapter);

        bdList.setHasFixedSize(true);

        bdList.setLayoutManager(new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        bdList.addItemDecoration(itemDecoration);

        //Can be used in future to handle clicks on item cards
        ItemClickSupport.addTo(bdList).setOnItemClickListener(
                (recyclerView, position, v) -> {
                    // Just to show if it is working
                    Toast.makeText(getContext(),String.format(getString(R.string.item_clicked),
                            birthDaysAdapter.getItemByPosition(position).name),
                            Toast.LENGTH_LONG).show();
                }
        );
    }

    @Override
    public void render(List<BirthDayRecord> bdList) {
        if(!bdList.isEmpty()){
            hideLoading();
            birthDaysAdapter.setItemList(bdList);
            birthDaysAdapter.notifyItemRangeInserted(0, bdList.size());
        }
        else
            hideLoading();
    }

    @Inject
    @Override
    public void injectPresenter(ListPresenter presenter) {
        super.injectPresenter(presenter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.list_birthday_fragment;
    }

    @Override
    public void showLoading() {
        loading.setVisibility(View.VISIBLE);
        ViewCompat.setAlpha(loading, 0);
        ViewCompat.animate(bdList).alpha(0);
        ViewCompat.animate(loading).alpha(1);
    }

    @Override
    public void hideLoading() {
        ViewCompat.animate(bdList).alpha(1);
        ViewCompat.animate(loading).alpha(0);
    }

    @OnClick(R.id.fab)
    public void onFabClick(){
        AddFragment addFragment = AddFragment.newInstance(new AddFragment.GetRecord() {
            @Override
            public void getBirthDayRecord(BirthDayRecord birthDayRecord) {
                birthDaysAdapter.addRecord(birthDayRecord);
            }
        });
        navigator.replaceFragment(getActivity(),R.id.main_container, addFragment);
    }
}
