package com.mmessage.dcu.sylvain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import android.view.Gravity;

import com.mmessage.dcu.sylvain.interfaces.Displayable;

public class Conversation implements Displayable, Cloneable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8484442511014429151L;
	private long _id = -1;
	private String _name = null;
	private List<Contact> _addresseeList = new ArrayList<Contact>();
	private int _gravity = Gravity.CENTER;

	public Conversation() {

	}

	public Conversation(String parName, List<Contact> parAddresseeList) {
		this._name = parName;
		this._addresseeList = parAddresseeList;
	}

	public boolean fillStates(JSONObject parJSon) {
		try {

			this._id = (Long) parJSon.get("id");
			this._name = (String) parJSon.get("name");

			JSONArray locContactsJSONArray = (JSONArray) parJSon.get("users");

			for (int i = 0; i < locContactsJSONArray.size(); i++) {
				JSONObject locContactJSON = (JSONObject) locContactsJSONArray
						.get(i);
				Contact locContact = new Contact();
				boolean isContactValid = locContact.fillStates(locContactJSON);

				if (isContactValid) {
					_addresseeList.add(locContact);
				}

			}

		} catch (Exception e) {
			return false;
		}
		return true;

	}

	@Override
	public String getTitleToDisplay() {

		StringBuilder locListAddresse = new StringBuilder();

		for (int i = 0; i < _addresseeList.size() - 1; i++) {
			locListAddresse.append(_addresseeList.get(i).getTitleToDisplay());
			locListAddresse.append(", ");
		}
		locListAddresse.append(_addresseeList.get(_addresseeList.size() - 1)
				.getTitleToDisplay());
		return locListAddresse.toString();

	}

	@Override
	public String getFullTextToDisplay() {
		return _name;
	}

	@Override
	public long getIdOfItem() {
		return _id;
	}

	public List<Contact> getAddresseeList() {
		return _addresseeList;
	}

	@Override
	public int getGravity() {
		return _gravity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((_addresseeList == null) ? 0 : _addresseeList.hashCode());
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
		Conversation other = (Conversation) obj;
		if (_addresseeList == null) {
			if (other._addresseeList != null)
				return false;
		} else if (!_addresseeList.equals(other._addresseeList))
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
