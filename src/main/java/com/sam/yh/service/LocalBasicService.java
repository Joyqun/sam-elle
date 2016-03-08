package com.sam.yh.service;

//import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.sam.yh.crud.exception.CrudException;
import com.sam.yh.req.bean.LatLonReq;


public interface LocalBasicService {

	 @Transactional
	 public LatLonReq  uploadLbsInfo(String mcc,String mnc,String lac,String ci) throws CrudException;
	 

}
