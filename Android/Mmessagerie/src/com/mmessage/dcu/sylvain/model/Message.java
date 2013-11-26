package com.mmessage.dcu.sylvain.model;

import java.sql.Timestamp;

import com.mmessage.dcu.sylvain.interfaces.Displayable;

public class Message implements Displayable{

	Long _id;
	Integer _status;
	Timestamp _time;
	Contact _fromContact;
	Contact _toContact;
	String _subject;
	String _text;
	
	@Override
	public String getTitleToDisplay() {
		return _subject;
	}
	@Override
	public String getFullTextToDisplay() {
		return _text;
	}
	@Override
	public long getIdOfItem() {
		return _id;
	}	
	


}
