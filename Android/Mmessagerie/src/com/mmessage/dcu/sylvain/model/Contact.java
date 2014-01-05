package com.mmessage.dcu.sylvain.model;

import org.json.simple.JSONObject;

import com.mmessage.dcu.sylvain.interfaces.Displayable;

public class Contact implements Displayable {

	private Long _id;
	private String _name;
	private String _email;

	public Contact() {
	}

	public boolean fillStates(JSONObject parJSon) {

		try {
			this._id = (Long) parJSon.get("id");;
			this._name = (String) parJSon.get("username");
			this._email = (String) parJSon.get("email");

		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public String getTitleToDisplay() {
		return _name;
	}

	@Override
	public String getFullTextToDisplay() {
		return _email;
	}

	@Override
	public long getIdOfItem() {
		return _id;
	}

}
