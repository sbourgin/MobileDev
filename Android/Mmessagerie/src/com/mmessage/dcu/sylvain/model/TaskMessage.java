package com.mmessage.dcu.sylvain.model;

public class TaskMessage {

	private Commands _command;
	private int _httpCode;
	private Object _result;
	
	public TaskMessage(Commands parCommand, int parHttpCode, Object parResult) {
		this._command = parCommand;
		this._httpCode = parHttpCode;
		this._result = parResult;
	}
	public Commands getCommand() {
		return _command;
	}
	public int getHttpCode() {
		return _httpCode;
	}
	public Object getResult() {
		return _result;
	}
	
}
