package com.sam.yh.enums;

public enum DeviceDataExtKeys {
	
	GSENSOR("gsensor");
	
	private String key;

	private DeviceDataExtKeys(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
}
