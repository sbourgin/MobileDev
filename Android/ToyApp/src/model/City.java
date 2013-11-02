package model;

import interfaces.Displayable;

import org.json.simple.JSONObject;


/*
 * The aim of this class is to describe a city and all the informations it's has
 */
public class City implements Displayable {
	
	private Long _id=null;
	private String _country=null;
	private String _region=null;
	private String _cityName=null;
	private Double _latitude=null;
	private Double _longitude=null;
	private String _comment=null;
	
	public City (){
		
	}
	
	/**
	 * Fill states of the City class
	 * @param parJSon
	 * @return if the construction doen't work return false
	 */
	
	public boolean fillStates (JSONObject parJSon) {
		
		try {
		this._id =  (Long) parJSon.get("id");
		this._country = (String) parJSon.get("country");
		this._region = (String) parJSon.get("region");
		this._cityName = (String) parJSon.get("city");
		this._latitude = (Double) parJSon.get("latitude");
		this._longitude = (Double) parJSon.get("longitude");
		this._comment = (String) parJSon.get("comment");
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	//TODO DELETE
	public void setCityName(String cityName) {
		_cityName = cityName;
	}

	public Long get_id() {
		return _id;
	}
	public String get_country() {
		return _country;
	}
	public String get_region() {
		return _region;
	}
	public String get_cityName() {
		return _cityName;
	}
	public Double get_latitude() {
		return _latitude;
	}
	public Double get_longitude() {
		return _longitude;
	}
	public String get_comment() {
		return _comment;
	}

	@Override
	public String getNameToDisplay() {
		
		return get_cityName();
	}
	
	
	
	
}
