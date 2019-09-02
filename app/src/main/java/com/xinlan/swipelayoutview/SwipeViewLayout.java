package com.xinlan.swipelayoutview;

import android.content.Context;
import android.icu.text.SymbolTable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

import androidx.annotation.RequiresApi;

/**
 * 左滑 右滑控件
 */
public class SwipeViewLayout extends ViewGroup {
    private Context mContext;
    private int mTouchSlop;
    private float mLastX;
    private float mLastY;

    private Scroller mScroller;

    private boolean isSwipe = false;
    private int mLeftViewWidth; //滑动 左侧View总宽度
    private int mRightViewWidth; //滑动右侧View总宽度

    private static final int STATUS_CLOSE = 0;
    private static final int STATUS_RIGHT_OPEN = 1;
    private static final int STATUS_LEFT_OPEN = 2;

    private int mStatus;

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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //System.out.println("wMode = " + showDebug(wMode) + "   wSize = " + wSize);
        //System.out.println("hMode = " + showDebug(hMode) + "   hSize = " + hSize);
        for (int i = 0; i < getChildCount(); i++) {
            final View child = getChildAt(i);
            switch (i) {
                case 0:
                    measureSubView(child, widthMeasureSpec, heightMeasureSpec);
                    break;
                case 1:
                    measureSubView(child, widthMeasureSpec, heightMeasureSpec);
                    mRightViewWidth = child.getMeasuredWidth();
                    break;
                case 2:
                    measureSubView(child, widthMeasureSpec, heightMeasureSpec);
                    mLeftViewWidth = child.getMeasuredWidth();
                    break;
            }
        }
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    private void init(Context context) {
        mContext = context;
        mScroller = new Scroller(context);
        mTouchSlop = ViewConfiguration.get(mContext).getScaledTouchSlop();
        mStatus = STATUS_CLOSE;
        mRightViewWidth = mLeftViewWidth = 0;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //System.out.println("event = [ " + ev.getAction() + " ]");
        boolean result = super.dispatchTouchEvent(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //System.out.println("disptach down");
                mLastX = ev.getX();
                result = true;
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(mLastX - ev.getX()) >= mTouchSlop || isSwipe) {
                    int dx = (int) (mLastX - ev.getX());
                    mLastX = ev.getX();
                    //scrollTo( dx, 0);
                    //滑动边界判定
                    int desiredMove = getScrollX() + dx;
                    // System.out.println("desiredMove = " + desiredMove);
                    if(desiredMove < -mLeftViewWidth){
                        scrollTo(-mLeftViewWidth , 0);
                    }else if(desiredMove > mRightViewWidth){
                        scrollTo(mRightViewWidth , 0);
                    } else{
                        scrollBy(dx , 0);
                    }
                    //System.out.println("disptach move");
                    result = true;
                    isSwipe = true;
                }
                //mLastX = ev.getX();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                //System.out.println("disptach up");
                swipScrollView();
                result = false;
                isSwipe = false;
                //scrollTo(0,0);
                break;
        }
        return result;
    }

    private void swipScrollView() {
        int offset = getScrollX();
        // System.out.println("offset = " + offset);
        if (offset < 0 && Math.abs(offset) >= 2* mLeftViewWidth / 3) {
            openLeftView();
        } else if (offset > 0 && Math.abs(offset) >= 2 * mRightViewWidth / 3) {
            openRightView();
        } else {
            closeView();
        }
    }

    public void openLeftView() {
        mScroller.startScroll(getScrollX(), 0, -getScrollX() - mLeftViewWidth, 0); //复位
        mStatus = STATUS_LEFT_OPEN;
        postInvalidate();
    }

    public void openRightView() {
        mScroller.startScroll(getScrollX(), 0, -getScrollX() + mRightViewWidth, 0); //复位
        mStatus = STATUS_RIGHT_OPEN;
        postInvalidate();
    }

    public void closeView() {
        mScroller.startScroll(getScrollX(), 0, -getScrollX(), 0); //复位
        mStatus = STATUS_CLOSE;
        postInvalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = super.onInterceptTouchEvent(ev);
        if (isSwipe) { //滑动中 拦截此事件
            intercept = true;
        }
        return intercept;
    }

    private void measureSubView(final View childView, int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        LayoutParams params = childView.getLayoutParams();
        measureChild(childView, getMeasureSpec(width, params.width), getMeasureSpec(height, params.height));
    }

    private int getMeasureSpec(int size, int dimension) {
        int measureSpec = 0;
        switch (dimension) {
            case LayoutParams.MATCH_PARENT:
                measureSpec = MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY);
                break;
            case LayoutParams.WRAP_CONTENT:
                measureSpec = MeasureSpec.makeMeasureSpec(size, MeasureSpec.AT_MOST);
                break;
            default:
                measureSpec = MeasureSpec.makeMeasureSpec(dimension, MeasureSpec.EXACTLY);
                break;
        }//end switch
        return measureSpec;
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int offset = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            //v.layout(left , top , right , bottom);
            if (i == 0) {
                v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
                offset = v.getMeasuredWidth();
            } else if (i == 1) {
                v.layout(offset, 0, offset + v.getMeasuredWidth(), v.getMeasuredHeight());
            } else if(i == 2) {
                v.layout(-v.getMeasuredWidth(), 0, 0, v.getMeasuredHeight());
            }
        }
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        boolean ret = false;
//        //System.out.println("isSwipe = " + isSwipe);
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                //System.out.println("down");
//                break;
//            case MotionEvent.ACTION_MOVE:
//                //System.out.println("move");
//                break;
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
//                //System.out.println("up");
//                break;
//        }//end switch
//        return super.onTouchEvent(event);
//    }
}//end class
