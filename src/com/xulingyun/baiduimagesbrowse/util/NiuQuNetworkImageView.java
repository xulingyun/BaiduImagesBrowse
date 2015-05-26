package com.xulingyun.baiduimagesbrowse.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;

import com.android.volley.toolbox.NetworkImageView;
import com.xulingyun.baiduimagesbrowse.R;

public class NiuQuNetworkImageView extends NetworkImageView {
	private Bitmap bitmap;
	// 定义两个常量,这两个常量指定该图片横向,纵向上都被划分为20格
	private final int WIDTH = 20;
	private final int HEIGHT = 20;
	// 记录该图片上包含441个顶点
	private final int COUNT = (WIDTH + 1) * (HEIGHT + 1);
	// 定义一个数组,记录Bitmap上的21*21个点的坐标
	private final float[] verts = new float[COUNT * 2];
	// 定义一个数组,记录Bitmap上的21*21个点经过扭曲后的坐标
	// 对图片扭曲的关键就是修改该数组里元素的值
	private final float[] orig = new float[COUNT * 2];
	private float K = 1;

	public NiuQuNetworkImageView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public NiuQuNetworkImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public NiuQuNetworkImageView(Context context) {
		super(context);
		init();
	}

	private void init() {
		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a1);
		float bitmapWidth = bitmap.getWidth();
		float bitmapHeight = bitmap.getHeight();
		int index = 0;
		for (int y = 0; y <= HEIGHT; y++) {
			float fy = bitmapHeight * y / HEIGHT;
			for (int x = 0; x <= WIDTH; x++) {
				float fx = bitmapWidth * x / WIDTH;
				// 初始化orig,verts数组
				// 初始化,orig,verts两个数组均匀地保存了21 * 21个点的x,y坐标　
				orig[index * 2 + 0] = verts[index * 2 + 0] = fx;
				orig[index * 2 + 1] = verts[index * 2 + 1] = fy;
				index += 1;
			}
		}
		// 设置背景色
		setBackgroundColor(Color.WHITE);

		for (int i = 0; i < HEIGHT + 1; i++) {
			for (int j = 0; j < WIDTH + 1; j++) {
				verts[(i * (WIDTH + 1) + j) * 2 + 0] += 0;
				float offsetY = (float) Math.sin((float) j / WIDTH * 2 * Math.PI + K * 2 * Math.PI);
				verts[(i * (WIDTH + 1) + j) * 2 + 1] = orig[(i * (WIDTH + 1) + j) * 2 + 1] + offsetY * 50;
			}
		}
		K += 0.1F;
	}

	@Override
	protected void onDraw(Canvas canvas) {
//		for (int i = 0; i < HEIGHT + 1; i++) {
//			for (int j = 0; j < WIDTH + 1; j++) {
//				verts[(i * (WIDTH + 1) + j) * 2 + 0] += 0;
//				float offsetY = (float) Math.sin((float) j / WIDTH * 2 * Math.PI + K * 2 * Math.PI);
//				verts[(i * (WIDTH + 1) + j) * 2 + 1] = orig[(i * (WIDTH + 1) + j) * 2 + 1] + offsetY * 50;
//			}
//		}
//		K += 0.1F;
		canvas.drawBitmapMesh(bitmap, WIDTH, HEIGHT, verts, 0, null, 0, null);
		invalidate();
	}

}
