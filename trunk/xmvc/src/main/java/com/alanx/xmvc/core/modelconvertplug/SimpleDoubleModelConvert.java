package com.alanx.xmvc.core.modelconvertplug;

import com.alanx.xmvc.core.ModelConvert;

public class SimpleDoubleModelConvert extends ModelConvert implements IModelConvert{
	public Object convertObjectValue(Object objectValues) {
		
		try {
			String stringValue = (String) objectValues;
			double value = 0;
			if (stringValue!= null) {
				value = Double.parseDouble(stringValue.trim());
			}
			return value;
		} catch (Exception e) {
			throw new RuntimeException("ModelConvert获取属性出错",e);
		}
	
	}
	
}
