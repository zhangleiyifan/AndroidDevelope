package com.gyz.androiddevelope.activity.custom;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.base.BaseActivity;
import com.gyz.androiddevelope.view.calendarview.MaterialCalendarView;
import com.gyz.androiddevelope.view.calendarview.span.CalendarDay;
import com.gyz.androiddevelope.view.calendarview.span.OnDateSelectedListener;
import com.gyz.androiddevelope.view.calendarview.span.OnMonthChangedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author: guoyazhou
 * @date: 2016-04-08 10:55
 */
public class McalendarActivity extends BaseActivity  implements OnDateSelectedListener, OnMonthChangedListener {
    private static final String TAG = "McalendarActivity";
    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();

    @Bind(R.id.calendarView)
    MaterialCalendarView widget;

    @Bind(R.id.textView)
    TextView textView;
    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        setContentView(R.layout.activity_m_calendar);
        ButterKnife.bind(this);

        widget.setOnDateChangedListener(this);
        widget.setOnMonthChangedListener(this);

        //Setup initial text
        textView.setText(getSelectedDatesString());
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        textView.setText(getSelectedDatesString());
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

        //noinspection ConstantConditions
//        getSupportActionBar().setTitle(FORMATTER.format(date.getDate()));
    }


    private String getSelectedDatesString() {
        CalendarDay date = widget.getSelectedDate();
        if (date == null) {
            return "No Selection";
        }
        return FORMATTER.format(date.getDate());
    }
}
