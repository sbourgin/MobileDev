package com.mmessage.dcu.sylvain.controler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.mmessage.dcu.sylvain.interfaces.OnTaskCompleted;
import com.mmessage.dcu.sylvain.model.Commands;
import com.mmessage.dcu.sylvain.model.TaskMessage;
import com.mmessage.dcu.sylvain.tasks.PostRESTTask;


public class CreateAccountController implements OnTaskCompleted{

	private String _urlPostUser = "http://message.eventhub.eu/users";
	private OnTaskCompleted _listener;
	
	public CreateAccountController(OnTaskCompleted parListener) {
		_listener = parListener;
	
	}
	
	public void createNewUser(String parUserName, String parPassword, String parEmailAddress) {
		
        List<NameValuePair> locNameValuePairs = new ArrayList<NameValuePair>(3);
	    locNameValuePairs.add(new BasicNameValuePair("username", "Sylvain49"));
	    locNameValuePairs.add(new BasicNameValuePair("password", "Sylvain49"));
	    locNameValuePairs.add(new BasicNameValuePair("email", "sylvain49@sylvain.com"));
/*
	    //TODO à remettre et tester bien sûr
	    locNameValuePairs.add(new BasicNameValuePair("username", parUserName));
	    locNameValuePairs.add(new BasicNameValuePair("password", parPassword));
	    locNameValuePairs.add(new BasicNameValuePair("email", parEmailAddress));
*/	
		new PostRESTTask(this, false, Commands.CREATE_A_USER, locNameValuePairs).execute(_urlPostUser);
	}

	@Override
	public void onTaskCompleted(Object parObject) {
		
		TaskMessage locTaskMessage = (TaskMessage) parObject;
		
		Boolean isCreatingAccountASuccess;
				
		if(locTaskMessage.getHttpCode() == HttpStatus.SC_OK) {
			isCreatingAccountASuccess = Boolean.valueOf(true);
		} else {
			isCreatingAccountASuccess = Boolean.valueOf(false);
		}
		
		_listener.onTaskCompleted(isCreatingAccountASuccess);
		
	}
	
	
	
}
