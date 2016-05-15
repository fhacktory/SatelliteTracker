package com.example.satellitetracker;

import java.util.Vector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class DrawView extends View {
	
	private Paint paint;
	private Vector<Point> points = new Vector<Point>();
	private String text = "";
	
	public DrawView(Context context) {
		super(context);
		paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
				
		paint.setColor(Color.BLACK);
		canvas.drawPaint(paint);
		
		paint.setAntiAlias(false);
		for(Point point : points) {
			paint.setColor(point.getColor());
			canvas.drawCircle(point.getX()*40, point.getY()*10, 20, paint);
		}
		paint.setColor(Color.RED);
	}
	
	public void setPoints(Vector<Point> points) {
		this.points = points;
		invalidate();
	}
	
	public void setText(String text) {
		this.text = text;
		//invalidate();
	}
	
	
}
