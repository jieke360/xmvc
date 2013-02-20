package xmvc.core.modelconvertplug;

import xmvc.core.ModelConvert;

public class SimpleCharModelConvert extends ModelConvert implements IModelConvert{
	public Object convertObjectValue(Object objectValues) {
		
		try {
			String stringValue = (String) objectValues;
			if (stringValue!= null && stringValue.length() ==1) {
				return stringValue.charAt(0);
			}else {
				throw new RuntimeException("ModelConvert类型转换错误，char类型的字符长度必须为1");
			}
		} catch (Exception e) {
			throw new RuntimeException("ModelConvert获取属性出错",e);
		}
	
	}
	
}
