package com.ttjjttjj.bezier;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2016/5/17 0017.
 * @author tj
 */
public class BezierLevelSecond extends View {

    private Paint mPaint = new Paint();

    /** 两个数据点，一个控制点 */
    private PointF mStart, mEnd, mControl;

    private int centerX, centerY;

    /**
     * 用于代码创建控件
     *
     */
    public BezierLevelSecond(Context context){
        this(context, null);
        initData();
    }

    /**
     *  用于在XML中使用，可以指定自定义属性
     *
     */
    public BezierLevelSecond(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        initData();
    }

    /**
     * 用于在XML中使用，可以指定自定义属性，并指定样式
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public BezierLevelSecond(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initData(){
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(60);

        mStart = new PointF(0,0);
        mEnd = new PointF(0,0);
        mControl = new PointF(0,0);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        centerX = w/2;
        centerY = h/2;

        mStart.x = centerX - 200;
        mStart.y = centerY;

        mEnd.x = centerX + 200;
        mEnd.y = centerY;

        mControl.x = centerX;
        mControl.y = centerY - 100;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mControl.x = event.getX();
        mControl.y = event.getY();
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画2个数据点和1个控制点
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(20);
        canvas.drawPoint(mStart.x, mStart.y, mPaint);
        canvas.drawPoint(mEnd.x, mEnd.y, mPaint);
        canvas.drawPoint(mControl.x, mControl.y, mPaint);

        mPaint.setStrokeWidth(4);
        canvas.drawLine(mStart.x, mStart.y, mControl.x, mControl.y, mPaint);
        canvas.drawLine(mEnd.x, mEnd.y, mControl.x, mControl.y, mPaint);

        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(8);

        Path path = new Path();
        //开始画贝塞尔曲线
        path.moveTo(mStart.x, mStart.y);
        path.quadTo(mControl.x, mControl.y, mEnd.x, mEnd.y);

        canvas.drawPath(path, mPaint);
    }




}
