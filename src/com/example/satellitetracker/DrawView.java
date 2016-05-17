package com.example.satellitetracker;

import java.util.Vector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

/**
 * This class manage the painting on screen
 * @author François Jolain
 *
 */
public class DrawView extends View {
	
	private Paint paint;
	private Vector<Point> points = new Vector<Point>();

	// constant to convert angle received by sky into pixel on screen
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

	/**
	 * Call back at every refresh screen
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	
		// Paint a new black background
		paint.setColor(Color.BLACK);
		canvas.drawPaint(paint);
		
		
		// Paint each point contained in the tab
		for(Point point : points) {
			paint.setColor(point.getColor());
			
			int pixelX = (int) (point.getX()*angle2pixelX);
			int pixelY = (int) (point.getY()*angle2pixelY);
			
			canvas.drawCircle(pixelX, pixelY, point.getSize(), paint);
		}
	}
	
	/**
	 * Set a new collection of point and ask a screen update
	 * @param points collection of points
	 */
	public void setPoints(Vector<Point> points) {
		this.points = points;
		invalidate();
	}
	
	
	
}