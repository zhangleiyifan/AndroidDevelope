package com.gyz.androiddevelope.view.calendarview.format;


import com.gyz.androiddevelope.view.calendarview.span.CalendarDay;

/**
 * Used to format a {@linkplain  } to a string for the month/year title
 */
public interface TitleFormatter {

    /**
     * Converts the supplied day to a suitable month/year title
     *
     * @param day the day containing relevant month and year information
     * @return a label to display for the given month/year
     */
    public CharSequence format(CalendarDay day);
}
