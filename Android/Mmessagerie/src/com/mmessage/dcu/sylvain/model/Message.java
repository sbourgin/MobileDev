package com.mmessage.dcu.sylvain.model;

import org.json.simple.JSONObject;

import com.mmessage.dcu.sylvain.interfaces.Displayable;

public class Message implements Displayable {

	private Long _id;
	private Integer _status;
	private String _updatedAt;
	private String _subject;
	private String _text;
	private Contact _sender;
	
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

	public boolean fillStates(JSONObject parJSon) {
	
		boolean isSenderValid = true;
		
		try {
			_id = (Long) parJSon.get("id");
			_text = (String) parJSon.get("content");
			_updatedAt = (String) parJSon.get("updated_at");
			_sender = new Contact();
			isSenderValid =  _sender.fillStates((JSONObject) parJSon.get("user"));

		} catch (Exception e) {
			return false;
		}
		return isSenderValid;
	}

}
