package xmvc.core.modelconvertplug;


import xmvc.core.ModelConvert;

public class SimpleStringModelConvert extends ModelConvert implements IModelConvert{
	public Object convertObjectValue(Object objectValues) {
		return objectValues;
	}
	
}
