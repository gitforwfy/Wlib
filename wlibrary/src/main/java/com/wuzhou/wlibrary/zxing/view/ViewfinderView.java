package com.wuzhou.wlibrary.zxing.view;

/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.wuzhou.wlibrary.R;
import com.wuzhou.wlibrary.zxing.camera.CameraManager;


/**
 * This view is overlaid on top of the camera preview. It adds the viewfinder
 * rectangle and partial transparency outside it, as well as the laser scanner
 * beginAnimation and result points.
 *
 */
public final class ViewfinderView extends View {
	/**
	 * 刷新界面的时间
	 */
	private final long ANIMATION_DELAY = 10L;

	/**
	 * 四个绿色边角对应的长度
	 */
	private int ScreenRate=18;
	
	/**
	 * 四个绿色边角对应的宽度
	 */
	private int CORNER_WIDTH = 3;
	/**
	 * 扫描框中的中间线的宽度
	 */
	private final int MIDDLE_LINE_WIDTH = 6;
	
	/**
	 * 扫描框中的中间线的与扫描框左右的间隙
	 */
	private final int MIDDLE_LINE_PADDING = 5;
	
	/**
	 * 中间那条线每次刷新移动的距离
	 */
	private final int SPEEN_DISTANCE = 5;
	
	/**
	 * 手机的屏幕密度
	 */
	private float density;
	/**
	 * 字体大小
	 */
	private int TEXT_SIZE = 12;
	/**
	 * 字体距离扫描框下面的距离
	 */
	private final int TEXT_PADDING_TOP = 30;
	
	/**
	 * 画笔对象的引用
	 */
	private Paint paint;

	private Paint paint_eight;

	private Paint paint_text;
	
	/**
	 * 中间滑动线的最顶端位置
	 */
	private int slideTop;
	
	/**
	 * 中间滑动线的最底端位置
	 */
	private int slideBottom;


	/**
	 * 扫描线宽
	 */

	private int scan_width=18;

	private final int maskColor;

