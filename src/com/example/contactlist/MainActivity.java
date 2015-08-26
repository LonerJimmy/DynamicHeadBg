package com.example.contactlist;

import java.lang.reflect.Method;

import lenovo.jni.ImageUtils;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;

import com.example.lenovo.apis.R;
import com.example.params.ViewParams;
import com.example.view.CircleView;
import com.example.view.CoverageView;
import com.example.view.SquareView;

public class MainActivity extends Activity implements OnPageChangeListener, OnTabChangeListener,PullViewPager.ViewPagerCallBack, OnGestureListener{

	private int width,height;
	private FrameLayout layout;	
	private ViewParams cp;
	private CircleView AView,BView;
	private SquareView CView,DView;
	private static final String TAG_TAB1 = "拨号";
    private static final String TAG_TAB2 = "联系人";
    private static final String TAG_TAB3 = "黄页";    
    private PullViewPager mViewPager;
    private TabHost mTabHost;    
    private View bgView; 
    private CoverageView frontView;
    private FrameLayout containFrame;
    private GestureDetector detector = new  GestureDetector(this);   
    private FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
    		FrameLayout.LayoutParams.MATCH_PARENT,
    		FrameLayout.LayoutParams.MATCH_PARENT);  
    private int mFlag=0;
    private int mTouchSlop;
    private int mViewPagerScrollState = ViewPager.SCROLL_STATE_IDLE;
    private int mHeightFrameLast;
    private int mHeightFrameBg;
    private boolean mBgScrolling = false;
    private float mStartScrollingYaxis = 0;
   	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
    	super.onCreate(savedInstanceState);    
    	getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    	/*
		Window window = getWindow();		
		window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    	*/
		
    	getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        final ViewConfiguration configuration = ViewConfiguration.get(this);

        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
        Log.e("ppp", "werqw");        
        
        containFrame=(FrameLayout)this.findViewById(R.id.frame_last);
        layout= (FrameLayout) findViewById(R.id.frame_bg);//定义框架布局器       
        bgView=(View)this.findViewById(R.id.frame_bg);  
        frontView=new CoverageView(this);
        
        layout.setBackgroundColor(Color.argb(255, 30, 155, 36));
        
        DisplayMetrics dm = new DisplayMetrics();//获取当前显示的界面大小
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        width=dm.widthPixels;
        height=dm.heightPixels;//获取当前界面的高度
        System.out.println("width="+width+"height="+height);      
        
        cp=new ViewParams(); 
        //背景动态效果
        init();
        
        //tab效果实现
        tabHost();         

        mViewPager.setViewPagerCallBack(this);

        detector.setIsLongpressEnabled(true);  
