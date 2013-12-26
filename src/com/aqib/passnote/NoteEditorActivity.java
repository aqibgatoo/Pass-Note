package com.aqib.passnote;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.aqib.passnote.data.NoteItem;

public class NoteEditorActivity extends SherlockActivity {

	private NoteItem item;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.android_editor_layout);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
