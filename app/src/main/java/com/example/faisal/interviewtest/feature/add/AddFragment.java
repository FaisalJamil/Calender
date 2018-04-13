/*
 * Copyright (c) 2018. Faisal Jamil
 */

package com.example.faisal.interviewtest.feature.add;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.faisal.domain.models.BirthDayRecord;
import com.example.faisal.interviewtest.AndroidApplication;
import com.example.faisal.interviewtest.R;
import com.example.faisal.interviewtest.internal.di.components.DaggerFragmentComponent;
import com.example.faisal.interviewtest.internal.di.components.FragmentComponent;
import com.example.faisal.interviewtest.internal.di.modules.FragmentModule;
import com.example.faisal.interviewtest.internal.mvp.BaseFragment;
import com.example.faisal.interviewtest.util.Navigator;
import com.example.faisal.interviewtest.util.Utils;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

public class AddFragment extends BaseFragment<AddPresenter> implements AddView<AddPresenter> {

    private static final int NEW_EVENT_ADDED = 9922;
    private static final int NAME_SPEECH_REQUEST = 9921;
    private static final int DATE_SPEECH_REQUEST = 9920;


    @BindView(R.id.name_edit_text)
    MaterialEditText nameEditText;
    @BindView(R.id.birthday_edit_text)
    MaterialEditText birthdayEditText;
    @BindView(R.id.add)
    Button addButton;
    @BindView(R.id.constrainLayout)
    ConstraintLayout constrainLayout;
    Unbinder unbinder;
    long lastEventId;
    @Inject
    Navigator navigator;
    static GetRecord getRecord;

    public static AddFragment newInstance(GetRecord getRecord) {
        AddFragment.getRecord = getRecord;
        return new AddFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInjector();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle(R.string.add_new_record);
    }

    private void initInjector() {
        FragmentComponent component = DaggerFragmentComponent.builder()
                .applicationComponent(AndroidApplication.getComponent(getContext()))
                .fragmentModule(new FragmentModule(this)) // Support for future providers
                .build();

        component.inject(this);
    }

    @Inject
    @Override
    public void injectPresenter(AddPresenter presenter) {
        super.injectPresenter(presenter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.add_birthday_fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnFocusChange(R.id.birthday_edit_text)
    public void onBirthdayFocus(boolean hasFocus) {
        if (hasFocus)
            showDatePicker();
    }

    @OnClick(R.id.name_voice)
    public void onNameVoice(){
        promptspeech(NAME_SPEECH_REQUEST);
    }

    @OnClick(R.id.birthday_voice)
    public void onBirthdayVoice(){
        promptspeech(DATE_SPEECH_REQUEST);
    }
    private void showDatePicker() {
        // if no date selected then we need at least a date 18 years back from now
        // in order to make it easy for user to select
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -18);
        calendar.add(Calendar.DATE, -1);
        new SpinnerDatePickerDialogBuilder()
                .context(getContext())
                .callback(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String month = new DateFormatSymbols(Locale.US).getMonths()[monthOfYear];
                        birthdayEditText.setText(String.format("%s %s %s", dayOfMonth, month, year));
                    }
                })
                .spinnerTheme(R.style.NumberPickerStyle)
                .defaultDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                .build()
                .show();
    }

    @OnClick(R.id.add)
    public void onAddClicked() {
        if(Utils.hasPermissions(getContext(), Utils.CALENDER_READ_PERMISSIONS)){
            openCalenderApp();
        }
        else
            ActivityCompat.requestPermissions(getActivity(), Utils.CALENDER_READ_PERMISSIONS, Utils.CALENDER_READ_PERMISSIONS_REQUEST_CODE);
    }

    private void openCalenderApp() {
        // this eventId needs to match with current after event is created
        lastEventId = Utils.getLastEventId(getContext().getContentResolver(), getContext())+1;
        Intent intent = new Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);
        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
        intent.putExtra(CalendarContract.Events.TITLE, String.format("%s's Birthday", nameEditText.getText().toString()));
        try{
            Date date = new SimpleDateFormat("dd LLLL yyyy", Locale.US).parse(birthdayEditText.getText().toString());
            intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, date.getTime());
            intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, date.getTime() + 60*60*1000);
            startActivityForResult(intent, NEW_EVENT_ADDED);
        }catch (ParseException exception){
            displayError(R.string.common_error);
        }
    }


    @Override
    public void add(BirthDayRecord birthDayRecord) {
        // update the list in ListFragment;
        getRecord.getBirthDayRecord(birthDayRecord);
        //Go to list fragment
        getActivity().getSupportFragmentManager().popBackStack();
    }

    public void promptspeech(int type)
    {
        final Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1000);
        // for the time being only english laguage is considered but can changed in future
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now!");
        try {
            startActivityForResult(intent, type);
        }
        catch(ActivityNotFoundException a)
        {
            displayError(R.string.speech_not_supported);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == NEW_EVENT_ADDED){
            //event added
            if(Utils.getLastEventId(getContext().getContentResolver(), getContext()) == lastEventId)
                presenter.addRecord(nameEditText.getText().toString(), birthdayEditText.getText().toString());

            //otherwise event has been cancelled. No need to enter in our app
        }
        if(requestCode == NAME_SPEECH_REQUEST && resultCode == RESULT_OK){
            List<String> result= data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            nameEditText.setText(result.get(0)); // choose only the first sample
        }

        if(requestCode == DATE_SPEECH_REQUEST && resultCode == RESULT_OK){
            List<String> result= data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            birthdayEditText.setText(result.get(0)); // we choose only the first sample, other samples can also
            //be used as a drop down to choose from. But it looks beyond the scope of this application.
        }
        else{
            //user has cancelled
            //do nothing.
        }
    }

    // if permissions granted
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        if (requestCode == Utils.CALENDER_READ_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
                openCalenderApp();
            } else {
                // User refused to grant permission.
                displayError(R.string.need_calender_permission);
            }
        }
    }

    public interface GetRecord{
        public void getBirthDayRecord (BirthDayRecord birthDayRecord);
    }
}
