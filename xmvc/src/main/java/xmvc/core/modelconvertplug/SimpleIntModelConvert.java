package xmvc.core.modelconvertplug;

import xmvc.core.ModelConvert;

public class SimpleIntModelConvert extends ModelConvert implements IModelConvert{
	public Object convertObjectValue(Object objectValues) {
		String stringValue = (String) objectValues;
		try {
			int value = 0;
			if (stringValue!= null) {
				value = Integer.parseInt(stringValue.trim());
			}
			return value;
		} catch (Exception e) {
			throw new RuntimeException("ModelConvert获取属性出错",e);
		}
	
	}
	
}
