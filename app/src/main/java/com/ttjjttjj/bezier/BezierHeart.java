package com.ttjjttjj.bezier;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/5/18 0018.
 *
 * 心形的曲线
 *
 */
public class BezierHeart extends View {

    private Paint mPaint;
    //4个数据点
    private float[] mData = new float[8];
    //8个控制点
    private float[] mCtrl = new float[16];
    //圆的半径
    private float mCircleRadius = 200;

    // 一个常量，用来计算绘制圆形贝塞尔曲线控制点的位置
    private static final float C = 0.551915024494f;
    // 圆形的控制点与数据点的差值
    private float mDifference = mCircleRadius * C;

    private int mCenterX, mCenterY;


    private float mDuration = 2000;                     // 变化总时长
    private float mCurrent = 0;                         // 当前已进行时长
    private float mCount = 100;                         // 将时长总共划分多少份
    private float mPiece = mDuration/mCount;            // 每一份的时长


    public BezierHeart(Context context) {
        this(context, null);
    }

    public BezierHeart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        initData();
    }

    public BezierHeart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initData(){

        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(60);

        //初始化4个数据点(顺时针记录)
        mData[0] = 0;
        mData[1] = mCircleRadius;

        mData[2] = mCircleRadius;
        mData[3] = 0;

        mData[4] = 0;
        mData[5] = -mCircleRadius;

        mData[6] = -mCircleRadius;
        mData[7] = 0;

        //初始化8个控制点(顺时针记录)
        mCtrl[0] = mData[0] + mDifference;
        mCtrl[1] = mData[1];

        mCtrl[2] = mData[2];
        mCtrl[3] = mData[3] + mDifference;

        mCtrl[4] = mData[2];
        mCtrl[5] = mData[3] - mDifference;

        mCtrl[6] = mData[4] + mDifference;
        mCtrl[7] = mData[5];

        mCtrl[8] = mData[4] - mDifference;
        mCtrl[9] = mData[5];

        mCtrl[10] = mData[6];
        mCtrl[11] = mData[7] - mDifference;

        mCtrl[12] = mData[6];
        mCtrl[13] = mData[7] + mDifference;

        mCtrl[14] = mData[0] - mDifference;
        mCtrl[15] = mData[1];
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w/2;
        mCenterY = h/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCoordinateSystem(canvas);       // 绘制坐标系

        canvas.translate(mCenterX, mCenterY); // 将坐标系移动到画布中央
        canvas.scale(1,-1);                 // 翻转Y轴

        drawAuxiliaryLine(canvas);


        // 绘制贝塞尔曲线
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(8);

        Path path = new Path();
        //画圆
        path.moveTo(mData[0],mData[1]);
        path.cubicTo(mCtrl[0],  mCtrl[1],  mCtrl[2],  mCtrl[3],     mData[2], mData[3]);
        path.cubicTo(mCtrl[4],  mCtrl[5],  mCtrl[6],  mCtrl[7],     mData[4], mData[5]);
        path.cubicTo(mCtrl[8],  mCtrl[9],  mCtrl[10], mCtrl[11],    mData[6], mData[7]);
        path.cubicTo(mCtrl[12], mCtrl[13], mCtrl[14], mCtrl[15],    mData[0], mData[1]);

        canvas.drawPath(path, mPaint);

        //将圆改变为心形
        mCurrent += mPiece;
        if (mCurrent < mDuration){

            //下面仔细看到，响应坐标x，y的位置变化
            //下面的120,80,80,20,20 这样的...
            mData[1] -= 120/mCount;
            mCtrl[7] += 80/mCount;
            mCtrl[9] += 80 / mCount;

            mCtrl[4] -= 20/mCount;
            mCtrl[10] += 20/mCount;

            postInvalidateDelayed((long) mPiece);
        }
    }

    // 绘制辅助线
    private void drawAuxiliaryLine(Canvas canvas) {
        // 绘制数据点和控制点
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(20);

        for (int i=0; i<8; i+=2){
            canvas.drawPoint(mData[i],mData[i+1], mPaint);
        }

        for (int i=0; i<16; i+=2){
            canvas.drawPoint(mCtrl[i], mCtrl[i+1], mPaint);
        }


        // 绘制辅助线
        mPaint.setStrokeWidth(4);

        for (int i=2, j=2; i<8; i+=2, j+=4){
            canvas.drawLine(mData[i],mData[i+1],mCtrl[j],mCtrl[j+1],mPaint);
            canvas.drawLine(mData[i],mData[i+1],mCtrl[j+2],mCtrl[j+3],mPaint);
        }
        canvas.drawLine(mData[0],mData[1],mCtrl[0],mCtrl[1],mPaint);
        canvas.drawLine(mData[0],mData[1],mCtrl[14],mCtrl[15],mPaint);
    }

    /**
     * 绘制坐标
     *
     * @param canvas
     */
    private void drawCoordinateSystem(Canvas canvas){

        //????
        canvas.save();

        canvas.translate(mCenterX, mCenterY);
        canvas.scale(1, -1);

        Paint fuzhuPaint = new Paint();
        fuzhuPaint.setColor(Color.RED);
        fuzhuPaint.setStyle(Paint.Style.STROKE);
        fuzhuPaint.setStrokeWidth(5);

        canvas.drawLine(0, -mCenterX, 0, mCenterX, fuzhuPaint);
        canvas.drawLine(mCenterY,0, -mCenterY, 0, fuzhuPaint);

        canvas.restore();
    }





}
