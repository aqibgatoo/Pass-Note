package com.aqib.passnote;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class ImageActivity extends Activity {

	// private PointsCollector pointsCollector=new PointsCollector();
	private static final CharSequence title = "Create your Pass Point Sequence";
	private static final CharSequence message = "Touch four points to create your pass point sequence";

	private List<Point> list = new ArrayList<Point>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);
		
		addTouchListener();
		showPrompt();
		
		
	}

	private void showPrompt() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(title).setMessage(message)
				.setPositiveButton("OK", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					}
				});

		AlertDialog alertDialog = builder.create();
		alertDialog.show();

	}

	private void addTouchListener() {

		ImageView imageView = (ImageView) findViewById(R.id.touch_image);
		// imageView.setOnTouchListener(pointsCollector);
		imageView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int x = Math.round(event.getRawX());
				int y = Math.round(event.getRawY());
				Log.d(MainActivity.LOGTAG,
						String.format("coordinates %d %d ", x, y));

				list.add(new Point(x, y));

				if (list.size() == 4) {
					Intent intent = new Intent(ImageActivity.this,MainActivity.class);
					startActivity(intent);

				}
				return false;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image, menu);
		return true;
	}

}
