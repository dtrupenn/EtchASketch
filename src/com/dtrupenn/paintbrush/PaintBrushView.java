package com.dtrupenn.paintbrush;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class PaintBrushView extends View{
	
	protected ShapeDrawable squareA, squareB, squareC, squareD, squareE, squareF;
	int width, height;
	protected Paint p = new Paint();
	static List<Dot> points = new ArrayList<Dot>();

	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	public PaintBrushView(Context context) {
		super(context);
		
		//Get screen dimensions and initialize
		Point size = new Point();
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		if(Build.VERSION.SDK_INT >= 13){
			display.getSize(size);
			width = size.x;
			height = size.y;
		}
		else {
			width = display.getWidth();
			height = display.getHeight();
		}
		init();
	}
	
	public PaintBrushView(Context c, AttributeSet a){
		super(c, a);
		init();
	}
	
	protected void init(){
		// Red Square
		squareA = new ShapeDrawable(new RectShape());
		squareA.getPaint().setColor(Color.RED);
		squareA.setBounds(10, height-200, (width/10) - 10, height-150);
		
		// Yellow Square
		squareB = new ShapeDrawable(new RectShape());
		squareB.getPaint().setColor(Color.YELLOW);
		squareB.setBounds((width/10), height-200, (width/5 - 20), height-150);
		
		// Green Square
		squareC = new ShapeDrawable(new RectShape());
		squareC.getPaint().setColor(Color.GREEN);
		squareC.setBounds((width/5) - 10, height-200, (3*width/10- 30), height-150);
		
		// Blue Square
		squareD = new ShapeDrawable(new RectShape());
		squareD.getPaint().setColor(Color.BLUE);
		squareD.setBounds((3*width/10) - 20, height-200, (2*width/5) - 40, height-150);
		
		// Black Square
		squareE = new ShapeDrawable(new RectShape());
		squareE.getPaint().setColor(Color.BLACK);
		squareE.setBounds((2*width/5) - 30, height-200, (width/2) - 50, height-150);
		
		// Grey Square
		squareF = new ShapeDrawable(new RectShape());
		squareF.getPaint().setColor(Color.DKGRAY);
		squareF.setBounds(width/2 + 130, height-200, (width/2) + 210, height-150);
		
		// Set default paint values
		p.setColor(Color.BLACK);
		p.setStrokeWidth(5);
	}
	
	
	@SuppressLint("DrawAllocation")
	protected void onDraw(Canvas c){
		
		// Drawing some shapes like it's 1999
		squareA.draw(c);
		squareB.draw(c);
		squareC.draw(c);
		squareD.draw(c);
		squareE.draw(c);
		squareF.draw(c);
		
		Paint paint = new Paint();
		
		// Writing text like I just don't care!
		paint.setColor(Color.BLACK);
		paint.setTextAlign(Paint.Align.LEFT);
		paint.setTypeface(Typeface.SANS_SERIF);
		paint.setTextSize(30);
		c.drawText("thin", width/2 - 40, height-165, paint);
		c.drawText("THICK", width/2 + 30, height-165, paint);		
		
		c.drawText("Clear", width/2 + 135, height-165, paint);
		
		// I whip my brush back and forth
		Path path = new Path();
		Paint ps = new Paint(Paint.ANTI_ALIAS_FLAG);
		ps.setStyle(Paint.Style.STROKE);
		for(Dot dot : points) {
			if(dot.first) {
				if(!path.isEmpty()){
					c.drawPath(path, ps);
					path = new Path();
				}
				ps.setColor(dot.c);
				ps.setStrokeWidth(dot.s);
				path.moveTo(dot.x, dot.y);
			}
			else {
				path.lineTo(dot.x, dot.y);
			}
			Log.d("DRAWING", "Painting: "+dot);
		}
		if(!path.isEmpty()){
			c.drawPath(path, ps);
		}
	}
	
	public boolean onTouchEvent(MotionEvent e){
		// Getting them digits
		int x = (int)e.getX();
		int y = (int)e.getY();
		
		
		//Checks for any drawing actions
		if ((x > 0 && x < width) && (y > 0 && y < height-200)) {
			if(e.getAction() == MotionEvent.ACTION_MOVE || e.getAction() == MotionEvent.ACTION_UP || e.getAction() == MotionEvent.ACTION_DOWN) {
				Dot point = new Dot();
				point.x = e.getX();
				point.y = e.getY();
				point.c = p.getColor();
				point.s = (int)p.getStrokeWidth();
				points.add(point);
				if(e.getAction() == MotionEvent.ACTION_DOWN)
					point.first = true;
				invalidate();
				Log.d("DETECTED", "point: " + point);
				return true;
			}
		}
		
		//Checks for any button actions
		if (e.getAction() == MotionEvent.ACTION_DOWN){
			if(y > height-200 && y < height-150) {
				if(x > 10 && x < (width/10) - 10){
					Log.v("TOUCH", "RED");
					p.setColor(Color.RED);
				}
				else if(x > (width/10) && x < (width/5 - 20)){
					Log.v("TOUCH", "YELLOW");
					p.setColor(Color.YELLOW);
				}
				else if(x > (width/5 - 10) && x < (3*width/10- 30)){
					Log.v("TOUCH", "GREEN");
					p.setColor(Color.GREEN);
				}
				else if(x > (3*width/10- 20) && x < (2*width/5 - 40)){
					Log.v("TOUCH", "BLUE");
					p.setColor(Color.BLUE);
				}
				else if(x > (2*width/5 - 30) && x < (width/2 - 50)){
					Log.v("TOUCH", "BLACK");
					p.setColor(Color.BLACK);
				}
				else if(x > (width/2 - 40) && x < (width/2 + 20)){
					Log.v("TOUCH", "Thin");
					p.setStrokeWidth(5);
				}
				else if(x > (width/2 + 30) && x < (width/2 + 100)){
					Log.v("TOUCH", "Thick");
					p.setStrokeWidth(15);
				}
				else if (x > width/2 + 130 && x < width/2 + 210) {
					Log.v("TOUCH", "Clear");
					clearDots();
					invalidate();
				}
			}
			return true;
		}
		return false;
	}
	
	public static void clearDots(){
		points.clear();
	}
	
	class Dot {
		float x, y;
		int c, s;
		boolean first = false;
		
		@Override
		public String toString() {
			return x + ", " + y;
		}
	}

}
