package com.example.shoppingmall.utils;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

@SuppressWarnings({"unused"})
public class GrounpVieww extends ViewGroup {
	private static final int SIDE_MARGIN = 10;// 左右间距
	private static final int TEXT_MARGIN = 10;

	/**
	 * @param context
	 */
	public GrounpVieww(Context context) {
		super(context);
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public GrounpVieww(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public GrounpVieww(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	// 先走的是下面的这一步，，，嘿嘿 首先明确的是 这个 OnMeasure 是先测量 自身的宽和高。里面的参数是由父view 给 传过不的来的

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int x = 0;// 横坐标
		int y = 0;// 纵坐标
		int rows = 1;// 总行数
//		System.out.println("我要看看这个宽的Mode  是一个啥东西。。。"
//				+ MeasureSpec.getMode(widthMeasureSpec));
//		System.out.println("我要看看这个宽的的具体值   是一个啥东西。。。"
//				+ MeasureSpec.getSize(widthMeasureSpec));

		int specWidth = MeasureSpec.getSize(widthMeasureSpec);

		// 在这个虚拟机上，宽度应该是480px 但是又定义了下面的这个方法所以 宽度变成了460px
		int actualWidth = specWidth - SIDE_MARGIN * 2;// 实际宽度
		int childCount = getChildCount();
		for (int index = 0; index < childCount; index++) {
			View child = getChildAt(index);

			child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
			int width = child.getMeasuredWidth();
			
			int height = child.getMeasuredHeight();
			x += width + TEXT_MARGIN;
			if (x > actualWidth) {
				// 换行
				x = width;
				rows++;
			}
			y = rows * (height + TEXT_MARGIN);
		}
		// 下面的 这个方法可是关键，啊，，它决定 了 这个view　是多宽多高的啊，，，
		setMeasuredDimension(actualWidth, y );
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int childCount = getChildCount();
		int autualWidth = r - l;
		int x = SIDE_MARGIN;// 横坐标开始
		int y = 0;// 纵坐标开始
		int rows = 1;
		for (int i = 0; i < childCount; i++) {
			View view = getChildAt(i);
//			view.setBackgroundColor(Color.GREEN);
			int width = view.getMeasuredWidth();
			int height = view.getMeasuredHeight();
			x += width + TEXT_MARGIN;
			if (x > autualWidth) {
				x = width + SIDE_MARGIN;
				rows++;
			}
			y = rows * (height + TEXT_MARGIN);
			if (i == 0) {
				view.layout(x - width - TEXT_MARGIN, y - height, x
						- TEXT_MARGIN, y);
			} else {
				view.layout(x - width, y - height, x, y);
			}
		}
	};

}