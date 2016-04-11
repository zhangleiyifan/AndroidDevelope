package com.gyz.androiddevelope.view.calendar;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gyz.androiddevelope.R;

import java.util.Calendar;


public class CalendarAdapter extends ArrayAdapter<Day> implements
		AdapterView.OnItemClickListener {

	private static final int FLAG_INVEST = 0x001;
	private static final int FLAG_INTEREST = 0x002;
	private static final int FLAG_BOTH = 0x003;

	private int currentYear = -1;
	private int currentMonth = -1;
	private int currentDay = -1;

	public int getYear() {
		return currentYear;
	}

	public int getMonth() {
		return currentMonth;
	}

	public int getDay() {
		return currentDay;
	}

//	public List<ProfitCalendar.DayItem> getCurrentDayProjects() {
//		return items.get(currentDay);
//	}

	public interface CalendarListener {
		public void onDateChange();
	}

	public CalendarAdapter(Context context) {
		super(context, 0);
	}

	private CalendarListener listener;

	public void setCalendarListener(CalendarListener listener) {
		this.listener = listener;
	}

	public void reset() {
		Calendar calendar = Calendar.getInstance();
		currentYear = calendar.get(Calendar.YEAR);
		currentDay = calendar.get(Calendar.DAY_OF_MONTH);
		currentMonth = calendar.get(Calendar.MONTH);
		refreshCalendar();

		notifyDateChange();
	}

	private void refreshCalendar() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(currentYear, currentMonth, currentDay);
		int currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		if (currentDayOfWeek == Calendar.SUNDAY) {
			currentDayOfWeek = 7;
		} else {
			currentDayOfWeek--;
		}

		int subDay = currentDay % 7;
		int firstDayOfWeek;
		if (currentDayOfWeek < subDay) {
			firstDayOfWeek = currentDayOfWeek + 7 - subDay;
		} else {
			firstDayOfWeek = currentDayOfWeek - subDay;
		}

		calendar.set(Calendar.DAY_OF_MONTH, 1); //把日期设置为当月第一天
		calendar.roll(Calendar.DAY_OF_MONTH, -1);// 日期回滚一天，也就是最后一天
		int currentMonthDayCount = calendar.get(Calendar.DATE);

		clear();
		if (firstDayOfWeek > 0) {
			int[] preMonth = preMonth();
			while (firstDayOfWeek > 0) {
				firstDayOfWeek--;
				Day day = new Day();
				day.day = preMonth[2] - firstDayOfWeek;
				day.month = preMonth[1];
				day.year = preMonth[0];
				add(day);
			}
		}

		for (int i = 1; i <= currentMonthDayCount; i++) {
			Day day = new Day();
			day.day = i;
			day.month = currentMonth;
			day.year = currentYear;
			add(day);
		}

		if (getCount() % 7 != 0) {
			int nextMonthDay = 7 - getCount() % 7;
			int[] nextMonth = nextMonth();
			for (int i = 1; i <= nextMonthDay; i++) {
				Day day = new Day();
				day.day = i;
				day.month = nextMonth[1];
				day.year = nextMonth[0];
				add(day);
			}
		}

	}

	private int[] nextMonth() {
		int[] nextMonth = new int[3];

		if (currentMonth == Calendar.DECEMBER) {
			nextMonth[1] = Calendar.JANUARY;
			nextMonth[0] = currentYear + 1;
		} else {
			nextMonth[1] = currentMonth + 1;
			nextMonth[0] = currentYear;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.set(nextMonth[0], nextMonth[1], 1);
		calendar.roll(Calendar.DAY_OF_MONTH, -1);// 日期回滚一天，也就是最后一天

		nextMonth[2] = calendar.get(Calendar.DATE);
		calendar.set(currentYear, currentMonth, currentDay);
		return nextMonth;
	}

	private int[] preMonth() {
		int[] preMonth = new int[3];

		if (currentMonth == Calendar.JANUARY) {
			preMonth[1] = Calendar.DECEMBER;
			preMonth[0] = currentYear - 1;
		} else {
			preMonth[0] = currentYear;
			preMonth[1] = currentMonth - 1;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.set(preMonth[0], preMonth[1], 1);
		calendar.roll(Calendar.DAY_OF_MONTH, -1);// 日期回滚一天，也就是最后一天

		preMonth[2] = calendar.get(Calendar.DATE);
		calendar.set(currentYear, currentMonth, currentDay);
		return preMonth;
	}

//	private SparseArray<List<ProfitCalendar.DayItem>> items = new SparseArray<List<DayItem>>();
	private SparseIntArray flags = new SparseIntArray();

//	public void refreshCalendar(List<ProfitCalendar.DayItem> incomes) {
//		if (incomes != null) {
//			for (ProfitCalendar.DayItem item : incomes) {
//				List<ProfitCalendar.DayItem> dayItems = items
//						.get(item.repayTime);
//				if (dayItems == null) {
//					dayItems = new ArrayList<DayItem>();
//					items.put(item.repayTime, dayItems);
//				}
//				int integer = flags.get(item.repayTime);
//
//				integer = integer | item.type;
//				flags.put(item.repayTime, integer);
//
//				dayItems.add(item);
//			}
//		}
//		refreshCalendar();
//	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView textView;
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.vw_calendar_item, null);
		}

		assert convertView != null;
		textView = (TextView) convertView.findViewById(R.id.number);

		Day day = getItem(position);
		textView.setText(day.day + "");
		if (day.month == currentMonth) {
			textView.setTextColor(Color.rgb(0x60, 0x65, 0x66));
		} else {
			textView.setTextColor(Color.rgb(0xd4, 0xd4, 0xd4));
		}

		if (day.day == currentDay && day.month == currentMonth) {
			textView.setTextColor(Color.WHITE);
			textView.setBackgroundResource(R.drawable.calendar_selected);
		} else {
			int flag = flags.get(day.day);

			if (day.month != currentMonth) {
				flag = 0;
			}

			if (flag == FLAG_INTEREST) {
				textView.setBackgroundResource(R.drawable.calendar_interest);
			} else if (flag == FLAG_BOTH) {
				textView.setBackgroundResource(R.drawable.calendar_both);
			} else if (flag == FLAG_INVEST) {
				textView.setBackgroundResource(R.drawable.calendar_invest);
			} else {
				textView.setBackgroundColor(getContext().getResources()
						.getColor(android.R.color.transparent));
			}
		}

		return convertView;
	}

	@Override
	public Day getItem(int position) {
		return super.getItem(position);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Day day = getItem(position);
		if ((day.month < currentMonth && day.year == currentYear)
				|| (day.month > currentMonth && day.year < currentYear)) {
			int[] preMonth = preMonth();
			currentYear = preMonth[0];
			currentMonth = preMonth[1];
			currentDay = day.day;
			notifyDateChange();
		} else if ((day.month > currentMonth && day.year == currentYear)
				|| (day.month < currentMonth && day.year > currentYear)) {
			int[] nextMonth = nextMonth();
			currentYear = nextMonth[0];
			currentMonth = nextMonth[1];
			currentDay = day.day;
			notifyDateChange();
		} else {
			currentDay = day.day;
		}
		refreshCalendar();
	}

	public void goToNextMonth() {
		int[] nextMonth = nextMonth();
		currentYear = nextMonth[0];
		currentMonth = nextMonth[1];

		if (currentDay > nextMonth[2]) {
			currentDay = nextMonth[2];
		}

		notifyDateChange();

		refreshCalendar();
	}

	private void notifyDateChange() {
		if (listener != null) {
//			items.clear();
			flags.clear();
			listener.onDateChange();
		}
	}

	public void goToPreMonth() {
		int[] preMonth = preMonth();
		currentYear = preMonth[0];
		currentMonth = preMonth[1];

		if (currentDay > preMonth[2]) {
			currentDay = preMonth[2];
		}

		notifyDateChange();
		refreshCalendar();
	}
}
