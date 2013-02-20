package xmvc.core.modelconvertplug;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import xmvc.core.ModelConvert;

public class CollectionListModelConvert extends ModelConvert implements IModelConvert{
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object convertObjectValue(Object objectValues) {
		Field field = this.getConvertField();
		Class classz = field.getType();
		
		ParameterizedType pt = (ParameterizedType)field.getGenericType();
		Class genericClass = (Class) pt.getActualTypeArguments()[0];
		ModelConvert convert = ModelConvert.instance(genericClass);
		
		List result = null;
		if(classz == List.class || classz == ArrayList.class){
			result = new ArrayList();
			
		}else if(classz == LinkedList.class){
			result = new LinkedList();
		}
		
		if(objectValues instanceof String[]){
			String[] stringValues = (String[]) objectValues;
			for(int i = 0; i < stringValues.length; i++){
				result.add(convert.convertObjectValue(stringValues[i]));
			}
		}else if(objectValues instanceof Map){
			Map<String,String> mapValues =  (Map<String, String>) objectValues;
			if(mapValues !=null){
				Set<String> keySetOrder = new TreeSet<String>();
				for(String strKey : mapValues.keySet()){
					keySetOrder.add(strKey);
				}
				for(String strKey : keySetOrder){
					result.add(convert.convertObjectValue(mapValues.get(strKey)));
				}
			}
			
		}else{
			throw new RuntimeException("CollectionListModelConvert转换异常");
		}
		return result;
	}
	
}
