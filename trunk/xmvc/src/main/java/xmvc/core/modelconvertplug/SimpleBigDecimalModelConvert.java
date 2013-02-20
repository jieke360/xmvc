package xmvc.core.modelconvertplug;

import java.math.BigDecimal;

import xmvc.core.ModelConvert;


public class SimpleBigDecimalModelConvert extends ModelConvert implements IModelConvert{
	public Object convertObjectValue(Object objectValues) {
		
		try {
			String stringValue = (String) objectValues;
			BigDecimal value = null;
			if (stringValue!= null) {
				value = new BigDecimal(stringValue);
			}
			return value;
		} catch (Exception e) {
			throw new RuntimeException("ModelConvert获取属性出错",e);
		}
	
	}
	
}
