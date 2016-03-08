package com.sam.yh.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sam.yh.crud.exception.CrudException;
import com.sam.yh.req.bean.LbsInfoReq;
import com.sam.yh.service.LocalBasicService;

@Controller
public class UploadLbsInfoController {

    private static final Logger logger = LoggerFactory.getLogger(UploadLbsInfoController.class);

    @Autowired
    LocalBasicService localBasicService;

    @RequestMapping(value = "/upload/lbsinfo", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json")
    public @ResponseBody
    String uploadLbsInfo(HttpServletRequest httpServletRequest, LbsInfoReq lbsInfoReq) throws IOException {

        logger.info("Upload lbsinfo request url:" + httpServletRequest.getRequestURI());

        logger.info("Upload lbsinfo request:" + lbsInfoReq);
        // TODO

        try {	          	
        	localBasicService.uploadLbsInfo(lbsInfoReq.getMcc(),lbsInfoReq.getMnc(),lbsInfoReq.getLac(),lbsInfoReq.getCi());
        } catch (CrudException e) {
            logger.error("upload localBasic info error", e);
        }

        return "ok";
    }
}
