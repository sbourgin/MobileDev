package tasks;

import interfaces.OnTaskCompleted;

import java.util.ArrayList;
import java.util.List;

import model.Country;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CountriesListManager implements OnTaskCompleted {

	private OnTaskCompleted _listener = null;
	private final String _httpLink = "http://honey.computing.dcu.ie/city/countries.php";

	public CountriesListManager(OnTaskCompleted parContext) {
		_listener = parContext;
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

		List<Country> locResult = new ArrayList<Country>();

		boolean isCountriesListSucess = true;
		JSONParser locParser = new JSONParser();
		JSONArray locCountriesJSONArray = null;

		try {
			JSONObject locAnswerJSON = (JSONObject) locParser.parse(parString);
			locCountriesJSONArray = (JSONArray) locAnswerJSON.get("result");

		} catch (ParseException e) {
			e.printStackTrace();
			isCountriesListSucess = false;
		}

		if (isCountriesListSucess) {

			for (int i = 0; i < locCountriesJSONArray.size(); i++) {

				Country locCountry = null;

				boolean isCountryValid = true;
				try {
					String locCountryName = (String) locCountriesJSONArray
							.get(i);

					locCountry = new Country(locCountryName);
				} catch (Exception e) {
					isCountryValid = false;
				}

				if (isCountryValid) {
					locResult.add(locCountry);

				}

			}

		}

		_listener.onTaskCompleted(locResult);

	}

	@Override
	public void onTaskCompleted(Object parObject) {
		updateCountriesList((String) parObject);
	}

}
