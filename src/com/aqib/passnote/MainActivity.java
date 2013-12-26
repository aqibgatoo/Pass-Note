package com.aqib.passnote;

import java.util.List;

import com.actionbarsherlock.app.SherlockListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.aqib.passnote.data.NoteItem;
import com.aqib.passnote.data.NoteItemDataSource;

public class MainActivity extends SherlockListActivity {
	private static final String NOTE_VALUE = "value";
	private static final String NOTE_KEY = "key";
	public static final int NOTE_EDITOR_CONSTANT = 1001;
	public static final String LOGTAG = "MAG";
	public static final int MENU_DELETE_ID=1002;
	private int currentNode;
	
	// private static final String KEY = "hello";

	// private EditText text;

	private NoteItemDataSource dataSource;
	private List<NoteItem> noteList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// setButtonListener();
		registerForContextMenu(getListView());
		dataSource = new NoteItemDataSource(this);
		refreshDisplay();

		// text = (EditText) findViewById(R.id.edit_text);
		// SharedPreferences preferences=getPreferences(MODE_PRIVATE);
		// String string=preferences.getString(KEY, null);
		// text.setText(string);
		//
		// if (preferences!=null) {
		// addSharedPreferences();
		// }

	}

	// private void setButtonListener() {
	//
	// Button button = (Button) findViewById(R.id.save_button);
	// button.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	//
	// // text = (EditText) findViewById(R.id.edit_text);
	// // String value=text.getText().toString();
	// // SharedPreferences preferences=getPreferences(MODE_PRIVATE);
	// // SharedPreferences.Editor editor=preferences.edit();
	// // editor.putString(KEY,value);
	// // editor.commit();
	//
	// // String value = getString(R.id.edit_text);
	// //
	// // preferences = getPreferences(MODE_PRIVATE);
	// // SharedPreferences.Editor editor = preferences.edit();
	// // editor.putString(KEY, value);
	// // editor.commit();
	//
	// }
	// });
	//
	// }

	private void refreshDisplay() {

		noteList = dataSource.getAll();
		ArrayAdapter<NoteItem> adapter = new ArrayAdapter<NoteItem>(this,
				R.layout.list_item_layout, noteList);
		setListAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSherlock().getMenuInflater().inflate(R.menu.main, menu);
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

	private void createNote() {

		NoteItem item = NoteItem.getNew();

		Intent intent = new Intent(this, NoteEditorActivity.class);
		intent.putExtra(NOTE_KEY, item.getKey());
		intent.putExtra(NOTE_VALUE, item.getValue());
		startActivityForResult(intent, NOTE_EDITOR_CONSTANT);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.create_note) {
			createNote();
		}

		return super.onOptionsItemSelected(item);
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

		System.exit(1);

	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
	
		NoteItem item=noteList.get(position);
		Intent intent = new Intent(this, NoteEditorActivity.class);
		intent.putExtra(NOTE_KEY, item.getKey());
		intent.putExtra(NOTE_VALUE, item.getValue());
		startActivityForResult(intent, NOTE_EDITOR_CONSTANT);

	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode==NOTE_EDITOR_CONSTANT&& resultCode==RESULT_OK) {
			NoteItem item=new NoteItem();
			item.setKey(data.getStringExtra(NOTE_KEY));
			item.setValue(data.getStringExtra(NOTE_VALUE));
			dataSource.update(item);
			refreshDisplay();
		}
		
	}
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		AdapterContextMenuInfo info=(AdapterContextMenuInfo) menuInfo;
		currentNode=(int) info.id;
	menu.add(0,MENU_DELETE_ID,0,"Delete");
	
	}
	@Override
	public boolean onContextItemSelected(android.view.MenuItem item) {

		if (item.getItemId()==MENU_DELETE_ID) {
			NoteItem note=noteList.get(currentNode);
			dataSource.remove(note);
			refreshDisplay();
		}
		
		return super.onContextItemSelected(item);
	}
}
