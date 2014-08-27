
package com.xbting.circle.vp.widget;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;

/**
 * @author xbting
 * 
 */
public class SimpleCirclePageAdapter extends CirclePagerAdapter{
	/**真正的子项数目*/
	private int realCount;
	/**子项list*/
	private List<View> views;

	/**
	 * @param views
	 */
	public SimpleCirclePageAdapter(List<View> views) {
		super();
		this.views = views;
		this.realCount = views.size();
	}
	
	@Override
	public int getRealCount() {
		// TODO Auto-generated method stub
		return realCount;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		// TODO Auto-generated method stub
		return view==object;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		container.removeView((View) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		View view = views.get(position%getRealCount());
		container.addView(view);
		return view;
	}
	
	
	
	

	
	


}
