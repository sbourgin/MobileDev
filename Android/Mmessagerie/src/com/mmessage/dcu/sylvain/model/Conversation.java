package com.mmessage.dcu.sylvain.model;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.mmessage.dcu.sylvain.interfaces.Displayable;

public class Conversation implements Displayable {

	private long _id;
	private String _name;
	private List<String> _addresseeList = new ArrayList<String>();

	
	


	public Conversation() {
		
	}
	
	public boolean fillStates(JSONObject parJSon) {
		try {
			
			this._id =  (Long) parJSon.get("id");;
			this._name = (String) parJSon.get("name");

			JSONArray locContactsJSONArray = (JSONArray) parJSon.get("conversations");
			
			for (int i = 0; i < locContactsJSONArray.size(); i++) {
				
				
				
			} 
			
		} catch (Exception e) {
			return false;
		}
		return true;
		
		
	}
	

	@Override
	public String getTitleToDisplay() {
		
		StringBuilder locListAddresse = new StringBuilder();
		
		for(int i=0; i<_addresseeList.size()-1;i++) {
			locListAddresse.append(_addresseeList.get(i));
			locListAddresse.append(", ");
		}
		locListAddresse.append(_addresseeList.get(_addresseeList.size())); //TODO Check si ça marche pour la virgule
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

}
