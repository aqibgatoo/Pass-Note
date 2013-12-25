package com.aqib.passnote;

import java.util.List;

import com.aqib.passnote.data.NoteItem;
import com.aqib.passnote.data.NoteItemDataSource;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Note;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends ListActivity {
	public static final String LOGTAG = "MAG";
//	private static final String KEY = "hello";

	// private EditText text;

	private NoteItemDataSource dataSource;
	List<NoteItem> noteList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		setButtonListener();
		
		refreshDispalay();

		// text = (EditText) findViewById(R.id.edit_text);
		// SharedPreferences preferences=getPreferences(MODE_PRIVATE);
		// String string=preferences.getString(KEY, null);
		// text.setText(string);
		//
		// if (preferences!=null) {
		// addSharedPreferences();
		// }

	}

//	private void setButtonListener() {
//
//		Button button = (Button) findViewById(R.id.save_button);
//		button.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				// text = (EditText) findViewById(R.id.edit_text);
//				// String value=text.getText().toString();
//				// SharedPreferences preferences=getPreferences(MODE_PRIVATE);
//				// SharedPreferences.Editor editor=preferences.edit();
//				// editor.putString(KEY,value);
//				// editor.commit();
//
//				// String value = getString(R.id.edit_text);
//				//
//				// preferences = getPreferences(MODE_PRIVATE);
//				// SharedPreferences.Editor editor = preferences.edit();
//				// editor.putString(KEY, value);
//				// editor.commit();
//
//			}
//		});
//
//	}

	private void refreshDispalay() {
	
		noteList=dataSource.getAll();
		ArrayAdapter<NoteItem> adapter=new ArrayAdapter<NoteItem>(this, android.R.layout.simple_list_item_1,noteList);
		setListAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {

		boolean handled = false;

		int id = item.getItemId();
		switch (id) {
		case R.id.changeimage:
			onMenuChangePictureClick(item);
			handled = true;
			break;
		case R.id.lock:
			onMenuLockClick(item);
			handled = true;
			break;
		case R.id.exit:
			onMenuExitClick(item);
			handled = true;
			break;

		default:
			handled = super.onMenuItemSelected(featureId, item);
			break;

		}

		return handled;
	}

	public void onMenuChangePictureClick(MenuItem item) {
		Toast toast = Toast.makeText(this, "Picture change", Toast.LENGTH_LONG);
		toast.show();
	}

	public void onMenuLockClick(MenuItem item) {

		Intent intent = new Intent(this, ImageActivity.class);
		startActivity(intent);

	}

	public void onMenuExitClick(MenuItem item) {

		finish();

	}
}
