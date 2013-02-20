package xmvc.core.modelconvertplug;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import xmvc.core.ModelConvert;

public class MapModelConvert extends ModelConvert implements IModelConvert{
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object convertObjectValue(Object objectValues) {
		Map result = null;
		Field field = this.getConvertField();
		Class classz = field.getType();
		ParameterizedType pt = (ParameterizedType)field.getGenericType();
		Class genericClassKey = (Class) pt.getActualTypeArguments()[0];
		Class genericClassValue= (Class) pt.getActualTypeArguments()[1];
		ModelConvert convertKey = ModelConvert.instance(genericClassKey);
		ModelConvert convertValue = ModelConvert.instance(genericClassValue);
		
		if(classz == Map.class || classz == HashMap.class){
			result = new HashMap();
			
		}else if(classz == TreeMap.class){
			result = new TreeMap();
		}else if(classz == Hashtable.class){
			result = new Hashtable();
		}else if(classz == LinkedHashMap.class){
			result = new LinkedHashMap();
		}else if(classz == ConcurrentHashMap.class){
			result = new ConcurrentHashMap();
		}
	
		if(objectValues instanceof String[]){
			String[] stringValues = (String[]) objectValues;
			for(int i = 0; i < stringValues.length; i++){
				result.put(convertKey.convertObjectValue(i+""), convertValue.convertObjectValue(stringValues[i]));
			}
		}else if(objectValues instanceof Map){
			Map<String,String> mapValues =  (Map<String, String>) objectValues;
			if(mapValues !=null){
				for(String key : mapValues.keySet() ){
					result.put(convertKey.convertObjectValue(key), convertValue.convertObjectValue(mapValues.get(key)));
				}
			}
		}
		
		return result;
	}
	
}
