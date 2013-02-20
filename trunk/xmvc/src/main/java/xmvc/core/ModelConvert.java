package xmvc.core;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("rawtypes")
public class ModelConvert {
	public static final String splitSUFFIX = "\\.";
	public static final Logger log = LoggerFactory.getLogger(ModelConvert.class);
	private static Map<String, String> convertPlug = null;
	
	private Field convertField;
	private ServletRequest req;
	private Object convertObject;
	
	private Set<String> skipParams = new HashSet<String>();
	
	
	public Field getConvertField() {
		return convertField;
	}
	public void setConvertField(Field convertField) {
		this.convertField = convertField;
	}

	/**
	 * 初始化
	 * @param propPath
	 * @throws IOException
	 */
	public static void load(String propPath) {
		if(propPath == null || propPath.trim().equals("")){
			return;
		}
		log.info("初始化ModelConvert属性文件{}",propPath);
		InputStream is = ModelConvert.class.getClassLoader().getResourceAsStream(propPath);
		try {
			_load(is);
		} catch (Exception e) {
			throw new RuntimeException("不能读取配置文件:"+propPath,e);
		}
	}
	
	public static void loadFormJar(String propPath){
		
		try {
			log.info("初始化ModelConvert属性文件{}",propPath); 
			InputStream is = ModelConvert.class.getClassLoader().getResourceAsStream(propPath);
			_load(is);
		} catch (Exception e) {
			throw new RuntimeException("不能读取配置文件："+propPath, e);
		}
		
	}
	
