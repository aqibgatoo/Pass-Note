package com.aqib.passnote.data;

/*
 * Thanks to Nathan Hazout for supplying me with this reconstructed project from lecture 24,
 * which I sadly neglected to save as I went along! That in turn gave us all a bit of a headache,
 * Since the original version of lecture 25 was pretty confusing, and I wasn't able to redo it
 * without going through the first 24 lectures myself, which I couldn't face doing.
 * A big thanks to Nathan, and apologies to everyone who put up with the original lecture 25
 * videos. - John Purcell, October 11 2013
 */

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Point;

public class Database extends SQLiteOpenHelper {

	private static final String POINTS_TABLE = "POINTS";
	private static final String COL_ID = "ID";
	private static final String COL_X = "X";
	private static final String COL_Y = "Y";

	public Database(Context context) {
		super(context, "note.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = String
				.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s INTEGER NOT NULL, %s INTEGER NOT NULL)",
						POINTS_TABLE, COL_ID, COL_X, COL_Y);
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	public void storePoints(List<Point> points){
		SQLiteDatabase db = getWritableDatabase();
		
		db.delete(POINTS_TABLE, null, null);
		
		int i = 0;
		for(Point point: points){
			ContentValues values = new ContentValues();
			
			values.put(COL_ID, i);
			values.put(COL_X, point.x);
			values.put(COL_Y, point.y);
			db.insert(POINTS_TABLE, null, values);
			i++;
		}
		
		db.close();
	}
	
	public List<Point> getPoints(){
		List<Point> points = new ArrayList<Point>();
		
		SQLiteDatabase db = getReadableDatabase();
		
		String sql = String.format("SELECT %s, %s FROM %s ORDER BY %s",COL_X,COL_Y,POINTS_TABLE, COL_ID);
		Cursor cursor = db.rawQuery(sql, null);
		while(cursor.moveToNext()){
			int x = cursor.getInt(0);
			int y = cursor.getInt(1);
			
			points.add(new Point(x,y));
		}
		
		db.close();
		
		return points;
	}
}
