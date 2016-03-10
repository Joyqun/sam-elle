package com.sam.yh.req.bean;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class LatLonReq implements Serializable{
	 private static final long serialVersionUID = 1L;
     private String lat;
     private String lon;
  

public LatLonReq() {
		super();
	
	}


public String getLat() {
	return lat;
}

public void setLat(String lat) {
	this.lat = lat;
}
public String getLon() {
	return lon;
}
public void setLon(String lon) {
	this.lon = lon;
}

@Override
public String toString() {
	return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
}

//@Override
//public String toString() {
//	 
//    return  errcode + ", " + lat + ","+ lon+", "+ radius + "," + address ;
//}



}
