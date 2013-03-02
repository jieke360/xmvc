package com.alanx.xmvc.core.modelconvertplug;

import com.alanx.xmvc.core.ModelConvert;

public class SimpleShortModelConvert extends ModelConvert implements IModelConvert{
	public Object convertObjectValue(Object objectValues) {
		
		try {
			String stringValue = (String) objectValues;
			short value = 0;
			if (stringValue!= null) {
				value = Short.parseShort(stringValue.trim());
			}
			return value;
		} catch (Exception e) {
			throw new RuntimeException("ModelConvert获取属性出错",e);
		}
	
	}
	
}
