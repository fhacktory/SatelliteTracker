package com.example.satellitetracker;

import java.util.Vector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

public class DrawView extends View {
	
	private Paint paint;
	private Vector<Point> points = new Vector<Point>();
	private String text = "";
	
	private double angle2pixelX;
	private double angle2pixelY;
	
	public DrawView(Context context, int width, int height) {
		super(context);
		paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		
		// A width screen represents a quart of sky longitude
		angle2pixelX = width*4.0/360.0;
		// A height screen represents a half of sky latitude
		angle2pixelY = height*2.0/90.0;
		
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
				
		paint.setColor(Color.BLACK);
		canvas.drawPaint(paint);
		
		paint.setAntiAlias(false);
		for(Point point : points) {
			paint.setColor(point.getColor());
			
			
			int pixelX = (int) (point.getX()*angle2pixelX);
			int pixelY = (int) (point.getY()*angle2pixelY);
			
			canvas.drawCircle(pixelX, pixelY, 20, paint);
		}
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