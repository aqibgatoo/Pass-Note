package com.aqib.passnote;

import com.aqib.passnote.data.NoteItem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class NoteEditorActivity extends Activity {

	
	private NoteItem item;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.id.textEdit);

		Intent intent=this.getIntent();
		item=new NoteItem();
		item.setKey(intent.getStringExtra("key"));
		item.setValue(intent.getStringExtra("value"));
		
		EditText editText=(EditText) findViewById(R.id.textEdit);
		editText.setText(item.getValue());
		editText.setSelection(item.getValue().length());
		
		
	}

}
