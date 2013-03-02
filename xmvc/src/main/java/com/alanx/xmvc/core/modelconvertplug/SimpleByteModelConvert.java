package com.alanx.xmvc.core.modelconvertplug;

import com.alanx.xmvc.core.ModelConvert;

public class SimpleByteModelConvert extends ModelConvert implements IModelConvert{
	public Object convertObjectValue(Object objectValues) {
		
		try {
			String stringValue = (String) objectValues;
			if (stringValue!= null ) {
				Object object = Byte.parseByte(stringValue);
				return object;
			}else {
				throw new RuntimeException("ModelConvert获取属性出错");
			}
		} catch (Exception e) {
			throw new RuntimeException("ModelConvert获取属性出错",e);
		}
	
	}
	
}
