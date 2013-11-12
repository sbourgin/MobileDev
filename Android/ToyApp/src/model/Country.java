package model;

import org.json.simple.JSONObject;

import interfaces.Displayable;

public class Country implements Displayable {

	private static long lastID = 0;
	private String _name = null;
	private long _id = -1;

	public Country() {
	}

	public boolean fillStates(JSONObject parJSon) {

		_id = lastID++;

		try {

			_name = (String) parJSon.toString();

		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public String getNameToDisplay() {
		return _name;
	}

	@Override
	public String getSubtitle() {
		return "";
	}

	@Override
	public long getIdOfItem() {
		return _id;
	}

}
