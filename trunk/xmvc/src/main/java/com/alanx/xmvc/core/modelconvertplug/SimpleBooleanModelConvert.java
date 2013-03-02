package com.alanx.xmvc.core.modelconvertplug;

import com.alanx.xmvc.core.ModelConvert;

public class SimpleBooleanModelConvert extends ModelConvert implements IModelConvert{
	public Object convertObjectValue(Object objectValues) {
		
		try {
			String stringValue = (String) objectValues;
			if (stringValue!= null ) {
				
				if(stringValue.equalsIgnoreCase("true") || stringValue.equals("1")) return true;
				else if(stringValue.equalsIgnoreCase("false") || stringValue.equals("0")) return false;
				else throw new RuntimeException("ModelConvert获取属性出错,必须是true,false,1,0");
			}else {
				throw new RuntimeException("ModelConvert获取属性出错,必须是true,false,1,0");
			}
		} catch (Exception e) {
			throw new RuntimeException("ModelConvert获取属性出错,必须是true,false,1,0",e);
		}
	
	}
	
}
