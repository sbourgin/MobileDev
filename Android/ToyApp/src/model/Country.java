package model;

import interfaces.Displayable;

public class Country implements Displayable {

	private static long lastID = 0;
	private String _name = null;
	private long _id = -1;

	public Country(String parName) {
		_id = lastID++;
		_name = parName;
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
