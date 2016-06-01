package com.gyz.androiddevelope.activity.custom;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.gyz.androiddevelope.base.BaseToolbarActivity;

/**
 * @author: guoyazhou
 * @date: 2016-05-09 16:28
 */
public class ConcatMatrixActivity extends BaseToolbarActivity {
    private static final String TAG = "ConcatMatrixActivity";


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(
//                WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setContentView(new ConcatMatrixView(this));
//    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(new ConcatMatrixView(this));
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected String currActivityName() {
        return "Matrix";
    }

    public class ConcatMatrixView extends View {

        private int scanAngle;//扫描旋转的角度
        private Matrix matrix = new Matrix();
        private Paint bgPaint = new Paint();

        public ConcatMatrixView(Context context) {
            super(context);
//            matrix.setScale(2f, 2f);
            post(run);
        }

        private   Runnable run = new Runnable() {
            @Override
            public void run() {
                scanAngle+=10;
                matrix.postRotate(scanAngle,150,150);
                invalidate();
                postDelayed(run,130);
            }
        };

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            bgPaint.setColor(Color.RED);
            canvas.drawRect(0, 0, 100, 100, bgPaint);

            canvas.save();
            canvas.concat(matrix);
            canvas.drawRect(100, 100, 200, 200, bgPaint);
            canvas.restore();

            canvas.drawRect(400, 400, 500, 500, bgPaint);
        }
    }
}



