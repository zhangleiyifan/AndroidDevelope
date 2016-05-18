package com.gyz.androiddevelope.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.EditText;

import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.util.DensityUtils;
import com.gyz.androiddevelope.util.L;

/**
 * @author: guoyazhou
 * @date: 2016-03-14 16:34
 */
public class PasswordEditText extends EditText {

    private static final String TAG = "PasswordEditText";
    private Context context;
    private int width,height, txtLength,txtColor,txtSize,boundColor;
    private Paint paintTxt,paintBound;
    private String text;

    public PasswordEditText(Context context) {
        this(context, null);
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PasswordEditText);
        txtLength = typedArray.getInteger(R.styleable.PasswordEditText_textLength,4);
        txtColor= typedArray.getInteger(R.styleable.PasswordEditText_textColor,R.color.colorBlack);
        txtSize = (int) typedArray.getDimension(R.styleable.PasswordEditText_textSize, 12);
        txtSize = DensityUtils.dp2px(context,txtSize);
        boundColor = typedArray.getInteger(R.styleable.PasswordEditText_boundColor,R.color.color_cacaca);
        typedArray.recycle();

        init();
    }

    private void init() {

        paintTxt = new Paint();
        paintTxt.setStyle(Paint.Style.FILL);
        paintTxt.setTextSize(txtSize);
        paintTxt.setColor(txtColor);
        paintTxt.setAntiAlias(true);

        paintBound = new Paint();
        paintBound.setStyle(Paint.Style.STROKE);
        paintBound.setColor(boundColor);
        paintBound.setAntiAlias(true);




    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        draw bound
        RectF rectF = new RectF(0,0,width,height);
        canvas.drawRoundRect(rectF,1,1,paintBound);

//        draw sperate line
        for (int i=0;i<txtLength;i++){
            canvas.drawLine(width/txtLength*i,0,width/txtLength*i ,height,paintBound);
//        draw text
//        canvas.drawText(text.substring(i,i+1),,,paintTxt);
        }


    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        L.d("text=="+text);
        this.text = text.toString();
    }
}
