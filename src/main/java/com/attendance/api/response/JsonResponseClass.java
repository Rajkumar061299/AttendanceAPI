package com.attendance.api.response;

public class JsonResponseClass {

	private Object data;
	private Object error;

	public Object getData() {
		return data;
	}

	public Object getError() {
		return error;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public void setError(Object error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "JsonResponseClass [data=" + data + ", error=" + error + "]";
	}

}
