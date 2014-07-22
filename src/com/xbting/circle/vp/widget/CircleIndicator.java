
package com.xbting.circle.vp.widget;



import com.xbting.circle.vp.R;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.View;

/**
 * author xbting
 * 
 */
public class CircleIndicator extends View implements OnPageChangeListener{
	
	private CircleViewPager mCircleViewPager;
	/**圆点半径*/
	private float radius ;
	/**圆圈间距*/
	private float circlePadding;
	/**圆点的填充颜色*/
	private int fillColor;
	/**空心圆点的环颜色*/
	private int strokeColor;
	/**空心圆点的环宽度*/
	private float strokeWidth;

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
		radius  = res.getDimension(R.dimen.default_lock_prvview_radius);
		circlePadding = res.getDimension(R.dimen.default_lock_preview_circle_padding);
		strokeWidth= res.getDimension(R.dimen.default_lock_preview_stroke_width);
		fillColor = res.getColor(R.color.default_lock_preview_view_fill_color);
		strokeWidth = res.getColor(R.color.default_lock_preview_view_stroke_color);
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
		float defaultRadius  = res.getDimension(R.dimen.default_lock_prvview_radius);
		float defaultCirclePadding = res.getDimension(R.dimen.default_lock_preview_circle_padding);
		float defaultStrokeWidth= res.getDimension(R.dimen.default_lock_preview_stroke_width);
		int defaultFillColor = res.getColor(R.color.default_lock_preview_view_fill_color);
		int defaultStrokeColor = res.getColor(R.color.default_lock_preview_view_stroke_color);
		
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LockPatternPreView);
		radius = ta.getDimension(R.styleable.LockPatternPreView_radius, defaultRadius);
		circlePadding = ta.getDimension(R.styleable.LockPatternPreView_cellPadding, defaultCirclePadding);
		strokeWidth = ta.getDimension(R.styleable.LockPatternPreView_strokeWidth, defaultStrokeWidth);
		fillColor = ta.getColor(R.styleable.LockPatternPreView_fillColor, defaultFillColor);
		strokeColor = ta.getColor(R.styleable.LockPatternPreView_strokeColor, defaultStrokeColor);
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
	}

	/**
	 * 初始化
	 * 
	 * void
	 */
	private void init() {
		mPaintStroke.setStyle(Style.STROKE);
		mPaintStroke.setColor(strokeColor);
		mPaintStroke.setStrokeWidth(strokeWidth);
		
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
			float dx = firstX+circlePadding*i+radius*2*i;
			float dy = firstY;
			canvas.drawCircle(dx, dy, radius, mPaintStroke);//画空心圈
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
