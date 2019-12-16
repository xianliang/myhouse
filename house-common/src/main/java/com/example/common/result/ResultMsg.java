package com.example.common.result;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author liangxianliang
 * @create 2019-12-04 14:08
 */
public class ResultMsg {
    public static final String errorMsgKey = "errorMsg";

    public static final String successMsgKey = "successMsg";

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }

    public boolean isSuccess(){
        return errorMsg == null;
    }

    private String errorMsg;

    private String successMsg;

    public static ResultMsg errorMsg(String msg){
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.setErrorMsg(msg);
        return resultMsg;
    }

    public static ResultMsg successMsg(String msg){
        ResultMsg  resultMsg = new ResultMsg();
        resultMsg.setSuccessMsg(msg);
        return resultMsg;
    }

    public Map<String,String> asMap(){
        Map<String ,String> map = Maps.newHashMap();
        map.put(successMsgKey,successMsg);
        map.put(errorMsgKey,errorMsg);
        return map;
    }
    public String asUrlParams(){
         Map<String,String> map = asMap();
         Map<String,String> newMap = Maps.newHashMap();
         map.forEach((k,v) -> {if(v!=null)
             try{
                 newMap.put(k, URLEncoder.encode(v,"utf-8"));
             }catch (UnsupportedEncodingException e){

             }
                 });
         return Joiner.on("&").useForNull("").withKeyValueSeparator("=").join(newMap);
    }
}
