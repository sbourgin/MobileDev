package com.mmessage.dcu.sylvain.model;

import java.sql.Timestamp;

import com.mmessage.dcu.sylvain.interfaces.Displayable;

public class Message implements Displayable{

	private Long _id;
	private Integer _status;
	private Timestamp _creationtime;
	private String _subject;
	private String _text;
	
	
	
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
