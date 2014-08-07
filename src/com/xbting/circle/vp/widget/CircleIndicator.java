
package com.xbting.circle.vp.widget;


import com.xbting.circle.vp.R;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.View;


public class CircleIndicator extends View implements OnPageChangeListener{
	
	private CircleViewPager mCircleViewPager;
	/**圆点半径*/
	private float radius ;
	/**圆圈间距*/
	private float circlePadding;
	/**未选中页面圆点填充颜色*/
	private int pageColor;
	/**空心圆点的环颜色*/
	private int strokeColor;
	/**空心圆点的环宽度*/
	private float strokeWidth;
	/**选中页面圆点填充颜色*/
	private int fillColor;

	private final Paint mPaintPage = new Paint();
	private final Paint mPaintStroke = new Paint();
	private final Paint mPaintFill = new Paint();
	private int count;
	private int realCurrentItem;
	
	/**
	 * @param context
	 */
	public CircleIndicator(Context context) {
		super(context);
		Resources res = getResources();
		radius  = res.getDimension(R.dimen.default_circle_indicator_radius);
		circlePadding = res.getDimension(R.dimen.default_circle_indicator_circle_padding);
		pageColor = res.getColor(R.color.default_circle_indicator_page_color);
		strokeWidth= res.getDimension(R.dimen.default_circle_indicator_stroke_width);
		strokeColor = res.getColor(R.color.default_circle_indecator_stroke_color);
		fillColor = res.getColor(R.color.default_circle_indicator_fill_color);

		init();
	}


	/**
	 * @param context
	 * @param attrs
	 */
	public CircleIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		getAttrs(context, attrs);
		init();
	}
	
	/**
	 * 
	 * 获取xml属性
	 * 
	 * @param context
	 * @param attrs
	 * void
	 */
	private void getAttrs(Context context, AttributeSet attrs){
		Resources res = getResources();
		float defaultRadius  = res.getDimension(R.dimen.default_circle_indicator_radius);
		float defaultCirclePadding = res.getDimension(R.dimen.default_circle_indicator_circle_padding);
		int defaultPageColor = res.getColor(R.color.default_circle_indicator_page_color);
		float defaultStrokeWidth= res.getDimension(R.dimen.default_circle_indicator_stroke_width);
		int defaultFillColor = res.getColor(R.color.default_circle_indicator_fill_color);
		int defaultStrokeColor = res.getColor(R.color.default_circle_indecator_stroke_color);
		
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CircleViewPager);
		radius = ta.getDimension(R.styleable.CircleViewPager_radius, defaultRadius);
		circlePadding = ta.getDimension(R.styleable.CircleViewPager_circlePadding, defaultCirclePadding);
		strokeWidth = ta.getDimension(R.styleable.CircleViewPager_strokeWidth, defaultStrokeWidth);
		fillColor = ta.getColor(R.styleable.CircleViewPager_fillColor, defaultFillColor);
		strokeColor = ta.getColor(R.styleable.CircleViewPager_strokeColor, defaultStrokeColor);
		pageColor = ta.getColor(R.styleable.CircleViewPager_pageColor, defaultPageColor); 
		ta.recycle();
	}



	/**
	 * @return the mCircleViewPager
	 */
	public CircleViewPager getCircleViewPager() {
		return mCircleViewPager;
	}

	/**
	 * @param mCircleViewPager the mCircleViewPager to set
	 */
	public void setCircleViewPager(CircleViewPager mCircleViewPager) {
		this.mCircleViewPager = mCircleViewPager;
		count = mCircleViewPager.getCirclePageAdapter().getRealCount();
		if(this.mCircleViewPager!=null){
			this.mCircleViewPager.setOnPageChangeListener(this);
		}
	    requestLayout();
	}

	/**
	 * 初始化
	 * 
	 * void
	 * 
	 */
	private void init() {
		mPaintStroke.setStyle(Style.STROKE);
		mPaintStroke.setColor(strokeColor);
		mPaintStroke.setStrokeWidth(strokeWidth);
		
		mPaintFill.setStyle(Style.FILL);
		mPaintPage .setColor(pageColor);
		mPaintFill.setStyle(Style.FILL);
		mPaintFill.setColor(fillColor);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		
		int viewWidth ;
		int viewHeight ;
		
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		
		if(widthMode==MeasureSpec.EXACTLY){
			viewWidth = widthSize;
		}else{
			viewWidth = (int) (getPaddingLeft()+radius*2*count+circlePadding*(count-1)+getPaddingRight())+1;
		}
		
		if(heightMode==MeasureSpec.EXACTLY){
			viewHeight = heightSize;
		}else{
			viewHeight = (int) (getPaddingTop()+radius*2+getPaddingBottom())+1;
		}
		setMeasuredDimension(viewWidth, viewHeight);
	}
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		
		float firstX = getPaddingLeft()+radius;
		float firstY = getPaddingTop() +radius;
		
		//draw stroked circles
		for (int i = 0; i < count; i++) {
			float dx = firstX + circlePadding * i + radius * 2 * i;
			float dy = firstY;
			if(mPaintStroke.getStrokeWidth()>0){
				canvas.drawCircle(dx, dy, radius, mPaintStroke);// 画空心圈
			}

			if (mPaintPage.getAlpha() > 0) {
				canvas.drawCircle(dx, dy, radius, mPaintPage);
			}
		}
		
		//Draw the filled circle 
		float dx = firstX+realCurrentItem*circlePadding+radius*2*realCurrentItem;
		float dy = firstY;
		canvas.drawCircle(dx, dy, radius, mPaintFill);
		
	}




	@Override
	public void onPageScrollStateChanged(int state) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		
	}
	
