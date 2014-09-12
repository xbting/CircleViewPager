
package com.xbting.circle.vp.widget;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**

 * @author xbting

 * @version：
 */
public class IgnoreViewPage extends ViewPager{
	//存放被忽略的视图组件列表
	private List<View> mIgnoredViews = new ArrayList<View>();
	/**
	 * @param context
	 */
	public IgnoreViewPage(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public IgnoreViewPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 添加被忽略的组件
	 */
	public void addIgnoredView(View v) {
		if (!mIgnoredViews.contains(v)) {
			mIgnoredViews.add(v);
		}
	}

	/**
	 * 移除被忽略的组件
	 */
	public void removeIgnoredView(View v) {
		mIgnoredViews.remove(v);
	}

	/**
	 * 清空被忽略的组件
	 */
	public void clearIgnoredViews() {
		mIgnoredViews.clear();
	}
	
	/**
	 * 是否忽略视图
	 */
	private boolean isInIgnoredView(MotionEvent ev) {
		Rect rect = new Rect();
		for (View v : mIgnoredViews) {
			v.getHitRect(rect);
			if (rect.contains((int)ev.getX(), (int)ev.getY())) return true;
		}
		return false;
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if(isInIgnoredView(ev)){
				return false;
			}
			break;
		default:
			break;
		}
		return super.onInterceptTouchEvent(ev);
	}


}
