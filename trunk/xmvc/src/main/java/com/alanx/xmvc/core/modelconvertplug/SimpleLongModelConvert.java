package com.alanx.xmvc.core.modelconvertplug;

import com.alanx.xmvc.core.ModelConvert;

public class SimpleLongModelConvert extends ModelConvert implements IModelConvert{
	public Object convertObjectValue(Object objectValues) {
		
		try {
			String stringValue = (String) objectValues;
			long value = 0;
			if (stringValue!= null) {
				value = Long.parseLong(stringValue.trim());
			}
			return value;
		} catch (Exception e) {
			throw new RuntimeException("ModelConvert获取属性出错",e);
		}
	
	}
	
}
