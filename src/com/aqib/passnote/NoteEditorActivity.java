package com.aqib.passnote;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.aqib.passnote.data.NoteItem;

public class NoteEditorActivity extends SherlockActivity {

	private NoteItem item;
	private String key;
	private String value;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.android_editor_layout);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		Intent intent = this.getIntent();
		item = new NoteItem();
		item.setKey(intent.getStringExtra("key"));
		item.setValue(intent.getStringExtra("value"));
//		Log.i("mytag", item.getKey()+" "+item.getValue());
		EditText editText = (EditText) findViewById(R.id.textEdit);
		editText.setText(item.getValue());
		editText.setSelection(item.getValue().length());

	}

	public void saveAndFinish() {

		EditText editText = (EditText) findViewById(R.id.textEdit);
		String textValue = editText.getText().toString();

		Intent intent = new Intent();
		key =item.getKey();
		value= textValue;
		intent.putExtra("key", key);
		intent.putExtra("value",value);
		setResult(RESULT_OK, intent);
		finish();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSherlock().getMenuInflater().inflate(R.menu.note_editor, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem itemm) {
		int id = itemm.getItemId();
		switch (id) {
		case android.R.id.home:
			saveAndFinish();
			break;
		case R.id.upoload_to_cloud:
			Intent intent2 = new Intent(this, NoteSynchronize.class);
			intent2.putExtra("key",key);
			intent2.putExtra("value", value);
//			Log.i("mytag", item.getKey()+" "+item.getValue());
			startActivity(intent2);
		default:
			break;
		}

		return false;
	}

	@Override
	public void onBackPressed() {
		saveAndFinish();

	}

}
