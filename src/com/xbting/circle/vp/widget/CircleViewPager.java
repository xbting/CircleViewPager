package com.xbting.circle.vp.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * author xbting
 * 
 */
@SuppressLint("HandlerLeak")
public class CircleViewPager extends ViewPager{

	private Handler circleHandler;
	private  CircleViewPager mViewPager;
	private  boolean isAutoScroll;
	private  boolean isPause;
	private  long intervalTime = 3*1000;
	
	public CircleViewPager(Context context) {
		super(context);
		initView();
		
	}
	
	public CircleViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}


	private void initView(){
		mViewPager = this;
		circleHandler = new ScrollHandler();
	}
	
	 class ScrollHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if(isAutoScroll && !isPause){
				mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);
				this.sendEmptyMessageDelayed(0, intervalTime);
			}
		}
	}

	/**
	 * 开始自动滚动，在setAdapter()之后调用
	 * 
	 * void
	 */
	public void startAutoScroll(){
		isAutoScroll = true;
		mViewPager.setCurrentItem(0);
		circleHandler.sendEmptyMessageDelayed(0,intervalTime);
	}
	
	/**
	 * 停止自动滚动
	 * 
	 * void
	 */
	public void stopAutoScroll(){
		isAutoScroll = false;
		circleHandler.removeCallbacksAndMessages(null);
	}
	
	@Override
	public int getCurrentItem() {
		// TODO Auto-generated method stub
		return super.getCurrentItem();
	}
	
	/**
	 * get real item
	 * @return
	 * int real item
	 */
	public int getRealCurrentItem(){
		return getCurrentItem()%getCirclePageAdapter().getRealCount();
	}
	
	/**
	 * set real item
	 * @param realItem 
	 * void real item
	 * 
	 */
	public void setRealCurrentItem(int realItem){
		setCurrentItem(realItem);
	}


	/**
	 * 
	 * @return the circlePageAdapter
	 */
	public SimpleCirclePageAdapter getCirclePageAdapter() {
		return (SimpleCirclePageAdapter) getAdapter();
	}


	/**
	 * @param circlePageAdapter the circlePageAdapter to set
	 */
	public void setCirclePageAdapter(SimpleCirclePageAdapter circlePageAdapter) {
		this.setAdapter(circlePageAdapter);
	}
	
	
	
	
	/**
	 * 设置自动滚动的间隔时间，单位毫秒，默认3*1000毫秒
	 * @return the intervalTime
	 */
	public  long getIntervalTime() {
		return intervalTime;
	}

	/**
	 * 获取自动滚动的间隔时间，单位毫秒
	 * 
	 * @param intervalTime the intervalTime to set
	 */
	public  void setIntervalTime(long intervalTime) {
		this.intervalTime = intervalTime;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if(ev.getAction()==MotionEvent.ACTION_DOWN){//按下停止自动滚动
			isPause = true;
			if(circleHandler!=null){
				circleHandler.removeCallbacksAndMessages(null);
			}
		}else if(ev.getAction()==MotionEvent.ACTION_UP){//放开重新自动滚动
			isPause = false;
			if(circleHandler!=null){
				circleHandler.sendEmptyMessageDelayed(0, intervalTime);
			}
		}
		
		return super.dispatchTouchEvent(ev);
	}
	

}
