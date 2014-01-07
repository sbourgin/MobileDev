package com.mmessage.dcu.sylvain.model;

import org.json.simple.JSONObject;

import com.mmessage.dcu.sylvain.interfaces.Displayable;

public class Message implements Displayable {

	
	private Long _id;
	private String _updatedAt;
	private String _text;
	private Contact _sender;
	private int _gravity;
	
	
	@Override
	public String getTitleToDisplay() {
		return _text;
	}

	@Override
	public String getFullTextToDisplay() {
		String date = _updatedAt.substring(5, 7) + "/" + _updatedAt.substring(8, 10) + " " + _updatedAt.substring(11, 19);
		return _sender.getTitleToDisplay() + " at " + date;
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

	@Override
	public int getGravity() {
		return _gravity;
	}

	public void setGravity(int parGravity) {
		_gravity = parGravity;
	}
	
	public Contact getSender() {
		return _sender;
	}
}
