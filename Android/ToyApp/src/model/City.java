package model;


/*
 * The aim of this class is to describe a city and all the informations it's has
 */
public class City {
	
	private Integer _id;
	private String _country;
	private Integer _region;
	private String _cityName;
	private Double _latitude;
	private Double _longitude;
	private String _comment;
	
	
	public City(Integer parId, String parCountry, Integer parRegion,
			String parCityName, Double parLatitude, Double parLongitude,
			String parComment) {
		super();
		this._id = parId;
		this._country = parCountry;
		this._region = parRegion;
		this._cityName = parCityName;
		this._latitude = parLatitude;
		this._longitude = parLongitude;
		this._comment = parComment;
	}
	public Integer get_id() {
		return _id;
	}
	public String get_country() {
		return _country;
	}
	public Integer get_region() {
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
	
	
	
	
}