	private static final int new_add_margin=10;
	/**扫描框上的文字*/
	private String text;
	/**扫描线出现的范围*/
	private Rect lineRect;
	/**扫描线*/
	private Bitmap line_bitmap;
	public ViewfinderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		density = context.getResources().getDisplayMetrics().density;
		//将像素转换成dp
		ScreenRate = (int)(ScreenRate * density);
		TEXT_SIZE= (int) (TEXT_SIZE * density);
		scan_width= (int) (scan_width*density);
		CORNER_WIDTH= (int) (CORNER_WIDTH*density);
		maskColor = Color.parseColor("#60000000");

//		text = getResources().getString(R.string.scan_text);
		text ="将二维码放入框中，即可自动扫描";
		lineRect = new Rect();
		line_bitmap =((BitmapDrawable)(getResources().getDrawable(R.mipmap.saomiao_img_2x))).getBitmap();
		initPaint();
	}
	private float textWidth;
	private void initPaint(){
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(maskColor);

		paint_eight=new Paint();
		paint_eight.setAntiAlias(true);
		//画扫描框边上的角，总共8个部分
		paint_eight.setColor(Color.WHITE);

		paint_text=new Paint();
		paint_text.setAntiAlias(true);
		//画扫描框上的字
		paint_text.setColor(Color.WHITE);
		paint_text.setTextSize(TEXT_SIZE);
		//paint.setAlpha(0x40);
		paint_text.setTypeface(Typeface.create("System", Typeface.BOLD));
		textWidth = paint_text.measureText(text);
	}
	//中间的扫描框，你要修改扫描框的大小，去CameraManager里面修改
	private Rect frame;
	@Override
	public void onDraw(Canvas canvas) {
		if(frame==null){
			frame = CameraManager.get().getFramingRect();
			if(frame==null){
				return;
			}
			slideTop = frame.top;
			slideBottom = frame.bottom;
		}
		//获取屏幕的宽和高
		int width = canvas.getWidth();
		int height = canvas.getHeight();
		//画出扫描框外面的阴影部分，共四个部分，扫描框的上面到屏幕上面，扫描框的下面到屏幕下面
		//扫描框的左边面到屏幕左边，扫描框的右边到屏幕右边
		canvas.drawRect(0, 0, width, frame.top, paint);
		canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
		canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1,
				paint);
		canvas.drawRect(0, frame.bottom + 1, width, height, paint);
		
		canvas.drawRect(frame.left-CORNER_WIDTH-new_add_margin, frame.top-CORNER_WIDTH-new_add_margin, frame.left + ScreenRate-CORNER_WIDTH-new_add_margin,
				frame.top + CORNER_WIDTH-CORNER_WIDTH-new_add_margin, paint_eight);


		canvas.drawRect(frame.left-CORNER_WIDTH-new_add_margin, frame.top-CORNER_WIDTH-new_add_margin, frame.left + CORNER_WIDTH-CORNER_WIDTH-new_add_margin, frame.top
				+ ScreenRate-CORNER_WIDTH-new_add_margin, paint_eight);


		canvas.drawRect(frame.right - ScreenRate+CORNER_WIDTH+new_add_margin, frame.top-CORNER_WIDTH-new_add_margin, frame.right+CORNER_WIDTH+new_add_margin,
				frame.top + CORNER_WIDTH-CORNER_WIDTH-new_add_margin, paint_eight);


		canvas.drawRect(frame.right - CORNER_WIDTH+CORNER_WIDTH+new_add_margin, frame.top-CORNER_WIDTH-new_add_margin, frame.right+CORNER_WIDTH+new_add_margin, frame.top
				+ ScreenRate-CORNER_WIDTH-new_add_margin, paint_eight);


		canvas.drawRect(frame.left-CORNER_WIDTH-new_add_margin, frame.bottom - CORNER_WIDTH+CORNER_WIDTH+new_add_margin, frame.left
				+ ScreenRate-CORNER_WIDTH-new_add_margin, frame.bottom+CORNER_WIDTH+new_add_margin, paint_eight);


		canvas.drawRect(frame.left-CORNER_WIDTH-new_add_margin, frame.bottom - ScreenRate+CORNER_WIDTH+new_add_margin,
				frame.left + CORNER_WIDTH-CORNER_WIDTH-new_add_margin, frame.bottom+CORNER_WIDTH+new_add_margin, paint_eight);


		canvas.drawRect(frame.right - ScreenRate+CORNER_WIDTH+new_add_margin, frame.bottom - CORNER_WIDTH+CORNER_WIDTH+new_add_margin,
				frame.right+CORNER_WIDTH+new_add_margin, frame.bottom+CORNER_WIDTH+new_add_margin, paint_eight);


		canvas.drawRect(frame.right - CORNER_WIDTH+CORNER_WIDTH+new_add_margin, frame.bottom - ScreenRate+CORNER_WIDTH+new_add_margin,
				frame.right+CORNER_WIDTH+new_add_margin, frame.bottom+CORNER_WIDTH+new_add_margin, paint_eight);


		//绘制中间的线,每次刷新界面，中间的线往下移动SPEEN_DISTANCE

		slideTop += SPEEN_DISTANCE;
		if(slideTop >= frame.bottom-scan_width){
			slideTop = frame.top;
		}
		lineRect.left = frame.left;
		lineRect.right = frame.right;
		lineRect.top = slideTop;
		lineRect.bottom = slideTop + scan_width;
		canvas.drawBitmap(line_bitmap, null, lineRect, paint);

		canvas.drawText(text, (width - textWidth)/2, frame.top-(float)TEXT_PADDING_TOP *density, paint_text);

		//只刷新扫描框的内容，其他地方不刷新
		postInvalidateDelayed(ANIMATION_DELAY, frame.left, frame.top,
				frame.right, frame.bottom);
	}
}
