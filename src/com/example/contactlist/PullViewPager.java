package com.example.contactlist;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

public class PullViewPager extends ViewPager {
    private final boolean DEBUG = true;
    private final String TAG = "zyw";
    private int mTouchSlop;
    /**
     * Determines speed during touch scrolling
     */
    private VelocityTracker mVelocityTracker;
    private int mMinimumVelocity;
    private int mMaximumVelocity;
    private int mFlingDistance;
    private int mCloseEnough;
    /**
     * Position of the last motion event.
     */
    private float mLastMotionX;
    private float mLastMotionY;
    private float mInitialMotionX;
    private float mInitialMotionY;
    /**
     * ID of the active pointer. This is used to retain consistency during
     * drags/flings if multiple pointers are used.
     */
    private int mActivePointerId = INVALID_POINTER;
    /**
     * Sentinel value for no current active pointer.
     * Used by {@link #mActivePointerId}.
     */
    private static final int INVALID_POINTER = -1;
    private boolean mIsBeingDragged = false;
    private ViewPagerCallBack mViewPagerCallBack;
    
    public interface ViewPagerCallBack {
        boolean onTouchEventCallBack(MotionEvent ev);
    }
    
    

    public PullViewPager(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        initViewPager(context);
    }
    
    public PullViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViewPager(context);
    }
    
    public void setViewPagerCallBack(ViewPagerCallBack l) {
        mViewPagerCallBack = l;
    }
    
    private void initViewPager(Context context) {
        final ViewConfiguration configuration = ViewConfiguration.get(context);
        final float density = context.getResources().getDisplayMetrics().density;
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
        Log.i("zyw", ">>>>>>mTouchSlop ="+mTouchSlop);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mViewPagerCallBack != null) {
            mViewPagerCallBack.onTouchEventCallBack(ev);
        }
        return super.onTouchEvent(ev);
    }
    
}
