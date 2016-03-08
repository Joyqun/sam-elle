package com.sam.yh.req.bean;

import java.io.Serializable;
import java.util.Map;

public class LatLonReq implements Serializable{
	 private static final long serialVersionUID = 1L;
//     private String errcode;
     private String lat;
     private String lon;
//     private String radius;
//     private String address;

   

public LatLonReq() {
		super();
	
	}

//public String getErrcode() {
//		return errcode;
//	}
//	public void setErrcode(String errcode) {
//		this.errcode = errcode;
//	}
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
//public String getRadius() {
//	return radius;
//}
//public void setRadius(String radius) {
//	this.radius = radius;
//}
//public String getAddress() {
//	return address;
//}
//public void setAddress(String address) {
//	this.address = address;
//}
//"errcode":0, "lat":"40.004944", "lon":"116.482315", "radius":"265", "address":"鍖椾含甯傛湞闃冲尯宕斿悇搴勫湴鍖烘邯闃充笢璺?鏈涗含涓滆矾涓庢邯闃充笢璺矾鍙ｄ笢鍖?0绫?}

//@Override
//public String toString() {
//	 
//    return  errcode + ", " + lat + ","+ lon+", "+ radius + "," + address ;
//}

}
