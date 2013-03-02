package com.alanx.xmvc.core.modelconvertplug;

import java.lang.reflect.Array;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.alanx.xmvc.core.ModelConvert;

public class ArrayModelConvert extends ModelConvert implements IModelConvert{
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object convertObjectValue(Object objectValues) {
		Object arry = null;
		Class classz = this.getConvertField().getType().getComponentType();
		ModelConvert convert = ModelConvert.instance(classz);
		
		if(objectValues instanceof String[]){
			String[] stringValues = (String[]) objectValues;
			arry = Array.newInstance(classz,stringValues.length);
			for(int i = 0; i < stringValues.length;i++){
				Array.set(arry, i, convert.convertObjectValue(stringValues[i]));
			}
		}else if(objectValues instanceof Map){
			Map<String,String> mapValues =  (Map<String, String>) objectValues;
			if(mapValues !=null){
				Set<String> keySetOrder = new TreeSet<String>();
				for(String strKey : mapValues.keySet() ){
					keySetOrder.add(strKey);
				}
				arry = Array.newInstance(classz,keySetOrder.size());
				
				int i = 0;
				for(String strKey : keySetOrder){
					Array.set(arry, i, convert.convertObjectValue(mapValues.get(strKey)));
					i++;
				}
				
			}
		}
		return arry;
	}
	
}
