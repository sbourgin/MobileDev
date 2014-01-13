package com.mmessage.dcu.sylvain.model;

import java.io.Serializable;

import org.json.simple.JSONObject;

import android.view.Gravity;

import com.mmessage.dcu.sylvain.interfaces.Displayable;

public class Contact implements Displayable, Cloneable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -531790637559829520L;
	private Long _id;
	private String _name;
	private String _email;
	private int _gravity = Gravity.CENTER;

	public Contact() {
	}

	public boolean fillStates(JSONObject parJSon) {

		try {
			this._id = (Long) parJSon.get("id");
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

	@Override
	public String toString() {
		return _name;
	}

	@Override
	public int getGravity() {
		return _gravity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_email == null) ? 0 : _email.hashCode());
		result = prime * result + _gravity;
		result = prime * result + ((_name == null) ? 0 : _name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contact other = (Contact) obj;
		if (_email == null) {
			if (other._email != null)
				return false;
		} else if (!_email.equals(other._email))
			return false;
		if (_gravity != other._gravity)
			return false;
		if (_name == null) {
			if (other._name != null)
				return false;
		} else if (!_name.equals(other._name))
			return false;
		return true;
	}

	
	
}
