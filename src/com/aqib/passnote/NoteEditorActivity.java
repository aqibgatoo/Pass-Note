package com.aqib.passnote;

import com.aqib.passnote.data.NoteItem;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

@SuppressLint("NewApi")
public class NoteEditorActivity extends Activity {

	private NoteItem item;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.android_editor_layout);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		Intent intent = this.getIntent();
		item = new NoteItem();
		item.setKey(intent.getStringExtra("key"));
		item.setValue(intent.getStringExtra("value"));

		EditText editText = (EditText) findViewById(R.id.textEdit);
		editText.setText(item.getValue());
		editText.setSelection(item.getValue().length());

	}

	public void saveAndFinish() {

		EditText editText = (EditText) findViewById(R.id.textEdit);
		String textValue = editText.getText().toString();

		Intent intent = new Intent();
		intent.putExtra("key", item.getKey());
		intent.putExtra("value", textValue);
		setResult(RESULT_OK, intent);
		finish();

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			saveAndFinish();
		}
		return false;
	}

	@Override
	public void onBackPressed() {
		saveAndFinish();
	}

}
