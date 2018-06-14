package com.androidgesture;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 1.双击手势
 * 2.四个方向滑动的手势
 */
public class SecondActivity extends AppCompatActivity {

    private static final float FLING_MIN_DISTANCE = 200;
    private static final float FLING_MIN_VELOCITY = 100;
    GestureDetector detector;
    TextView tvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        tvTest = findViewById(R.id.tvTest);

        detector = new GestureDetector(new GestureDetector.SimpleOnGestureListener(){
            /**
             * 飞屏动作
             * @param e1 第1个ACTION_DOWN MotionEvent
             * @param e2 最后一个ACTION_MOVE MotionEvent
             * @param velocityX X轴上的移动速度，像素/秒
             * @param velocityY Y轴上的移动速度，像素/秒
             * @return
             */
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float
                    velocityY) {
                //当用户向左滑动距离超过100px，且滑动速度超过100 px/s时,即判断为向左滑动;向右同理
                if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE
                        && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
                    // Fling left
                    Log.i("MyGesture", "Fling left");
                    Toast.makeText(SecondActivity.this, "Fling Left", Toast.LENGTH_SHORT).show();
                } else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE
                        && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
                    // Fling right
                    Log.i("MyGesture", "Fling right");
                    Toast.makeText(SecondActivity.this, "Fling Right", Toast.LENGTH_SHORT).show();
                } else if(e1.getY()-e2.getY() > FLING_MIN_DISTANCE
                        && Math.abs(velocityY) > FLING_MIN_VELOCITY){
                    Log.i("MyGesture", "Fling top");
                    Toast.makeText(SecondActivity.this, "Fling Top", Toast.LENGTH_SHORT).show();
                } else if(e2.getY()-e1.getY() > FLING_MIN_DISTANCE
                        && Math.abs(velocityY) > FLING_MIN_VELOCITY){
                    Log.i("MyGesture", "Fling btm");
                    Toast.makeText(SecondActivity.this, "Fling Btm", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        detector.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                /*
                确定性的单击事件
                OnGestureListener有这样的一个方法onSingleTapUp，和onSingleTapConfirmed容易混淆。
                二者的区别是：onSingleTapUp，只要手抬起就会执行，
                而对于onSingleTapConfirmed来说，如果双击的话，则onSingleTapConfirmed不会执行。
                OnDown->OnsingleTapUp->OnsingleTapConfirmed
                 */
                Log.d("MyGesture","确认单击了");
                return false;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                /*
                双击事件
                 */
                tvTest.setBackgroundColor(Color.RED);
                Log.d("MyGesture", "双击了");
                return false;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                /*
                双击间隔中发生的动作。指触发onDoubleTap以后，
                在双击之间发生的其它动作，包含down、up和move事件
                 */
                return false;
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return detector.onTouchEvent(event);
    }
}
