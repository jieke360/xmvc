package xmvc.core.modelconvertplug;

import xmvc.core.ModelConvert;

public class SimpleFloatModelConvert extends ModelConvert implements IModelConvert{
	public Object convertObjectValue(Object objectValues) {
		
		try {
			String stringValue = (String) objectValues;
			float value = 0;
			if (stringValue!= null) {
				value = Float.parseFloat(stringValue.trim());
			}
			return value;
		} catch (Exception e) {
			throw new RuntimeException("ModelConvert获取属性出错",e);
		}
	
	}
	
}
