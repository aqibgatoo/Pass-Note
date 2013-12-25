package com.aqib.passnote.data;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NoteItem {

	private String key;
	private String value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@SuppressLint("SimpleDateFormat")
	public static NoteItem getNew() {

		Locale locale = new Locale("en_US");
		Locale.setDefault(locale);

		String pattern = "yyyy-MM-dd HH:mm:ss Z";
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		String key = format.format(new Date());

		NoteItem item = new NoteItem();
		item.setKey(key);
		item.setValue("");

		return item;

	}



}
