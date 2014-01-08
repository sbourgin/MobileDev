package com.mmessage.dcu.sylvain.controler;

import org.apache.http.HttpStatus;

import android.util.Base64;

import com.mmessage.dcu.sylvain.interfaces.OnTaskCompleted;
import com.mmessage.dcu.sylvain.model.Commands;
import com.mmessage.dcu.sylvain.model.TaskMessage;
import com.mmessage.dcu.sylvain.tasks.GetRESTTask;

public class MainActivityController implements OnTaskCompleted {

	private static String _authentication = "";
	private static String _userName = "";

	private String _urlPostUser = "http://message.eventhub.eu/users";
	private OnTaskCompleted _listener;

	public MainActivityController(OnTaskCompleted parListener) {
		_listener = parListener;

	}

	public void logInUser(String parUserName, String parPassword) {
		String authentication = parUserName + ":" + parPassword;
		_userName = parUserName;

		String encoding = Base64.encodeToString(authentication.getBytes(),
				Base64.NO_WRAP);
		_authentication = encoding;

		new GetRESTTask(this, Commands.GET_ALL_USERS).execute(_urlPostUser);
	}

	@Override
	public void onTaskCompleted(Object parObject) {

		TaskMessage locTaskMessage = (TaskMessage) parObject;

		if (locTaskMessage.getHttpCode() == HttpStatus.SC_OK) {
			_listener.onTaskCompleted(Boolean.valueOf(true));
		} else {
			_listener.onTaskCompleted(Boolean.valueOf(false));
		}
	}

	public static String getAuthentification() {
		return _authentication;
	}

	public static String getUserName() {
		return _userName;
	}

}
