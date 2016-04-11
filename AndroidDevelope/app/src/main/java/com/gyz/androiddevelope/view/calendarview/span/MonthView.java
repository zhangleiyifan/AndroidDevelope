package com.gyz.androiddevelope.view.calendarview.span;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.gyz.androiddevelope.view.calendarview.MaterialCalendarView;

import java.util.Calendar;
import java.util.Collection;


@SuppressLint("ViewConstructor")
public class MonthView extends CalendarPagerView {

    private static final int DEFAULT_DAYS_IN_WEEK = 7;
    private static final int DEFAULT_MAX_WEEKS = 6;

    public MonthView(@NonNull MaterialCalendarView view, CalendarDay month, int firstDayOfWeek) {
        super(view, month, firstDayOfWeek);
    }

    @Override
    protected void buildDayViews(Collection<DayView> dayViews, Calendar calendar) {
        for (int r = 0; r < DEFAULT_MAX_WEEKS; r++) {
            for (int i = 0; i < DEFAULT_DAYS_IN_WEEK; i++) {
                addDayView(dayViews, calendar);
            }
        }
    }

    public CalendarDay getMonth() {
        return getFirstViewDay();
    }

    @Override
    protected boolean isDayEnabled(CalendarDay day) {
        return day.getMonth() == getFirstViewDay().getMonth();
    }
}
