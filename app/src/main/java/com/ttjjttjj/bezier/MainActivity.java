package com.ttjjttjj.bezier;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** 二阶贝塞尔曲线 */
//        BezierLevelSecond secondView = new BezierLevelSecond(this);
//        addContentView(secondView, new RelativeLayout.LayoutParams(
//                RelativeLayout.LayoutParams.MATCH_PARENT,
//                RelativeLayout.LayoutParams.MATCH_PARENT));

        /** 三阶贝塞尔曲线 */
//        BezierLevelThird thirdView = new BezierLevelThird(this);
//        addContentView(thirdView, new RelativeLayout.LayoutParams(
//                RelativeLayout.LayoutParams.MATCH_PARENT,
//                RelativeLayout.LayoutParams.MATCH_PARENT));

        BezierHeart heartView = new BezierHeart(this);
        addContentView(heartView, new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT));

    }

}
