package com.wuzhou.wlibrary.zxing.listener;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;

import com.google.zxing.Result;

/**
 * 扫描接口
 *
 * @author Hitoha
 * @version 1.00 2015/04/27 新建
 */
public interface OnCaptureListener {

	/**
	 * 获取Handler
	 *
	 * @return Handler
	 */
	public Handler getHandler();

	/**
	 * 返回解析结果
	 *
	 * @param result
	 *            解析结果集
	 * @param barcode
	 *            被解析的图像
	 */
	public void handleDecode(Result result, Bitmap barcode);

	/**
	 * 设置返回前画面数据
	 *
	 * @param resultCode
	 *            ResultCode
	 * @param data
	 *            Intent
	 */
	public void onSetResult(int resultCode, Intent data);

	/**
	 * 画面跳转
	 *
	 * @param intent
	 *            Intent
	 */
	public void onStartActivity(Intent intent);

	/**
	 * 画面结束
	 */
	public void onFinish();
}
