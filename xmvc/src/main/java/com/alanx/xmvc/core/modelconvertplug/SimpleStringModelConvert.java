package com.alanx.xmvc.core.modelconvertplug;


import com.alanx.xmvc.core.ModelConvert;

public class SimpleStringModelConvert extends ModelConvert implements IModelConvert{
	public Object convertObjectValue(Object objectValues) {
		return objectValues;
	}
	
}
