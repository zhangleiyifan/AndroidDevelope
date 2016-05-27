package com.gyz.androiddevelope.view;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyz.androiddevelope.R;

/**
 * app bar
 */
public class AppBar extends LinearLayout {

    private static final String TAG = "AppBar";
    private LinearLayout mLeftRoot , mMiddleRoot , mRightRoot ;
    private Toolbar toolbar;
    private ImageView mBackImage ;
    private TextView mTitleView ;

    public AppBar(Context context) {
        super(context);
    }

    public AppBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public AppBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.appbar_layout ,this);
        mLeftRoot = (LinearLayout)view.findViewById(R.id.left_root);
        mMiddleRoot = (LinearLayout)view.findViewById(R.id.middle_root);
        mRightRoot = (LinearLayout)view.findViewById(R.id.right_root);
        toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        mBackImage = (ImageView)view.findViewById(R.id.image_back);
        mTitleView = (TextView)view.findViewById(R.id.title);
    }


    /**
     * set title
     * @param title
     */
    public void setTitle(String title){
        mTitleView.setText(title);
    }

    /**
     * set title size
     * @param sizeResource
     */
    public void setTitleSize(int sizeResource){
        mTitleView.setTextSize(sizeResource);
    }

    /**
     * set title color
     * @param colorResource
     */
    public void setTitleColor(int colorResource){
        mTitleView.setTextColor(colorResource);
    }


    /**
     * set image back
     * @param drableResource
     */
    public void setBackImage(int drableResource){
        mBackImage.setImageResource(drableResource);
    }

    /**
     * custom the view left
     * @param view
     */
    public void setLeftCustom(View view){
        if(null != view){
            mLeftRoot.removeAllViews();
            mLeftRoot.addView(view);
        }
    }

    /**
     * set the veiw right
     * @param view
     */
    public void setRightCustom(View view){
        if(null != view){
            mRightRoot.removeAllViews();
            mRightRoot.addView(view);
        }
    }
    /**
     * set the veiw right
     * @param view
     */
    public void setMiddleCustom(View view){
        if(null != view){
            mMiddleRoot.removeAllViews();
            mMiddleRoot.addView(view);
        }
    }

    /**
     * set left view visible
     * @param visible
     */
    public void setLeftVisible(boolean visible){
        mLeftRoot.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    /**
     * set left view visible
     * @param visible
     */
    public void setMiddleVisible(boolean visible){
        mMiddleRoot.setVisibility(visible ? View.VISIBLE : View.GONE);
    }


    /**
     * set left view visible
     * @param visible
     */
    public void setRightVisible(boolean visible){
        mRightRoot.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    /**
     * get left root
     * @return
     */
    public LinearLayout getLeftRoot(){
        return mLeftRoot;
    }

    /**
     * get left root
     * @return
     */
    public LinearLayout getMiddleRoot(){
        return mMiddleRoot;
    }


    /**
     * get left root
     * @return
     */
    public LinearLayout getRightRoot(){
        return mRightRoot;
    }

    /**
     * set support action bar
     * @param activity
     */
    public void setSupportActionBar(AppCompatActivity activity){
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    /**
     * set back listener
     * @param listener
     */
    public void setImageBackListener(OnClickListener listener){
        mBackImage.setOnClickListener(listener);
    }

}