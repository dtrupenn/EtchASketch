package com.dtrupenn.paintbrush;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class BrushView extends View{

	List<Dot> dots = new ArrayList<Dot>();
	
	public BrushView(Context context) {
		super(context);
	}

	
	public BrushView(Context c, AttributeSet a){
		super(c, a);
	}
	
	@SuppressLint("DrawAllocation")
	protected void onDraw(Canvas c){	
		// I whip my brush back and forth
		Path path = new Path();
		Paint ps = new Paint(Paint.ANTI_ALIAS_FLAG);
		ps.setStyle(Paint.Style.STROKE);
		for(Dot dot : dots) {
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
		PaletteView palette = (PaletteView) findViewById(R.id.palette_view);
		if(e.getAction() == MotionEvent.ACTION_MOVE || e.getAction() == MotionEvent.ACTION_UP || e.getAction() == MotionEvent.ACTION_DOWN) {
			Dot dot = new Dot();
			dot.x = e.getX();
			dot.y = e.getY();
			dot.c = palette.p.getColor();
			dot.s = (int) palette.p.getStrokeWidth();
			dots.add(dot);
			if(e.getAction() == MotionEvent.ACTION_DOWN)
				dot.first = true;
			invalidate();
			Log.d("DETECTED", "point: " + dot);
			return true;
		}
		return false;
	}
	
	public void clearDots(){
		dots.clear();
		invalidate();
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
