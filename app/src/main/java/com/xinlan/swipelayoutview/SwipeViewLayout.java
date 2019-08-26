package com.xinlan.swipelayoutview;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

/**
 * 左滑 右滑控件
 */
public class SwipeViewLayout extends ViewGroup {
    private Context mContext;
    private View mMainView;
    private List<View> mLeftViews = new ArrayList<View>(2);
    private List<View> mRightViews = new ArrayList<View>(2);

    private int mTouchSlop;
    private float mLastX;
    private float mLastY;

    private enum St{

    }

    public SwipeViewLayout(Context context) {
        super(context);
        init(context);
    }

    public SwipeViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SwipeViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SwipeViewLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mTouchSlop = ViewConfiguration.get(mContext).getScaledTouchSlop();
    }

    public void setMainView(final View v) {
        mMainView = v;
        addView(mMainView);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mLastX = ev.getX();
                mLastY = ev.getY();
                intercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                float x = ev.getX();
                float y = ev.getY();
                if(Math.abs(mLastX - x) >= mTouchSlop){
                    mLastX = ev.getX();
                    mLastY = ev.getY();
                    intercept = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                break;
        }
        System.out.println("intercept = " +intercept);
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        for (int i = 0; i < getChildCount(); i++) {
            final View child = getChildAt(i);
            if (i == 0) {
                child.measure(widthMeasureSpec, heightMeasureSpec);
            } else  {
                child.measure(widthMeasureSpec, heightMeasureSpec);
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int x = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            //v.layout(left , top , right , bottom);
            v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        }
        //mMainView.layout(left, top, mMainView.getMeasuredWidth(), mMainView.getMeasuredHeight());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean ret = false;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                System.out.println("down");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("move");
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                System.out.println("up");
                break;
        }//end switch
        return super.onTouchEvent(event);
    }
}//end class
