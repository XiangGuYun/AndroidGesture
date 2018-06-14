package com.androidgesture;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{

    private GestureDetector.OnGestureListener listener;
    private GestureDetector.OnGestureListener simpleListener;
    GestureDetector detector1,detector2,detector3;
    TextView tvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvTest = findViewById(R.id.tvTest);
        initFullListener();
        initSimpleListener();
        //三种构造方法
        detector1 = new GestureDetector(simpleListener);
        detector2 = new GestureDetector(this, listener);
        detector3 = new GestureDetector(this, simpleListener);
        tvTest.setOnTouchListener(this);
        tvTest.setFocusable(true);
        tvTest.setClickable(true);
        tvTest.setLongClickable(true);
    }

    private void initSimpleListener() {
        simpleListener = new GestureDetector.SimpleOnGestureListener(){
            // 用户轻触触摸屏，由1个MotionEvent ACTION_DOWN触发
            public boolean onDown(MotionEvent e) {
                Log.i("MyGesture", "onDown");
                Toast.makeText(MainActivity.this, "onDown", Toast.LENGTH_SHORT).show();
                return false;
            }

            /*
             * 用户轻触触摸屏，尚未松开或拖动，由一个1个MotionEvent ACTION_DOWN触发
             * 注意和onDown()的区别，强调的是没有松开或者拖动的状态
             *
             * 而onDown也是由一个MotionEventACTION_DOWN触发的，但是他没有任何限制，
             * 也就是说当用户点击的时候，首先MotionEventACTION_DOWN，onDown就会执行，
             * 如果在按下的瞬间没有松开或者是拖动的时候onShowPress就会执行，如果是按下的时间超过瞬间
             * （这块我也不太清楚瞬间的时间差是多少，一般情况下都会执行onShowPress），拖动了，就不执行onShowPress。
             */
            public void onShowPress(MotionEvent e) {
                Log.i("MyGesture", "onShowPress");
                Toast.makeText(MainActivity.this, "onShowPress", Toast.LENGTH_SHORT).show();
            }

            // 用户（轻触触摸屏后）松开，由一个1个MotionEvent ACTION_UP触发
            ///轻击一下屏幕，立刻抬起来，才会有这个触发
            //从名子也可以看出,一次单独的轻击抬起操作,当然,如果除了Down以外还有其它操作,那就不再算是Single操作了,所以这个事件 就不再响应
            public boolean onSingleTapUp(MotionEvent e) {
                Log.i("MyGesture", "onSingleTapUp");
                Toast.makeText(MainActivity.this, "onSingleTapUp", Toast.LENGTH_SHORT).show();
                return true;
            }

            // 用户按下触摸屏，并拖动，由1个MotionEvent ACTION_DOWN, 多个ACTION_MOVE触发
            public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                    float distanceX, float distanceY) {
                Log.i("MyGesture22", "onScroll:"+(e2.getX()-e1.getX()) +"   "+distanceX);
                Toast.makeText(MainActivity.this, "onScroll", Toast.LENGTH_LONG).show();

                return true;
            }

            // 用户长按触摸屏，由多个MotionEvent ACTION_DOWN触发
            public void onLongPress(MotionEvent e) {
                Log.i("MyGesture", "onLongPress");
                Toast.makeText(MainActivity.this, "onLongPress", Toast.LENGTH_LONG).show();
            }

            // 用户按下触摸屏、快速移动后松开，由1个MotionEvent ACTION_DOWN, 多个ACTION_MOVE, 1个ACTION_UP触发
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                   float velocityY) {
                Log.i("MyGesture", "onFling");
                Toast.makeText(MainActivity.this, "onFling", Toast.LENGTH_LONG).show();
                return true;
            }
        };
    }

    private void initFullListener() {
        listener = new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float
                    distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }
        };
    }

    /**
     * 在onTouch()方法中，我们调用GestureDetector的onTouchEvent()方法，
     * 将捕捉到的MotionEvent交给GestureDetector
     * 来分析是否有合适的callback函数来处理用户的手势
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return detector3.onTouchEvent(event);//拦截
    }

    public void btnNext(View view) {
        startActivity(new Intent(this, SecondActivity.class));
    }
}
