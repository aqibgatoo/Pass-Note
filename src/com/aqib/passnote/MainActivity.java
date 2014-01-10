package com.aqib.passnote;

import java.io.File;
import java.util.List;

import com.actionbarsherlock.app.SherlockListActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.aqib.passnote.data.NoteItem;
import com.aqib.passnote.data.NoteItemDataSource;

public class MainActivity extends SherlockListActivity {
	private static final String NOTE_VALUE = "value";
	private static final String NOTE_KEY = "key";
	public static final int NOTE_EDITOR_CONSTANT = 1001;
	public static final String LOGTAG = "MAG";
	public static final int MENU_DELETE_ID = 1002;
	private int currentNode;
	public static final String DEBUGTAG = "JWP";
	private static final int PHOTO_TAKEN_REQUEST = 0;
	private static final int BROWSE_GALLERY_REQUEST = 1;
	private Uri image;
	private File imageFile;

	// private static final String KEY = "hello";

	// private EditText text;

	private NoteItemDataSource dataSource;
	private List<NoteItem> noteList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_main);
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
	private void replaceImage() {

		// Offer a choice of methods to replace the image in a dialog;
		// the user can either take a photo or browse the gallery.
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		View v = getLayoutInflater().inflate(R.layout.replace_image, null);
		builder.setTitle(R.string.replace_lock_image);
		builder.setView(v);

		final AlertDialog dlg = builder.create();
		dlg.show();

		Button takePhoto = (Button) dlg.findViewById(R.id.take_photo);
		Button browseGallery = (Button) dlg.findViewById(R.id.browse_gallery);

		takePhoto.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// Invoke the camera activity.
				takePhoto();
			}
		});

		browseGallery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// Browse the gallery.
				browseGallery();
			}
		});
	}

	/*
	 * This method invokes the browse gallery activity
	 */
	private void browseGallery() {
		Intent i = new Intent(Intent.ACTION_PICK,
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(i, BROWSE_GALLERY_REQUEST);
	}

	/*
	 * This method invokes the camera activity, to take a photo
	 */
	private void takePhoto() {
		// Figure out where to put the photo when it's taken.
		File picturesDirectory = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		imageFile = new File(picturesDirectory, "passpoints_image");

		Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		// Tell the activity to save the photo to the given file.
		// (it will also be added to the gallery)
		image = Uri.fromFile(imageFile);
		i.putExtra(MediaStore.EXTRA_OUTPUT, image);
		startActivityForResult(i, PHOTO_TAKEN_REQUEST);
	}

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
			replaceImage();
			handled = true;
			break;
		case R.id.lock:
			onMenuLockClick(item);
			handled = true;
			break;
		case R.id.exit:
			resetPasspoints(image);
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

	public void onMenuLockClick(MenuItem item) {

		Intent intent = new Intent(this, ImageActivity.class);
		startActivity(intent);

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		NoteItem item = noteList.get(position);
		Intent intent = new Intent(this, NoteEditorActivity.class);
		intent.putExtra(NOTE_KEY, item.getKey());
		intent.putExtra(NOTE_VALUE, item.getValue());
		startActivityForResult(intent, NOTE_EDITOR_CONSTANT);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == NOTE_EDITOR_CONSTANT && resultCode == RESULT_OK) {
			NoteItem item = new NoteItem();
			item.setKey(data.getStringExtra(NOTE_KEY));
			item.setValue(data.getStringExtra(NOTE_VALUE));
			dataSource.update(item);
			refreshDisplay();
		}
		if (requestCode == BROWSE_GALLERY_REQUEST) {
			String[] columns = { MediaStore.Images.Media.DATA };

			Uri imageUri = data.getData();

			Cursor cursor = getContentResolver().query(imageUri, columns, null,
					null, null);

			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(columns[0]);
			String imagePath = cursor.getString(columnIndex);

			cursor.close();

			image = Uri.parse(imagePath);

			// Display an error message and return if we don't have an
			// image URI
			if (image == null) {
				Toast.makeText(this, R.string.unable_to_display_image,
						Toast.LENGTH_LONG).show();
				return;
			}

			Log.d(DEBUGTAG, "Photo: " + image.getPath());

			// Now we can start the image activity and tell it to use
			// this new image.
			resetPasspoints(image);
		}
		if (requestCode == PHOTO_TAKEN_REQUEST) {
//			Bitmap photo = BitmapFactory
//					.decodeFile(imageFile.getAbsolutePath());
//
//			if (photo != null) {
//				ImageView imageView = (ImageView) findViewById(R.id.touch_image);
//				imageView.setImageBitmap(photo);
//
//			} else {
//				Toast.makeText(this, R.string.unable_to_load_photo_file,
//						Toast.LENGTH_LONG).show();
//			}
			resetPasspoints(image);
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
		currentNode = (int) info.id;
		menu.add(0, MENU_DELETE_ID, 0, "Delete");

	}

	@Override
	public boolean onContextItemSelected(android.view.MenuItem item) {

		if (item.getItemId() == MENU_DELETE_ID) {
			NoteItem note = noteList.get(currentNode);
			dataSource.remove(note);
			refreshDisplay();
		}

		return super.onContextItemSelected(item);
	}

	private void resetPasspoints(Uri image) {
		Intent i = new Intent(this, ImageActivity.class);
		i.putExtra(ImageActivity.RESET_PASSPOINTS, true);

		if (image != null) {
			i.putExtra(ImageActivity.RESET_IMAGE, image.getPath());
		}

		startActivity(i);
	}
}
