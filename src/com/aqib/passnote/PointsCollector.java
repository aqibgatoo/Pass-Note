package com.aqib.passnote;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Point;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class PointsCollector implements OnTouchListener {

	public  static final String LOGTAG = "MAG";
private List<Point> list = new ArrayList<Point>();

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		int x = Math.round(event.getRawX());
		int y = Math.round(event.getRawY());
		Log.d(LOGTAG, String.format("coordinates %d %d ", x, y));
		
		list.add(new Point(x, y));

		if (list.size()==4) {
		}
		
		return false;
	}
}
