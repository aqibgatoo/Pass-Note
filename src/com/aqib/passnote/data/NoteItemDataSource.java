package com.aqib.passnote.data;

import java.util.ArrayList;
import java.util.List;

public class NoteItemDataSource {

	public List<NoteItem> getAll() {

		List<NoteItem> list = new ArrayList<NoteItem>();
		NoteItem item = NoteItem.getNew();
		list.add(item);
		return list;

	}

	public boolean update(NoteItem item) {
		return true;
	}

	public boolean remove(NoteItem item) {
		return true;
	}

}
