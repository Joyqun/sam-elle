package com.sam.yh.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import com.sam.yh.crud.exception.CrudException;
import com.sam.yh.req.bean.LatLonReq;
import com.sam.yh.req.bean.LbsInfoReq;
import com.sam.yh.resp.bean.BtyFollower;
import com.sam.yh.dao.BatteryMapper;
import com.sam.yh.dao.BatteryInfoMapper;
import com.sam.yh.service.LocalBasicService;

@Service
public class LocalBasicServiceImp implements LocalBasicService {
    private static final Logger logger = LoggerFactory.getLogger(BatteryServiceImpl.class);
    @Resource
    private BatteryMapper batteryMapper;
    @Resource
    private BatteryInfoMapper batteryInfoMapper;
   
  @Override
  public LatLonReq uploadLbsInfo(String mcc,String mnc,String lac,String ci) throws CrudException {

		String url="http://api.cellocation.com/cell/";
		String param="mcc="+mcc+"&mnc="+mnc+"&lac="+lac+"&ci="+ci+"&output=xml";
		
		String message=sendGet(url,param);
		System.out.println(message);
		logger.info("message::" + message);
		LatLonReq req=new LatLonReq();
	    String lat=ReadXml(message, "lat");
	    String lon=ReadXml(message, "lon");
	    req.setLat(lat);
	    req.setLon(lon);
   
	    return req;
	}	
  
  public static  String ReadXml(String strXML,String key){
		 StringReader read = new StringReader(strXML);
	        //创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
	        InputSource source = new InputSource(read);
	        //创建一个新的SAXBuilder
	        SAXBuilder sb = new SAXBuilder();
	            //通过输入源构造一个Document
	        try
	        {
	           Document doc = sb.build(source);
	            //取的根元素
	            Element root = doc.getRootElement();
	           Element value=root.getChild(key);
	           return value.getValue();
	        }
	        catch (JDOMException e) {
	            // TODO 自动生成 catch 块
	            e.printStackTrace();
	        } catch (IOException e) {
	            // TODO 自动生成 catch 块
	            e.printStackTrace();
	        }
	        return null;
	  }
 
  
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
        	try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
    

}
        

