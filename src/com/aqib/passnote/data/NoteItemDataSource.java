package com.aqib.passnote.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import android.content.Context;
import android.content.SharedPreferences;

public class NoteItemDataSource {

	private static final String PREFKEY = "notes";
	private SharedPreferences notePref;

	public NoteItemDataSource(Context context) {

		notePref = context.getSharedPreferences(PREFKEY, Context.MODE_PRIVATE);

	}

	public List<NoteItem> getAll() {

		Map<String, ?> noteMap = notePref.getAll();

		SortedSet<String> keys = new TreeSet<String>(noteMap.keySet());

		List<NoteItem> list = new ArrayList<NoteItem>();
		for (String key : keys) {
			NoteItem item = new NoteItem();
			item.setKey(key);
			item.setValue((String) noteMap.get(key));
			list.add(item);
		}

		return list;

	}

	public boolean update(NoteItem item) {
		SharedPreferences.Editor editor = notePref.edit();
		editor.putString(item.getKey(), item.getValue());
		editor.commit();

		return true;
	}

	public boolean remove(NoteItem item) {
		if (notePref.contains(item.getKey())) {
			SharedPreferences.Editor editor = notePref.edit();
			editor.remove(item.getKey());
			editor.commit();
		}

		return true;
	}

}
