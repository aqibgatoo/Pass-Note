package com.aqib.passnote;

import java.io.IOException;
import java.util.Arrays;

import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.aqib.passnote.data.NoteItem;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;

public class NoteEditorActivity extends SherlockActivity {

	private NoteItem item;
	private String key;
	private String value;
	private static Drive service;
	private GoogleAccountCredential credential;
	
	static final int REQUEST_ACCOUNT_PICKER = 1;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.android_editor_layout);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		Intent intent = this.getIntent();
		item = new NoteItem();
		item.setKey(intent.getStringExtra("key"));
		item.setValue(intent.getStringExtra("value"));
		// Log.i("mytag", item.getKey()+" "+item.getValue());
		EditText editText = (EditText) findViewById(R.id.textEdit);
		editText.setText(item.getValue());
		editText.setSelection(item.getValue().length());

	}

	public void saveAndFinish() {

		EditText editText = (EditText) findViewById(R.id.textEdit);
		String textValue  = editText.getText().toString();

		Intent intent = new Intent();
		key = item.getKey();
		value = textValue;
		intent.putExtra("key", key);
		intent.putExtra("value", value);
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
			// Intent intent2 = new Intent(this, NoteSynchronize.class);
			// intent2.putExtra("key",key);
			// intent2.putExtra("value", value);
			Log.i("mytag", item.getKey() + " " + item.getValue());
			// startActivity(intent2);
			startCloud();
			break;
		default:
			break;
		}

		return false;
	}

	private void startCloud() {
		credential = GoogleAccountCredential.usingOAuth2(this,
				Arrays.asList(DriveScopes.DRIVE_FILE));
		startActivityForResult(credential.newChooseAccountIntent(),
				REQUEST_ACCOUNT_PICKER);

	}

	protected void onActivityResult(final int requestCode,
			final int resultCode, final Intent data) {
		if (resultCode == RESULT_OK && data != null && data.getExtras() != null) {
			String accountName = data
					.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
			if (accountName != null) {
				credential.setSelectedAccountName(accountName);
				service = getDriveService(credential);
				// startCameraIntent();
				saveFileToDrive();
          
				setResult(RESULT_OK);
				
			}
		}
	}

	private Drive getDriveService(GoogleAccountCredential credential) {
		return new Drive.Builder(AndroidHttp.newCompatibleTransport(),
				new GsonFactory(), credential).build();
	}

	public void saveFileToDrive() {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					// File's binary content
					// java.io.File fileContent = new
					// java.io.File(fileUri.getPath());
					// FileContent mediaContent = new FileContent("image/jpeg",
					// fileContent);

					File newFile = new File();
					newFile.setTitle(item.getKey());

					String fileType = "text/plain";
					newFile.setMimeType(fileType);

					String textString=item.getValue();
					// File's metadata.
					// File body = new File();
					// body.setTitle(fileContent.getName());
					// body.setMimeType("image/jpeg");

					// File file = service.files().insert(body,
					// mediaContent).execute();
					File insertedFile = service
							.files()
							.insert(newFile,
									ByteArrayContent
											.fromString(fileType,textString))
							.execute();
					if (insertedFile != null) {

						showToast("Note uploaded: " + insertedFile.getTitle());

					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		t.start();
	}

	public void showToast(final String toast) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(getApplicationContext(), toast,
						Toast.LENGTH_SHORT).show();
				return;
			}
		});

	}

	@Override
	public void onBackPressed() {
		saveAndFinish();

	}

}
