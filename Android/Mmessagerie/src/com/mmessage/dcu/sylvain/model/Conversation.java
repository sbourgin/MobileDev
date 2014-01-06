package com.mmessage.dcu.sylvain.model;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.mmessage.dcu.sylvain.interfaces.Displayable;

public class Conversation implements Displayable {

	private long _id = -1;
	private String _name = null;
	private List<Contact> _addresseeList = new ArrayList<Contact>();

	public Conversation() {
		
	}
	
	
	
	public Conversation(String parName, List<Contact> parAddresseeList) {
		this._name = parName;
		this._addresseeList = parAddresseeList;
	}



	public boolean fillStates(JSONObject parJSon) {
		try {
			
			this._id =  (Long) parJSon.get("id");
			this._name = (String) parJSon.get("name");

			JSONArray locContactsJSONArray = (JSONArray) parJSon.get("users");
			
			for (int i = 0; i < locContactsJSONArray.size(); i++) {
				JSONObject locContactJSON = (JSONObject) locContactsJSONArray
						.get(i);
				Contact locContact = new Contact();
				boolean isContactValid = locContact.fillStates(locContactJSON);
				
				if(isContactValid) {
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
		
		for(int i=0; i<_addresseeList.size()-1;i++) {
			locListAddresse.append(_addresseeList.get(i).getTitleToDisplay());
			locListAddresse.append(", ");
		}
		locListAddresse.append(_addresseeList.get(_addresseeList.size()-1).getTitleToDisplay());
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

	
	
}