/*
		Resources res = getResources();
		Bitmap bmp = BitmapFactory.decodeResource(res,
				R.drawable.ic_launcher);
		if (getActionBar() != null) {
			this.getActionBar().setBackgroundDrawable(new BitmapDrawable(bmp));
		}  
*/   
    }   

	private void tabHost() {
		// TODO Auto-generated method stub
		mTabHost = (TabHost) findViewById(R.id.tabhost);
        mTabHost.setup();
        mViewPager = (PullViewPager) findViewById(R.id.pager);

        mViewPager.setOnPageChangeListener(this);

        mTabHost.setOnTabChangedListener(this);

        TabSpec tabSpec1 = mTabHost.newTabSpec(TAG_TAB1);
        tabSpec1.setIndicator(TAG_TAB1);
        tabSpec1.setContent(new MyTabContentFactoryView());
        mTabHost.addTab(tabSpec1);

        TabSpec tabSpec2 = mTabHost.newTabSpec(TAG_TAB2);
        tabSpec2.setIndicator(TAG_TAB2);
        tabSpec2.setContent(new MyTabContentFactoryView());
        mTabHost.addTab(tabSpec2);
        
        TabSpec tabSpec3 = mTabHost.newTabSpec(TAG_TAB3);
        tabSpec3.setIndicator(TAG_TAB3);
        tabSpec3.setContent(new MyTabContentFactoryView());
        mTabHost.addTab(tabSpec3);        

        mViewPager.setAdapter(new PagerAdapter() {

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeViewAt(0);// 删除页卡
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) { // 这个方法用来实例化页卡
                View v = new View(MainActivity.this);
                container.addView(v, 0);// 添加页卡
                return v;
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;// 官方提示这样写
            }

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return 3;
            }
        });
        
        mTabHost.setCurrentTab(2);

		try {
			Class<?> aops = Class.forName("android.widget.TabHost");
			Method method = aops.getMethod("useLenovoSliderTab",
					null);
			method.invoke(mTabHost, null);
		} catch (Exception e) {
			Log.i("wangkl3_debug", ">>>>>>>useLenovoSliderTab error!!! " + e.toString());
		}
	}
    
    private void init() {	  
		//图像A    	
        cp=new ViewParams();
        cp.R=712*(width/1080);
		cp.isRote=true;		
		cp.x=1000*(width/1080);
		cp.y=600*(width/1080);		
		cp.rgb[0]=0;
		cp.rgb[1]=183;
		cp.rgb[2]=183;	
		cp.startAlph=70;
		cp.endAlph=30;
		cp.startR=100;
		cp.endR=92;
		cp.time=11;
		
		AView = new CircleView(this,cp); //第一个参数是半径，第二三参数是x、y轴坐标，后面的四个参数是透明度和argb
		AView.setBackgroundColor(Color.TRANSPARENT);			
        layout.addView(AView);         

        //图像B        
        cp=new ViewParams();
        cp.R=712*(width/1080);
		cp.isRote=true;		
		cp.x=40*(width/1080);
		cp.y=40*(width/1080);		
		cp.rgb[0]=109;
		cp.rgb[1]=193;
		cp.rgb[2]=0;
		cp.startAlph=60;
		cp.endAlph=25;
		cp.startR=100;
		cp.endR=96;
		cp.time=9;
		
		BView = new CircleView(this,cp); //第一个参数是半径，第二三参数是x、y轴坐标，后面的四个参数是透明度和argb
		BView.setBackgroundColor(Color.TRANSPARENT);				
        layout.addView(BView);  
		
        //图像C        
        cp=new ViewParams();
        cp.R=1520*(width/1080);
		cp.isRote=true;		
		cp.x=-20*(width/1080);
		cp.y=(float) (-190*Math.sqrt(2)-1800)*(width/1080);		
		cp.rgb[0]=0;
		cp.rgb[1]=114;
		cp.rgb[2]=114;
		cp.startAlph=50;
		cp.endAlph=20;
		cp.startR=100;
		cp.endR=96;
		cp.time=8;
		
		CView = new SquareView(this,cp); //第一个参数是半径，第二三参数是x、y轴坐标，后面的四个参数是透明度和argb
		CView.setBackgroundColor(Color.TRANSPARENT);			
        layout.addView(CView);	
        
        //图像D
        cp=new ViewParams();
        cp.R=1520*(width/1080);
		cp.isRote=false;		
		cp.x=(-20+1200)*(width/1080);
		cp.y=(float) (-190*Math.sqrt(2)-900+150)*(width/1080);			
		cp.rgb[0]=70;
		cp.rgb[1]=178;
		cp.rgb[2]=0;
		cp.startAlph=35;
		cp.endAlph=80;
		cp.startR=100;
		cp.endR=92;
		cp.time=8;
		
		DView = new SquareView(this,cp); //第一个参数是半径，第二三参数是x、y轴坐标，后面的四个参数是透明度和argb
		DView.setBackgroundColor(Color.TRANSPARENT);				
        layout.addView(DView);      
    }
    
    private class MyTabContentFactoryView implements TabHost.TabContentFactory {
        public View createTabContent(String tag) {
            final View v = new View(MainActivity.this);
            v.setMinimumWidth(0);
            v.setMinimumHeight(0);
            return v;
        }
    }


    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub
        Log.i("zyw", ">>>>>>>onPageScrollStateChanged="+arg0);
        mViewPagerScrollState = arg0;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetpixels) {
        // TODO Auto-generated method stub
        Log.i("zyw", ">>>>>>>onPageScrolled position="+position
                +", positionOffset="+positionOffset
                +", positionOffsetpixels="+positionOffsetpixels
                +", count="+mTabHost.getTabWidget().getChildCount());
        setTabSliderCoord(position, positionOffset, positionOffsetpixels);
    }

    private void setTabSliderCoord(int position, float positionOffset, int positionOffsetpixels) {
        // TODO Auto-generated method stub
        try {
            Class<?> aops = Class.forName("android.widget.TabHost");
            Method method = aops.getMethod("setTabCoordScale", new Class<?>[] {
                    int.class, float.class
            });
            method.invoke(mTabHost, position, positionOffset);
        } catch (Exception e) {
            Log.i("zyw", ">>>>>>>setTabSliderCoord error!!!");
        }
    }

    @Override
    public void onPageSelected(int position) {
        TabWidget widget = mTabHost.getTabWidget();
        int oldFocusability = widget.getDescendantFocusability();
        widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        mTabHost.setCurrentTab(position);
        widget.setDescendantFocusability(oldFocusability);
        Log.i("zyw", ">>>>>>onPageSelected, position=" + position);
    }
    
    private void setDrawChangeEnable(boolean b) {
        AView.setDrawChangeEnable(b);
        BView.setDrawChangeEnable(b);
        CView.setDrawChangeEnable(b);
        DView.setDrawChangeEnable(b);
    }

    @Override
    public void onTabChanged(String tabId) {
        // TODO Auto-generated method stub
        int position = mTabHost.getCurrentTab();
        Log.i("zyw", ">>>>>>>onTabChanged, position=" + position+", tabId="+tabId
                +", c="+mViewPager.getCurrentItem());
        if (position != mViewPager.getCurrentItem()) {
            mViewPager.setCurrentItem(position, true);
        }
    }
    
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
              // 手势向下 down
    	Log.i(getClass().getName(), "onFling-----" +" + e1.getX()  + e1.getY() + e2.getX() + e2.getY() ");
    	System.out.println("true");

    	return false;
    }
     
	//在长按时被调用
	public void onLongPress(MotionEvent e) { 
		
	}

	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	//手势滑动
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float arg2,
			float arg3) {	
		
		System.out.println("y="+e2.getY());
		int distance = mHeightFrameLast - mHeightFrameBg;
		Log.i("zyw", ">>>>>>>>mHeightFrameLast="+mHeightFrameLast
		        +" , mHeightFrameBg="+mHeightFrameBg
		        +" , e1.getY()="+e1.getY()
		        +" , diffY="+(e2.getY()-e1.getY())
		        +" , e1.getX()="+e1.getX()
		        +" , diffX="+(e2.getX()-e1.getX()));
		if (!mBgScrolling) {
	        if (Math.abs(e1.getX()-e2.getX()) >= mTouchSlop || (e2.getY()-e1.getY()) < mTouchSlop) {
	            return true;
	        } else if((e2.getY()-e1.getY()) >= mTouchSlop) {
	            mBgScrolling = true;
	            mStartScrollingYaxis = e1.getY();
	        }
		}
		if (!mBgScrolling) {
		    return true;
		}
		if((e1.getY()-e2.getY())>0 && mStartScrollingYaxis != e1.getY()){//up
		    mBgScrolling = false;
            params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    (int)(mHeightFrameBg)
                        );
            layout.setLayoutParams(params);
            layout.setAlpha(1.0f);
            frontView.setVisibility(View.INVISIBLE);
            setDrawChangeEnable(true);
		}
		
		if((e1.getY()-e2.getY())>-mTouchSlop && mStartScrollingYaxis == e1.getY()){//up 
		    mBgScrolling = false;
		    params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    (int)(mHeightFrameBg)
                        );
		    layout.setLayoutParams(params);
			frontView.setVisibility(View.INVISIBLE);
			setDrawChangeEnable(true);
		}
		
		if((e1.getY()-e2.getY())<-mTouchSlop && mStartScrollingYaxis == e1.getY()){//down
		    int layoutHeight = (int)((e2.getY()-e1.getY()-mTouchSlop)+mHeightFrameBg);
		    if (layoutHeight > mHeightFrameLast) {
		        layoutHeight = mHeightFrameLast;
		    }
			params = new FrameLayout.LayoutParams(
					FrameLayout.LayoutParams.MATCH_PARENT,
					layoutHeight);
			containFrame.setLayoutParams(params);
			layout.setLayoutParams(params);
			float alpha = 0f;
			if (layoutHeight < mHeightFrameLast) {
			    alpha = ((e2.getY()-e1.getY()-mTouchSlop)) / (mHeightFrameLast - mHeightFrameBg);
			} else {
			    alpha = 1.0f;
			}
			frontView.setAlpha(alpha);
			frontView.setVisibility(View.VISIBLE);
			setDrawChangeEnable(false);
		}	
		   	
		return true;
	}

	@Override
	public void onShowPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean onTouchEventCallBack(MotionEvent e) {
		// TODO Auto-generated method stub
		//Log.i(getClass().getName(), "onFling-----" +"e.getx="+ e.getX()+"e.gety=" + (int)e.getY());
	    initFrontViewBitmap();
		detector.onTouchEvent(e);
		return true;
	}

	private void initFrontViewBitmap() {
	    if(mFlag==0){
	        mHeightFrameLast = containFrame.getMeasuredHeight();
	        mHeightFrameBg = layout.getMeasuredHeight();
            params = new FrameLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                );  
            View tmpView=bgView;
            boolean bool = tmpView.isDrawingCacheEnabled();
            try{
            	//图片模糊效果
                tmpView.setDrawingCacheEnabled(true);
                Bitmap localBitmap1 = tmpView.getDrawingCache();
                Bitmap localBitmap2 = Bitmap.createScaledBitmap(localBitmap1, 
                (int)(localBitmap1.getWidth()*0.25), (int)(localBitmap1.getHeight()*0.25), true);
                long cur_time = System.currentTimeMillis();
                Bitmap blur_bitmap = ImageUtils.fastBlur(localBitmap2, 25);             
                BitmapDrawable bg = new BitmapDrawable(getResources(), blur_bitmap);                
                frontView.setBackground(bg); 
                frontView.setVisibility(View.INVISIBLE); 
            }
            catch (OutOfMemoryError localOutOfMemoryError)
            {
                //Log.e(TAG, "screenShot-OutOfMemoryError", localOutOfMemoryError);
            }  
            containFrame.addView(frontView, params);
            mFlag=1;
        }else{
            
        }
	}
}
