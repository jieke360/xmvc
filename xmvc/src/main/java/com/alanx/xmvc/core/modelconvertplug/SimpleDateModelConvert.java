package com.alanx.xmvc.core.modelconvertplug;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.alanx.xmvc.core.ModelConvert;



public class SimpleDateModelConvert extends ModelConvert implements IModelConvert{
	public Object convertObjectValue(Object objectValues) {
		
		try {
			String stringValue = (String) objectValues;
			Date date = null;
			if (stringValue!= null) {
				if(stringValue.length() == 10){
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
					date=sdf.parse(stringValue);  
				}else if(stringValue.length() == 19){
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
					date=sdf.parse(stringValue);  
				}else{
					throw new RuntimeException("ModelConvert获取属性出错,确保日期格式正确yyyy-MM-dd or yyyy-MM-dd hh:mm:ss");
				}
			}else {
				throw new RuntimeException("ModelConvert获取属性出错,确保日期格式正确yyyy-MM-dd or yyyy-MM-dd hh:mm:ss");
			}
			return date;
		} catch (Exception e) {
			throw new RuntimeException("ModelConvert获取属性出错 ,确保日期格式正确yyyy-MM-dd or yyyy-MM-dd hh:mm:ss",e);
		}
	
	}
	
}
