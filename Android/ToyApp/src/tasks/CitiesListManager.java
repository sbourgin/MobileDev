package tasks;

import interfaces.OnTaskCompleted;

import java.util.ArrayList;
import java.util.List;

import model.City;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import android.content.Context;

public class CitiesListManager implements OnTaskCompleted {

	private OnTaskCompleted _listener = null;
	private final String _httpLink = "http://honey.computing.dcu.ie/city/cities.php?";
	private String _countryCode = null;
	private Boolean _isUpdating = Boolean.valueOf(false);
	private boolean isScrollingDown = false;

	public CitiesListManager(Context parContext, String parCountryCode) {
		_listener = (OnTaskCompleted) parContext;
		_countryCode = parCountryCode;
	}

	public void initData() {
		updateCitiesList(true, 0);
	}

	/**
	 * Call this method to update Cities List.
	 * 
	 * @param isScrollingDown
	 *            : is the user ask to scroll down. If it's false it mean the
	 *            user want to go up
	 * @param parItemId
	 *            : item id to fetch
	 */
	public void updateCitiesList(boolean parIsScrollingDown, long parItemId) {

		synchronized (_isUpdating) {
			if (false == _isUpdating) {

				_isUpdating = Boolean.valueOf(true);
				StringBuilder locUrl = new StringBuilder();
				locUrl.append(_httpLink).append("id=").append(parItemId);
				locUrl.append("&cn=").append(_countryCode);

				if (false == parIsScrollingDown) {
					locUrl.append("&reverse=1");
				}

				isScrollingDown = parIsScrollingDown;
				new GetRESTTask(this).execute(locUrl.toString());

			}
		}

		// We get a call back with the OnTaskCompleted Method

	}

	protected synchronized void updateCitiesList(String parString) {

		List<Object> locResult = new ArrayList<Object>();

		boolean isLastRequestScrollingDown;

		synchronized (_isUpdating) {
			_isUpdating = Boolean.valueOf(false);
			isLastRequestScrollingDown = isScrollingDown;

		}

		boolean isCitiesListSucess = true;
		JSONParser locParser = new JSONParser();
		JSONArray locCitiesJSONArray = null;
		List<City> locCitiesList = new ArrayList<City>();

		try {
			JSONObject locAnswerJSON = (JSONObject) locParser.parse(parString);
			locCitiesJSONArray = (JSONArray) locAnswerJSON.get("result");

		} catch (ParseException e) {
			e.printStackTrace();
			isCitiesListSucess = false;
		}

		locResult.add(Boolean.valueOf(isLastRequestScrollingDown));

		if (isCitiesListSucess) {

			for (int i = 0; i < locCitiesJSONArray.size(); i++) {

				JSONObject locCityJSON = (JSONObject) locCitiesJSONArray.get(i);

				City locCity = new City();
				boolean isCityValid = locCity.fillStates(locCityJSON);
				if (isCityValid) {
					if (isLastRequestScrollingDown) {
						locCitiesList.add(locCity);
					} else {
						locCitiesList.add(0, locCity);
					}

				}

			}

			locResult.add(locCitiesList);

		} else {
			locResult.add(null);
		}

		_listener.onTaskCompleted(locResult);

	}

	@Override
	public void onTaskCompleted(Object parObject) {
		updateCitiesList((String) parObject);
	}

}
