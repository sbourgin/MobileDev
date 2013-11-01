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

	private long _lastBottomIdFetch = 0;
	private long _lastTopIdFetch = 0;
	private OnTaskCompleted _listener = null;
	private final String _httpLink = "http://honey.computing.dcu.ie/city/cities.php?";
	private String _countryCode = "us"; // TODO premettre de choisir !
	private Boolean _isUpdating = Boolean.valueOf(false);
	private boolean isScrollingDown = false;

	public CitiesListManager(Context parContext) {
		_listener = (OnTaskCompleted) parContext;
	}

	/**
	 * Call this method to update Cities List.
	 * 
	 * @param isScrollingDown
	 *            : is the user ask to scroll down. If it's false it mean the
	 *            user want to go up
	 */
	public void updateCitiesList(boolean parIsScrollingDown) {

		synchronized (_isUpdating) {
			if (false == _isUpdating) {

				_isUpdating = Boolean.valueOf(true);
				StringBuilder locUrl = new StringBuilder();
				locUrl.append(_httpLink).append("id=");

				if (parIsScrollingDown) {
					locUrl.append(_lastBottomIdFetch);
				} else {
					locUrl.append(_lastTopIdFetch);
				}
				locUrl.append("&cn=").append(_countryCode);

				if (false == parIsScrollingDown) {
					locUrl.append("reverse=1");
				}

				isScrollingDown = parIsScrollingDown;
				new GetRESTTask(this).execute(locUrl.toString());

			}
		}

		// We get a call back with the OnTaskCompleted Method

	}

	protected synchronized void updateCitiesList(String parString) {

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

		if (isCitiesListSucess) {

			for (int i = 0; i < locCitiesJSONArray.size(); i++) {

				JSONObject locCityJSON = (JSONObject) locCitiesJSONArray.get(i);

				City locCity = new City();
				boolean isCityValid = locCity.fillStates(locCityJSON);
				if (isCityValid) {
					locCitiesList.add(locCity);
				}

			}

			if (isLastRequestScrollingDown) {
				if (false == locCitiesList.isEmpty()) {
					_lastBottomIdFetch = locCitiesList.get(
							locCitiesList.size() - 1).get_id();
				}
			} else {
				if (false == locCitiesList.isEmpty()) {
					_lastTopIdFetch = locCitiesList.get(0).get_id();
				}
			}

			_listener.onTaskCompleted(locCitiesList);

		} else {
			_listener.onTaskCompleted(null);
		}

		// TODO gérer la fin quand il n'y a plus de data à fetcher (max id
		// atteint)
	}

	@Override
	public void onTaskCompleted(Object parObject) {
		updateCitiesList((String) parObject);
	}

}
