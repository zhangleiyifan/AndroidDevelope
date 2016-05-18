package com.gyz.androiddevelope.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;

import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.util.L;

/**
 * @version V1.0
 * @FileName: com.gyz.androiddevelope.view.MyListView.java
 * @author: ZhaoHao
 * @date: 2016-05-17 16:44
 */
public class MyListView extends FrameLayout implements ListView.OnScrollListener, ListView.OnItemClickListener {
    private static
    final String TAG = "MyListView";
    private Context content;
    private LayoutInflater inflater;
    private ListView listView;
    private ImageButton imgBtn;
    private onMyViewClickListener myViewClickListener;

    //listView中第一项的索引
    private int mListViewFirstItem = 0;
    //listView中第一项的在屏幕中的位置
    private int mScreenY = 0;
    //是否向上滚动
    private boolean mIsScrollToUp = false;

    public MyListView(Context context) {
        this(context, null);
    }

    public MyListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.content = context;
        initView();
    }

    private void initView() {

        inflater = LayoutInflater.from(content);
        View rootView = inflater.inflate(R.layout.view_my_listview, null);

        listView = (ListView) rootView.findViewById(R.id.listView);
        imgBtn = (ImageButton) rootView.findViewById(R.id.imageBtn);

        listView.setOnScrollListener(this);
        imgBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myViewClickListener != null)
                    myViewClickListener.onBtnClick(v);
            }
        });

        addView(rootView);
    }

    public void setListAdapter(BaseAdapter adapter) {
        if (listView != null) {
            listView.setAdapter(adapter);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (listView.getChildCount() > 0) {
            boolean isScrollToUp = false;
            View childAt = listView.getChildAt(firstVisibleItem);
            int[] location = new int[2];
            childAt.getLocationOnScreen(location);
            Log.d("onScroll", "firstVisibleItem= " + firstVisibleItem + " , y=" + location[1]);

            if (firstVisibleItem != mListViewFirstItem) {
                if (firstVisibleItem > mListViewFirstItem) {
                    Log.e("--->", "向上滑动");
                    isScrollToUp = true;
                } else {
                    Log.e("--->", "向下滑动");
                    isScrollToUp = false;
                }
                mListViewFirstItem = firstVisibleItem;
                mScreenY = location[1];
            } else {
                if (mScreenY > location[1]) {
                    Log.i("--->", "->向上滑动");
                    isScrollToUp = true;
                } else if (mScreenY < location[1]) {
                    Log.i("--->", "->向下滑动");
                    isScrollToUp = false;
                }
                mScreenY = location[1];
            }

            if (mIsScrollToUp != isScrollToUp) {
                onScrollDirectionChanged(mIsScrollToUp);
            }

        }
    }

    private void onScrollDirectionChanged(boolean isScrollToUp) {

        L.e("isScrollToUp=========="+isScrollToUp);


        if (isScrollToUp){

        }else {

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (myViewClickListener != null)
            myViewClickListener.onListItemClick(parent, view, position, id);
    }

    public void setOnMyViewClickListener(onMyViewClickListener listener) {
        this.myViewClickListener = listener;
    }

    public interface onMyViewClickListener {
        void onListItemClick(AdapterView<?> parent, View view, int position, long id);

        void onBtnClick(View view);
    }
}
