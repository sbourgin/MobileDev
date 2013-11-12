package tasks;

import interfaces.OnTaskCompleted;

import java.util.ArrayList;
import java.util.List;

import model.Country;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import android.content.Context;

public class CountriesListManager implements OnTaskCompleted {

	private OnTaskCompleted _listener = null;
	private final String _httpLink = "http://honey.computing.dcu.ie/city/countries.php";

	public CountriesListManager(Context parContext) {
		_listener = (OnTaskCompleted) parContext;
	}

	public void initData() {
		updateCountriesList();
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
	public void updateCountriesList() {

		new GetRESTTask(this).execute(_httpLink);
		// We get a call back with the OnTaskCompleted Method

	}

	protected synchronized void updateCountriesList(String parString) {

		List<Object> locResult = new ArrayList<Object>();

		boolean isCountriesListSucess = true;
		JSONParser locParser = new JSONParser();
		JSONArray locCountriesJSONArray = null;
		List<Country> locCountriesList = new ArrayList<Country>();

		try {
			JSONObject locAnswerJSON = (JSONObject) locParser.parse(parString);
			locCountriesJSONArray = (JSONArray) locAnswerJSON.get("result");

		} catch (ParseException e) {
			e.printStackTrace();
			isCountriesListSucess = false;
		}

		if (isCountriesListSucess) {

			for (int i = 0; i < locCountriesJSONArray.size(); i++) {

				JSONObject locCountryJSON = (JSONObject) locCountriesJSONArray
						.get(i);

				Country locCountry = new Country();
				boolean isCountryValid = locCountry.fillStates(locCountryJSON);
				if (isCountryValid) {
					locCountriesList.add(locCountry);

				}

			}

			locResult.add(locCountriesList);

		} else {
			locResult.add(null);
		}

		_listener.onTaskCompleted(locResult);

	}

	@Override
	public void onTaskCompleted(Object parObject) {
		updateCountriesList((String) parObject);
	}

}
