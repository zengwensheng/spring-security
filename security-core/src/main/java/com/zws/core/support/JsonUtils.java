package com.zws.core.support;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/9/30
 */
public interface JsonUtils {

    ObjectMapper objectMapper = new ObjectMapper();


    static String writeValueAsString(Object object){
        if(object==null){
            return "";
        }
        try{
            return  objectMapper.writeValueAsString(object);
        }catch (Exception e){
            return "";
        }
    }

    static <T> T readValue(String result,Class<T> obj){
         try{
            return objectMapper.readValue(result,obj);
         }catch (Exception e){
            return null;
         }
    }




}
