package com.dtrupenn.paintbrush;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class PaletteView extends View{
	
	protected ShapeDrawable squareA, squareB, squareC, squareD, squareE, squareF;
	protected static Paint p = new Paint();

	public PaletteView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}
	
	public PaletteView(Context c, AttributeSet a){
		super(c, a);
		init();
	}
	
	protected void init(){
		// Red Square
		squareA = new ShapeDrawable(new RectShape());
		squareA.getPaint().setColor(Color.RED);
		//squareA.setBounds(10, height-200, (width/10) - 10, height-150);
		squareA.setBounds(5, 20, 45, 60);
		
		// Yellow Square
		squareB = new ShapeDrawable(new RectShape());
		squareB.getPaint().setColor(Color.YELLOW);
		//squareB.setBounds((width/10), height-200, (width/5 - 20), height-150);
		squareB.setBounds(50, 20, 90, 60);
		
		// Green Square
		squareC = new ShapeDrawable(new RectShape());
		squareC.getPaint().setColor(Color.GREEN);
		//squareC.setBounds((width/5) - 10, height-200, (3*width/10- 30), height-150);
		squareC.setBounds(95, 20, 135, 60);
		
		// Blue Square
		squareD = new ShapeDrawable(new RectShape());
		squareD.getPaint().setColor(Color.BLUE);
		//squareD.setBounds((3*width/10) - 20, height-200, (2*width/5) - 40, height-150);
		squareD.setBounds(140, 20, 180, 60);
		
		// Black Square
		squareE = new ShapeDrawable(new RectShape());
		squareE.getPaint().setColor(Color.BLACK);
		//squareE.setBounds((2*width/5) - 30, height-200, (width/2) - 50, height-150);
		squareE.setBounds(185, 20, 225, 60);
		
		// Grey Square
		squareF = new ShapeDrawable(new RectShape());
		squareF.getPaint().setColor(Color.DKGRAY);
		//squareF.setBounds(width/2 + 130, height-200, (width/2) + 210, height-150);
		squareF.setBounds(230, 20, 270, 60);
		
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
		paint.setTextSize(20);
		c.drawText("thin", 275, 45, paint);
		c.drawText("THICK", 310, 45, paint);
	}
	
	public boolean onTouchEvent(MotionEvent e){
		//Checks for any button actions
		if (e.getAction() == MotionEvent.ACTION_DOWN){
			// Getting them digits
			int x = (int)e.getX();
			int y = (int)e.getY();
			if ((x > 0 && x < 360) && (y > 0 && y < 60)){
			
				//Touch Red Square
				if (x < 45) {
					p.setColor(Color.RED);
				//Touch Yellow Square
				} else if (x < 90) {
					p.setColor(Color.YELLOW);
				//Touch Green Square
				} else if (x < 135) {
					p.setColor(Color.GREEN);
				//Touch Blue Square
				} else if (x < 180) {
					p.setColor(Color.BLUE);
				//Touch Black Square
				} else if (x < 225) {
					p.setColor(Color.BLACK);
				//Touch Grey Square
				} else if (x < 270) {
					p.setColor(Color.DKGRAY);
				//Touch Thin
				} else if (x < 310) {
					p.setStrokeWidth(5);
				//Touch THICK
				} else {
					p.setStrokeWidth(15);
				}
			}
			return true;
		}
		else
			return false;
	}

}
