package com.mmessage.dcu.sylvain.model;

import java.io.Serializable;

import org.json.simple.JSONObject;

import com.mmessage.dcu.sylvain.interfaces.Displayable;

public class Message implements Displayable, Cloneable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9123806607158236343L;
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
		String date = _updatedAt.substring(5, 7) + "/"
				+ _updatedAt.substring(8, 10) + " "
				+ _updatedAt.substring(11, 19);
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
			isSenderValid = _sender
					.fillStates((JSONObject) parJSon.get("user"));

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + _gravity;
		result = prime * result + ((_sender == null) ? 0 : _sender.hashCode());
		result = prime * result + ((_text == null) ? 0 : _text.hashCode());
		result = prime * result
				+ ((_updatedAt == null) ? 0 : _updatedAt.hashCode());
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
		Message other = (Message) obj;
		if (_gravity != other._gravity)
			return false;
		if (_sender == null) {
			if (other._sender != null)
				return false;
		} else if (!_sender.equals(other._sender))
			return false;
		if (_text == null) {
			if (other._text != null)
				return false;
		} else if (!_text.equals(other._text))
			return false;
		if (_updatedAt == null) {
			if (other._updatedAt != null)
				return false;
		} else if (!_updatedAt.equals(other._updatedAt))
			return false;
		return true;
	}
	
	
}
