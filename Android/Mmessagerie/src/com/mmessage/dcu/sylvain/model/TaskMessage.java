package com.mmessage.dcu.sylvain.model;

import java.io.Serializable;

public class TaskMessage implements Cloneable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1656463781528208691L;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((_command == null) ? 0 : _command.hashCode());
		result = prime * result + _httpCode;
		result = prime * result + ((_result == null) ? 0 : _result.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaskMessage other = (TaskMessage) obj;
		if (_command != other._command)
			return false;
		if (_httpCode != other._httpCode)
			return false;
		if (_result == null) {
			if (other._result != null)
				return false;
		} else if (!_result.equals(other._result))
			return false;
		return true;
	}

}
