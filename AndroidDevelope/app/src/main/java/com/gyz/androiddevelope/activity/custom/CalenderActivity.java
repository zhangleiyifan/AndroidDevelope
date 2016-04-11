package com.gyz.androiddevelope.activity.custom;

import android.annotation.TargetApi;
import android.database.DataSetObserver;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.base.BaseActivity;
import com.gyz.androiddevelope.util.L;
import com.gyz.androiddevelope.view.calendar.CalendarAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: guoyazhou
 * @date: 2016-04-07 15:30
 */
public class CalenderActivity extends BaseActivity implements AdapterView.OnItemClickListener, CalendarAdapter.CalendarListener {
    private static final String TAG = "CalenderActivity";
    @Bind(R.id.calendar_number)
    GridView gridView;
    @Bind(R.id.current_date)
    TextView titleView;


    private CalendarAdapter adapter;

    float actionDownX, actionUpX;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_calendar);
        ButterKnife.bind(this);

    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void loadData() {

        adapter = new CalendarAdapter(this);
        adapter.setCalendarListener(this);
        adapter.registerDataSetObserver(observer);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);

        gridView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:

                        actionDownX = event.getX();

                        L.d(TAG, "actionDownX-----" + actionDownX);
                        break;
                    case MotionEvent.ACTION_UP:
                        actionUpX = event.getX();

                        L.d(TAG, "actionUpX-----" + actionUpX);
                        if (actionDownX > actionUpX && actionDownX - actionUpX > 250) {
                            adapter.goToNextMonth();
                            titleView.setText(getCurrentDate());
                        } else if (actionUpX > actionDownX && actionUpX - actionDownX > 250) {
                            adapter.goToPreMonth();
                            titleView.setText(getCurrentDate());
                        }

                        break;
                }
                return true;
            }
        });


        adapter.reset();

    }

    @OnClick({R.id.left_arrow, R.id.next_arrow, R.id.btnToday})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_arrow:
                adapter.goToPreMonth();
                titleView.setText(getCurrentDate());
                break;
            case R.id.next_arrow:
                adapter.goToNextMonth();
                titleView.setText(getCurrentDate());
                break;
            case R.id.btnToday:
                adapter.reset();
                titleView.setText(getCurrentDate());
                break;
        }
    }

    DataSetObserver observer = new DataSetObserver() {
        @Override
        public void onChanged() {
//            List<ProfitCalendar.DayItem> items = adapter
//                    .getCurrentDayProjects();
//            projectPanel.removeAllViews();
//            if (items == null || items.isEmpty()) {
//                return;
//            }
//            for (int i = 0; i < items.size(); i++) {
//                View convertView = LayoutInflater.from(CalendarActivity.this)
//                        .inflate(R.layout.vw_profit_project, null);
//
//                if (convertView != null) {
//                    ImageView imageView = (ImageView) convertView
//                            .findViewById(R.id.icon);
//                    TextView title = (TextView) convertView
//                            .findViewById(R.id.title);
//                    TextView money = (TextView) convertView
//                            .findViewById(R.id.money);
//
//                    ProfitCalendar.DayItem item = items.get(i);
//                    if (item.type == 1) {
//                        imageView.setImageResource(R.drawable.ic_profit_invest);
//                    } else {
//                        imageView
//                                .setImageResource(R.drawable.ic_profit_interest);
//                    }
//
//                    title.setText(item.title);
//                    money.setText(item.totalMoney + "元");
//
//                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
//                            ViewGroup.LayoutParams.MATCH_PARENT,
//                            ViewGroup.LayoutParams.WRAP_CONTENT);
//                    projectPanel.addView(convertView, layoutParams);
//                }
//            }
        }
    };

    @Override
    public void onDateChange() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        adapter.onItemClick(parent, view, position, id);
        titleView.setText(getCurrentDate());
    }

    private String getCurrentDate() {
        String[] months = getResources().getStringArray(R.array.month);
        int month = adapter.getMonth();
        int year = adapter.getYear();
        return months[month] + " " + year;
    }
}