	private static void _load(InputStream is) throws IOException {
		if(convertPlug == null){
			convertPlug = new HashMap<String, String>();
		}
		
		Properties props = new Properties();
		props.load(is);
		Set<Object> keyValue = props.keySet();
		if (keyValue != null) {
			Iterator<Object> it = keyValue.iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				String value = props.getProperty(key);
				convertPlug.put(key, value);
			}
		}
		if (is != null) {
			is.close();
		}
	}
	
	public static void convert(final Object p,ServletRequest req) {
		ModelConvert mc = new ModelConvert();
		mc.req = req;
		mc.convertObject = p;
		mc._convert();
	}
	
	
	private void _convert() {
		@SuppressWarnings("unchecked")
		Enumeration<String> parameterNames = req.getParameterNames();
		while (parameterNames != null && parameterNames.hasMoreElements()) {
			String parameter = parameterNames.nextElement();
			String[] values = req.getParameterValues(parameter);
			if(parameter == null || parameter.trim().length()==0){
				throw new RuntimeException("页面参数存在问题");
			}
			
			String[] complexParam =  parameter.split(splitSUFFIX);
			parseParam(convertObject,complexParam,0,values);
		}
	}
	/**
	 * 将值set到属性中，这里只针对复杂参数的设置
	 * 所谓复杂参数，指的是页面请求中带.的参数，反射到实体对象中的属性可能是任何负责对象及List,Set,Map数组的组合
	 * 有别于简单类型
	 * @see ModelConvert.parseSimpleParam
	 * @param o Object对象
	 * @param complexParam 参数名[]
	 * @param index 用于定规
	 * @param parameterValues 参数值
	 */
	@SuppressWarnings("unchecked")
	protected void parseParam(final Object o, final String[] complexParam,int index, final String[] fieldValue) {
		//a.b
		String fieldName = complexParam[index];
		Object fieldObject = null;
		if(index == complexParam.length-1){//已经递归到最后？
			parseSimpleParam(o, fieldName, fieldValue);
		}else if(index < complexParam.length-1){ //还没有递归到最后
			if(!fieldName.contains("[") && !fieldName.contains("]")){
				fieldObject = getFieldValueByName(fieldName, o);
				if(fieldObject == null){
					try {
						Class classz = getFieldByName(fieldName, o).getType();
						fieldObject = classz.newInstance();
						parseObjectFieldToObject(o, fieldName, fieldObject);
					} catch (Exception e) {
						new RuntimeException("Process中实例化属性实体时异常，请确保该属性对象能够被实例化"+fieldObject.getClass().getName(),e);
					}
				}
				index++;
				parseParam(fieldObject,complexParam,index, fieldValue);
			}else if(fieldName.contains("[") && fieldName.contains("]")){//map eq: a[x0]=x&a[x1]=b
				//从params获取相同的field
				String strKey = fieldName.substring(fieldName.indexOf("[")+1, fieldName.indexOf("]"));
				fieldName = fieldName.substring(0, fieldName.indexOf("["));
				Field field = getFieldByName(fieldName, o);
				Class fieldClass= field.getType();
				fieldObject = getFieldValueByName(fieldName, o);
				if(fieldObject == null){
					try {
						if(fieldClass.isInterface()){
							if(List.class.isAssignableFrom(fieldClass)){
								List tempfieldObject = ArrayList.class.newInstance();
								fieldObject = tempfieldObject;
							}else if(Set.class.isAssignableFrom(fieldClass)){
								fieldObject = HashSet.class.newInstance();
							}else if(Map.class.isAssignableFrom(fieldClass)){
								fieldObject = HashMap.class.newInstance();
							}
						}else if(fieldClass.isArray()){
							fieldObject = Array.newInstance(fieldClass.getComponentType(), getMaxIndexOfArrayParam(complexParam,index)+1);
							int key = Integer.parseInt(strKey);
							Array.set(fieldObject, key, fieldClass.getComponentType().newInstance());
						}else{
							fieldObject = fieldClass.newInstance();
						}
						
					} catch (Exception e) {
						throw new RuntimeException("初始化实例出错",e);
					}
				}
				
				if(fieldClass.isArray()){
					int key = Integer.parseInt(strKey);
					try {
						Array.set(fieldObject, key, fieldClass.getComponentType().newInstance());
					} catch (Exception e) {
						throw new RuntimeException("初始化属性(数组)实例出错",e);
					}
					index++;
					parseParam(Array.get(fieldObject, key),complexParam,index, fieldValue);
				}else if(List.class.isAssignableFrom(fieldClass)){
					int key = Integer.parseInt(strKey);
					List lfieldObject = (List) fieldObject;
					int maxKey = getMaxIndexOfArrayParam(complexParam,index);
					
					while(lfieldObject.size() < maxKey +1){
						lfieldObject.add(null);
					}
					try {
						ParameterizedType pt = (ParameterizedType)field.getGenericType();
						Class genericClass = (Class) pt.getActualTypeArguments()[0];
						lfieldObject.set(key, genericClass.newInstance());
						index++;
						parseParam(lfieldObject.get(key),complexParam,index, fieldValue);
					} catch (Exception e) {
						throw new RuntimeException("初始化属性(List)实例出错",e);
					}
				}else if(Set.class.isAssignableFrom(fieldClass)){
					try {
						ParameterizedType pt = (ParameterizedType)field.getGenericType();
						Class genericClass = (Class) pt.getActualTypeArguments()[0];
						Object obj = genericClass.newInstance();
						index++;
						parseParam(obj,complexParam,index, fieldValue);
						Set sfieldObject = (Set)fieldObject;
						sfieldObject.add(obj);
					} catch (Exception e) {
						throw new RuntimeException("初始化属性(Set)实例出错",e);
					}
				}else if(Map.class.isAssignableFrom(fieldClass)){
					ParameterizedType pt = (ParameterizedType)field.getGenericType();
					Class genericKeyClass = (Class) pt.getActualTypeArguments()[0];
					Class genericValueClass = (Class) pt.getActualTypeArguments()[1];
					try {
						ModelConvert modelConvert = ModelConvert.instance(genericKeyClass);
						
						Object valueObj = genericValueClass.newInstance();
						index++;
						parseParam(valueObj,complexParam,index, fieldValue);
						Map mfieldObject = (Map)fieldObject;
						mfieldObject.put(modelConvert.convertObjectValue(strKey), valueObj);
					} catch (Exception e) {
						throw new RuntimeException("初始化属性(Map)实例出错",e);
					}
				}
				parseObjectFieldToObject(o, fieldName, fieldObject);
			}
			
			
		}
		
	}

	/**
	 * 获取参数列表是数组类型的最大Index
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private int getMaxIndexOfArrayParam(final String[] complexParam,int index){
		Enumeration<String> parameterNames = req.getParameterNames();		
		int maxKey = 0;
		
		while(parameterNames!=null && parameterNames.hasMoreElements()){
			String parameterName = parameterNames.nextElement();
			String[] names = parameterName.split(splitSUFFIX);
			if(names == null || names.length != complexParam.length){
				continue;
			}
			
			boolean isEquals = true;
			for(int i = 0; i <= index; i++){
				
				if(i < index && !names[i].equals(complexParam[i])){
					isEquals = false;
					break;
				}
				
				if(i == index 
						&& !names[i].substring(0, names[i].indexOf("[")).equals(complexParam[i].substring(0, complexParam[i].indexOf("[")))){
					isEquals = false;
					break;
				}
			}
			if(!isEquals) {
				continue;
			}else{
				String key =names[index].substring(names[index].indexOf("[")+1,names[index].indexOf("]"));
				maxKey = (maxKey < Integer.parseInt(key)? Integer.parseInt(key):maxKey);
			}
		}
	
		
		
		
		return maxKey;
	}

	/**
	 * 将对象属性的值反射进去
	 * @param o
	 * @param fieldName
	 * @param field
	 */
	private void parseObjectFieldToObject(Object o, String fieldName, Object fieldValue) {

		Field convertField = null;
		
		convertField = getFieldByName(fieldName, o);
		
		String firstLetter = fieldName.substring(0, 1).toUpperCase();
		String setter = "set" + firstLetter + fieldName.substring(1);
		try {
			Method method = o.getClass().getMethod(setter, convertField.getType());
			method.invoke(o, fieldValue);     
		} catch (Exception e) {
			throw new RuntimeException(o.getClass().getName()+"."+setter+"失败，请检查转换器属性文件",e);
		}    
	}
	
	
	
	/**
	 * 将值set到属性中，这里只针对简单参数的设置
	 * 所谓简单参数，指的是页面请求中不带.的参数，反射到实体对象中的属性可能是
	 * java基本类型(相应的包装类型)，日期类型，BigDecimal及这三种对应的数组，List,Set,Map
	 * @param o Object对象
	 * @param parameter 参数名
	 * @param parameterValues 参数值
	 */
	protected void parseSimpleParam(final Object o, final String parameter, final String[] parameterValues) {
		// 日期，bigdecimal,数组,List, Set, Map(key 为数值，从0开始)
		Object convertObject = null;
		Field convertField = null;
		ModelConvert modelConvert = null;
		String fieldName = parameter;
		
		if(!fieldName.contains("[") && !fieldName.contains("]")){
			convertField = getFieldByName(fieldName, o);
			if(convertField.getType().isArray()){
				convertObject = parameterValues;
			}else if(parameterValues.length==1){
				convertObject = parameterValues[0];
			}else{
				convertObject = parameterValues;
			}
		}else if(fieldName.contains("[") && fieldName.contains("]")){
			//从params获取相同的field
			fieldName = fieldName.substring(0, fieldName.indexOf("["));
			convertField = getFieldByName(fieldName, o);
			convertObject = getSameSimpleParametars(fieldName);
			
		}
		
		if(convertField.getType().isArray()){
			modelConvert = ModelConvert.instance(Array.class);
		}else{
			modelConvert = ModelConvert.instance(convertField.getType());
		}
		modelConvert.setConvertField(convertField);
		parseObjectFieldToObject(o, fieldName,  modelConvert.convertObjectValue(convertObject));
		skipParams.add(fieldName);
	}


	/**
	 * 从Parameteor中获取相同的对象
	 * @param fieldName
	 * @param fieldClass
	 * @return
	 */
	private Object getSameSimpleParametars(final String fieldName) {
		@SuppressWarnings("unchecked")
		Enumeration<String> parameterNames = req.getParameterNames();
		Map<String,String> map = new HashMap<String, String>();
		while(parameterNames!=null && parameterNames.hasMoreElements()){
			String parameterName = parameterNames.nextElement();
			if(parameterName.startsWith(fieldName+"[") && parameterName.endsWith("]")){
				String key =parameterName.substring(parameterName.indexOf("[")+1,parameterName.indexOf("]"));
				map.put(key, req.getParameter(parameterName));
			}else{
				continue;
			}
		}
		return map;
	}
	/**
	 * 
	 * @param i
	 * @param fieldNames eg. : objectA.objectB.objectC ==> [objectA,objectB,objectC]
	 * @param o
	 * @param classz
	 * @param value 参数值
	 * @throws  
	 * @throws Exception 
	 */
	/*
	@SuppressWarnings("unchecked")
	public static void fillfill(int i,String[] fieldNames,Object o,String[] values) throws Exception{
		String fieldName = fieldNames[i];
		if(i == fieldNames.length-1){
			Class  classz = getFieldByName(fieldName, o);
			if(fieldName.contains("[") && fieldName.contains("]")){
				fillCollection(i,fieldNames,o,values);
			}else{
				methodCallSet(o, fieldName, convertBasicValue(classz,values), classz);	
			}
		}else if(i < fieldNames.length-1){
			Class classz = getFieldByName(fieldName, o);
			if(fieldName.contains("[") && fieldName.contains("]")){
				fillCollection(i,fieldNames,o,values);
			}else{
				Object object = getFieldValueByName(fieldName, o);
				if(object == null){
					try {
						object = classz.newInstance();
						methodCallSet(o, fieldNames[i], object, classz);
					} catch (Exception e) {
						new RuntimeException("Process中实例化属性实体时异常，请确保该属性对象能够被实例化"+object.getClass().getName(),e);
					}
				}
				i++;
				fillfill(i,fieldNames,object,values);
			}
		}
	}
	*/
	/*
	private static void fillCollection(int i,String[] fieldNames,Object o,String[] values) throws Exception{
		String fieldName = fieldNames[i];

		//List Map Set Array
		String _fieldName = fieldName.substring(0, fieldName.indexOf("["));
		if(_fieldName == null || _fieldName.trim().length() == 0){
			throw new RuntimeException("参数传递异常"+joinStrings(fieldNames));
		}
		
		String skey = fieldName.substring(fieldName.indexOf("[")+1, fieldName.indexOf("]"));
		Class fieldClassz = getFieldByName(_fieldName, o);
		Object fieldValue = getFieldValueByName(_fieldName, o);
		
		
		Type listType = o.getClass().getField(_fieldName).getGenericType();
		if(!(listType instanceof ParameterizedType)){
			throw new RuntimeException("数据转换异常，批量提交复杂对象，需要泛型的支持");
		}
		ParameterizedType pt = (ParameterizedType) listType;
		
		//List
		if(fieldClassz==List.class || fieldClassz == ArrayList.class){
			Class classz = (Class) pt.getActualTypeArguments()[0];
			int key = Integer.parseInt(skey);
			List list = null;
			if(fieldValue == null){
				list = new ArrayList(key+1);
				for(int size = 0; size < list.size(); size++){
					Object obj  = classz.newInstance();
					list.add(obj);
				}
			}else{
				list = (List) fieldValue;
				int oldSize = list.size();
				if(list.size()<key+1){
					for(int size = oldSize;size < list.size();size++){
						Object obj  = classz.newInstance();
						list.add(obj);
					}
				}
			}
			
			//a[0],a[1]..
			if(i == fieldNames.length-1){
				list.set(key, convertBasicValue(classz, values));
			}else{ //a[0].b.c
				fillfill(i+1,fieldNames,list.get(key),values);
			}
			methodCallSet(o,_fieldName,list,fieldClassz);
			
		}else if(fieldClassz==Map.class || fieldClassz == HashMap.class){//map
			Class classKey = (Class) pt.getActualTypeArguments()[0];
			Class classzValue = (Class) pt.getActualTypeArguments()[1];
			Map map = null;
			if(fieldValue == null){
				map = new HashMap();
			}else{
				map = (Map) fieldValue;					
			}
			
			String[] keys = new String[1];
			keys[0] = skey;
			Object objKey = convertBasicValue(classKey,keys);

			//a['0'],a['1']..
			if(i == fieldNames.length-1){
				map.put(objKey,convertBasicValue(classzValue,values));
			}else{ //a['0'].b.c
				Object objValue  = classzValue.newInstance();
				map.put(objKey, objValue);
				fillfill(i+1,fieldNames,map.get(skey),values);
			}
			methodCallSet(o,_fieldName,map,fieldClassz);
		
		}else{
			throw new RuntimeException("复杂对象集合的转换当前仅支持List,Map");
		}
		
	
	}
*/
	public static String joinStrings(String[] ss){
		if(ss == null) return null;
		StringBuilder sb = new StringBuilder("");
		for(String s : ss){
			sb.append(s).append(".");
		}
		return sb.toString();
	}
	

	/**
	 * 转换器
	 * @param fieldClass
	 * @param fieldValue
	 * @return
	 */
	/*private static Object convertBasicValue(Class fieldClass,String[] fieldValue){
		if (fieldClass.equals(ModelMapping.INTEGER.getClassz()) || fieldClass.equals(ModelMapping._INT.getClassz())) {
			ModelConvert modelConvert = ModelConvert.instance(ModelMapping._INT.classz.getName());
			modelConvert.setConvertType(fieldClass);
			return modelconvert.convertObjectValue(fieldValue);
		} else if (fieldClass.equals(ModelMapping._LONG.getClassz()) || fieldClass.equals(ModelMapping.LONG.getClassz())) {
			ModelConvert modelConvert = ModelConvert.instance(ModelMapping._LONG.classz.getName());
			modelConvert.setConvertType(fieldClass);
			return modelconvert.convertObjectValue(fieldValue);
		} else if (fieldClass.equals(ModelMapping._FLOAT.getClassz()) || fieldClass.equals(ModelMapping.FLOAT.getClassz())) {
			ModelConvert modelConvert = ModelConvert.instance(ModelMapping._FLOAT.classz.getName());
			modelConvert.setConvertType(fieldClass);
			return modelconvert.convertObjectValue(fieldValue);
		} else if (fieldClass.equals(ModelMapping._SHORT.getClassz()) || fieldClass.equals(ModelMapping.SHORT.getClassz())) {
			ModelConvert modelConvert = ModelConvert.instance(ModelMapping._SHORT.classz.getName());
			modelConvert.setConvertType(fieldClass);
			return  modelconvert.convertObjectValue(fieldValue);
		} else if (fieldClass.equals(ModelMapping._DOUBLE.getClassz()) || fieldClass.equals(ModelMapping.DOUBLE.getClassz())) {
			ModelConvert modelConvert = ModelConvert.instance(ModelMapping._DOUBLE.classz.getName());
			modelConvert.setConvertType(fieldClass);
			return modelconvert.convertObjectValue(fieldValue);
		} else if (fieldClass.equals(ModelMapping._CHAR.getClassz()) || fieldClass.equals(ModelMapping.CHARACTER.getClassz())) {
			ModelConvert modelConvert = ModelConvert.instance(ModelMapping._CHAR.classz.getName());
			modelConvert.setConvertType(fieldClass);
			return modelconvert.convertObjectValue(fieldValue);
		} else if (fieldClass.equals(ModelMapping._BOOLEAN.getClassz()) || fieldClass.equals(ModelMapping.BOOLEAN.getClassz())) {
			ModelConvert modelConvert = ModelConvert.instance(ModelMapping._BOOLEAN.classz.getName());
			modelConvert.setConvertType(fieldClass);
			return modelconvert.convertObjectValue(fieldValue);
		} else if (fieldClass.equals(ModelMapping.STRING.getClassz())) {
			return fieldValue[0];
		} else if (fieldClass.equals(ModelMapping.DATE.getClassz())) {
			ModelConvert modelConvert = ModelConvert.instance(ModelMapping.DATE.classz.getName());
			modelConvert.setConvertType(fieldClass);
			return modelconvert.convertObjectValue(fieldValue);
		} else if (fieldClass.getComponentType() != null) {
			if (fieldClass.getComponentType().equals(byte.class)) {
				ModelConvert modelConvert = ModelConvert.instance(ModelMapping._BYTE.classz.getName());
				modelConvert.setConvertType(fieldClass);
				return modelconvert.convertObjectValue(fieldValue);
			} else if (fieldClass.getComponentType().equals(Byte.class)) {
				ModelConvert modelConvert = ModelConvert.instance(ModelMapping._BYTE.classz.getName());
				modelConvert.setConvertType(fieldClass);
				return modelconvert.convertObjectValue(fieldValue);
			} else {
				ModelConvert modelConvert = ModelConvert.instance(ModelMapping.ARRAY.classz.getName());
				modelConvert.setConvertType(fieldClass);
				return modelconvert.convertObjectValue(fieldValue);
			}
		}else {
			throw new RuntimeException("不支持当前数据格式的转换"+fieldClass.getCanonicalName());
		}
	
	}*/
	
	/**
	 * 根据转换插件名获取插件实例
	 * @param pluginName
	 * @return
	 */
	protected static ModelConvert instance(Class classz){
		String pluginName = convertPlug.get(classz.getCanonicalName());
		if(pluginName == null){
			throw new RuntimeException("没有找到类型转换器，请检查web.xml中是否配置了转换器属性文件");
		}
		try {
			ModelConvert modelConvert = (ModelConvert)Class.forName(pluginName).newInstance();
			return modelConvert;
		} catch (Exception e) {
			throw new RuntimeException("没有找到对于的ModelConvert，请检查转换器属性文件",e);
		}
		
	}
	
	/**
	 * 根据属性名获取属性值
	 * */
	private static Object getFieldValueByName(String fieldName, Object o) {
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getter = "get" + firstLetter + fieldName.substring(1);
			Method method = o.getClass().getMethod(getter, new Class[] {});
			Object value = method.invoke(o, new Object[] {});
			return value;
		} catch (Exception e) {
			throw new RuntimeException("ModelConvert获取属性出错");
		}
	}

	/**
	 * 根据属性名从对象中获取属性的Field
	 * @param fieldName
	 * @param o
	 * @return
	 */
	protected Field getFieldByName(String fieldName,Object o){
		Class classz = o.getClass();
		try {
			return classz.getDeclaredField(fieldName);
		} catch (Exception e) {
			throw new RuntimeException("找不到属性?",e);
		}
		
	}


	/**
	 * 
	 * @param fieldValue
	 * @return set Value
	 */
	public Object convertObjectValue(Object objectValues){
		return null;
	}
	
	
}
