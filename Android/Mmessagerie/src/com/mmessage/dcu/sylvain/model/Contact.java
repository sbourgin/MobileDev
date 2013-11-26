package com.mmessage.dcu.sylvain.model;

import com.mmessage.dcu.sylvain.interfaces.Displayable;

public class Contact implements Displayable{

	Long _id;
	String _name;
	
	
	
	@Override
	public String getTitleToDisplay() {
		return _name;
	}

	@Override
	public String getFullTextToDisplay() {
		
		return "";
	}

	@Override
	public long getIdOfItem() {
		return _id;
	}



}