//	@Override
//	protected void onRestoreInstanceState(Parcelable state) {
//		// TODO Auto-generated method stub
//		SavedState savedState = (SavedState) state;
//		super.onRestoreInstanceState(savedState.getSuperState());
//		realCurrentItem = savedState.currentPage;
//		count = savedState.totalPage;
//		SWLog.LogE("CircleIndicator----onRestoreInstanceState"+"   realCurrentItem:"+realCurrentItem+"    count:"+count);
//		requestLayout();
//
//	}
//	
//	@Override
//	protected Parcelable onSaveInstanceState() {
//		// TODO Auto-generated method stub
//		SWLog.LogI("CircleIndicator----onSaveInstanceState   totalPage:"+count+"   currentPage:"+realCurrentItem);
//		SavedState savedState = new SavedState(super.onSaveInstanceState());
//		savedState.totalPage = count;
//		savedState.currentPage = realCurrentItem;
//		return savedState;
//	}
	
//	public static class SavedState extends BaseSavedState {
//		int totalPage;
//		int currentPage;
//
//		/**
//		 * @param arg0
//		 */
//		public SavedState(Parcelable arg0) {
//			super(arg0);
//			// TODO Auto-generated constructor stub
//		}
//
//		/**
//		 * @param arg0
//		 */
//		public SavedState(Parcel in) {
//			super(in);
//			// TODO Auto-generated constructor stub
//			totalPage = in.readInt();
//			currentPage = in.readInt();
//		}
//
//		@Override
//		public void writeToParcel(Parcel dest, int flags) {
//			// TODO Auto-generated method stub
//			super.writeToParcel(dest, flags);
//			dest.writeInt(totalPage);
//			dest.writeInt(currentPage);
//		}
//
//
//		public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
//			@Override
//			public SavedState createFromParcel(Parcel in) {
//				return new SavedState(in);
//			}
//
//			@Override
//			public SavedState[] newArray(int size) {
//				return new SavedState[size];
//			}
//		};
//
//	}


	@Override
	public void onPageSelected(int position) {
		realCurrentItem = position % count; 
		invalidate();
	}

	/**
	 * @return the radius
	 */
	public float getRadius() {
		return radius;
	}

	/**
	 * @param radius the radius to set
	 */
	public void setRadius(float radius) {
		this.radius = radius;
	}

	/**
	 * @return the circlePadding
	 */
	public float getCirclePadding() {
		return circlePadding;
	}

	/**
	 * @param circlePadding the circlePadding to set
	 */
	public void setCirclePadding(float circlePadding) {
		this.circlePadding = circlePadding;
	}

	/**
	 * @return the fillColor
	 */
	public int getFillColor() {
		return fillColor;
	}

	/**
	 * @param fillColor the fillColor to set
	 */
	public void setFillColor(int fillColor) {
		this.fillColor = fillColor;
	}

	/**
	 * @return the strokeColor
	 */
	public int getStrokeColor() {
		return strokeColor;
	}

	/**
	 * @param strokeColor the strokeColor to set
	 */
	public void setStrokeColor(int strokeColor) {
		this.strokeColor = strokeColor;
	}

	/**
	 * @return the strokeWidth
	 */
	public float getStrokeWidth() {
		return strokeWidth;
	}

	/**
	 * @param strokeWidth the strokeWidth to set
	 */
	public void setStrokeWidth(float strokeWidth) {
		this.strokeWidth = strokeWidth;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * @return the realCurrentItem
	 */
	public int getRealCurrentItem() {
		return realCurrentItem;
	}

	/**
	 * @param realCurrentItem the realCurrentItem to set
	 */
	public void setRealCurrentItem(int realCurrentItem) {
		this.realCurrentItem = realCurrentItem;
	}

	
	
	

}
