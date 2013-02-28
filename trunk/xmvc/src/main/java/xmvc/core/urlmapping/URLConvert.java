package xmvc.core.urlmapping;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import xmvc.core.XServlet;
import xmvc.core.utils.DomUtils;

public class URLConvert {
	public static final String SUFFIX = ".do";
	public static final String SERVLET_PATH="javax.servlet.include.servlet_path";

	public static final Logger log = LoggerFactory.getLogger(XServlet.class);
	private static URLConfig urlConfig = new URLConfig();
	
	public static void load(String urlConfigPath){
		log.info("初始化URL配置文件:{}",urlConfigPath);
		InputStream is = URLConvert.class.getClassLoader().getResourceAsStream(urlConfigPath.trim());

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		Document document;
		try {
			db = dbf.newDocumentBuilder();
			document = db.parse(is);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		NodeList nodeList = document.getChildNodes();
		if(nodeList.getLength() == 1){
			Node node = nodeList.item(0);
			if(ElementName.ROOT_NAME.value.equalsIgnoreCase(node.getNodeName())){
				parseURLConfig(node,urlConfigPath);
			}
		}else{
			throw new RuntimeException("XML格式错误,没有根元素"+ElementName.ROOT_NAME.value);
		}
	
		if (is != null) {
			try {
				is.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	private static void parseURLConfig(Node root,String xmlPath){
		List urlMappingNodes = DomUtils.getChildElementsByTagName((Element)root, ElementName.URLMAPPING_NAME.value);
		parseURLMapping(urlMappingNodes,xmlPath);
		List importNodes = DomUtils.getChildElementsByTagName((Element)root, ElementName.IMPORT.value);
		parseImport(importNodes,xmlPath);
		List modelConvertNodes = DomUtils.getChildElementsByTagName((Element)root, ElementName.MODEL_CONVERT_PROP_PATH.value);
		parseModelConvert(modelConvertNodes,xmlPath);
		List interceptorNodes = DomUtils.getChildElementsByTagName((Element)root, ElementName.INTERCEPTOR.value);
		parseInterceptor(interceptorNodes,xmlPath);
	}
	
	
	@SuppressWarnings("rawtypes")
	private static void parseURLMapping(List list,String xmlPath){
		Map<String,URLMapping> urlMappings = urlConfig.getUrlMappings();
		for(int i = 0; list != null && i < list.size();i++){
			Element e = (Element) list.get(i);
			URLMapping ump = new URLMapping();
			ump.setUri(DomUtils.getChildElementByTagName(e, ElementName.URI_NAME.value).getTextContent());
			ump.setClassName(DomUtils.getChildElementByTagName(e, ElementName.CLASS_NAME.value).getTextContent());
			ump.setMethodName(DomUtils.getChildElementByTagName(e, ElementName.METHODNAME_NAME.value).getTextContent());
			ump.setAction(DomUtils.getChildElementByTagName(e, ElementName.ACTION_NAME.value).getTextContent());
			if(urlMappings.keySet().contains(ump.getAction())){
				log.error("配置文件错误，Action必须唯一,{}存在重复，位于{}文件中.", ump.getAction(),xmlPath);
				throw new RuntimeException("配置文件错误，Action必须唯一.");
			}else{
				urlMappings.put(ump.getAction(),ump);
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	private static void parseImport(List list,String xmlPath){
		Set<String> imports = urlConfig.getImports();
		for(int i = 0; list != null && i < list.size();i++){
			Element e = (Element) list.get(i);
			String textContent = (e.getTextContent() == null?"":e.getTextContent().trim());
			String[] paths = textContent.trim().split(",");
			if(paths.length > 0){
				for(int j = 0; j < paths.length; j++){
					String path = paths[j].trim();
					if(!path.endsWith(".xml")){
						log.error("配置文件{}错误，{}必须是一个或多个(以逗号隔开)，并以.xml结尾", xmlPath,textContent);
					}else if(imports.contains(path)){
						log.warn("配置文件{}错误，{}重复配置",xmlPath,textContent);
					}else{
						imports.add(path);
						load(path);
					}
				}
			}else{
				log.warn("配置文件{}错误，转换器属性文件{}重复配置",xmlPath,textContent);
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	private static void parseModelConvert(List list,String xmlPath){
		Set<String> mcpps = urlConfig.getModelConvertPropPaths();
		for(int i = 0; list != null && i < list.size();i++){
			Element e = (Element) list.get(i);
			String textContent = (e.getTextContent() == null?"":e.getTextContent().trim());
			if(textContent != null) {
				String[] props = textContent.trim().split(",");
				if(props.length > 0){
					for(int j = 0; j < props.length; j++){
						String propone = props[j].trim();
						if(!propone.endsWith(".properties")){
							log.error("配置文件{}错误，转换器属性文件{}必须是一个或多个(以逗号隔开)，并以.properties结尾", xmlPath,textContent);
						}else if(mcpps.contains(propone)){
							log.warn("转换器属性文件{}重复配置",propone);
						}else{
							mcpps.add(propone);
						}
					}
				}else{
					log.warn("配置文件{}存在空的{}元素",xmlPath,ElementName.MODEL_CONVERT_PROP_PATH.value);
				}
			}
		}
	}
	@SuppressWarnings("rawtypes")
	private static void parseInterceptor(List list, String xmlPath) {
		Set<InterceptorMapping> interceptorMappings = urlConfig.getInterceptorMappings();
		for(int i = 0; list != null && i < list.size();i++){
			Element e = (Element) list.get(i);
			InterceptorMapping im = new InterceptorMapping();
			im.setClassName(DomUtils.getChildElementByTagName(e, ElementName.CLASS_NAME.value).getTextContent());
			List subList = DomUtils.getChildElementsByTagName(e, ElementName.URL_PATTERN.value);
			Set<String> urlPatterns = new HashSet<String>();
			for(int j = 0; subList!=null && j < subList.size();j++){
				String temp = ((Element)subList.get(j)).getTextContent();
				temp = (temp == null?"":temp.trim());
				urlPatterns.add(temp);
			}
			im.setUrlPattern(urlPatterns);
			if(im.getClassName()==null || im.getClassName().trim().equals("")){
				log.error("配置文件{}错误，{}元素中{}必须存在且唯一.", xmlPath,ElementName.INTERCEPTOR.value,ElementName.CLASS_NAME.value);
				throw new RuntimeException("配置文件错误.");
			}else if(im.getUrlPattern() == null || im.getUrlPattern().size()==0){
				log.error("配置文件{}错误，{}元素中{}至少应该有一个.", xmlPath,ElementName.INTERCEPTOR.value,ElementName.URL_PATTERN.value);
				throw new RuntimeException("配置文件错误.");
			}else if(interceptorMappings.contains(im)){
				log.error("配置文件{}错误，{}元素中{}必须唯一.", xmlPath,ElementName.INTERCEPTOR.value,ElementName.CLASS_NAME.value);
				throw new RuntimeException("配置文件错误.");
			}else{
				interceptorMappings.add(im);
			}
		}
		
	}
	
	public static URLMapping getURLMappingByCondition(String condition, URLMapping.Type type){
		Map<String, URLMapping> urlMappings = urlConfig.getUrlMappings();
		if(urlMappings != null && condition !=null && type != null){
			for(URLMapping s : urlMappings.values()){
				if(type.equals(URLMapping.Type.URI)){
					if(s.getUri()!=null && s.getUri().equals(condition)){
						return s;
					}
				}else if(type.equals(URLMapping.Type.CLASSNAME)){
					if(s.getClassName()!=null && s.getClassName().equals(condition)){
						return s;
					}
				}else if(type.equals(URLMapping.Type.METHODNAME)){
					if(s.getMethodName()!=null && s.getMethodName().equals(condition)){
						return s;
					}
				}else if(type.equals(URLMapping.Type.ACTION)){
					if(s.getAction()!=null && s.getAction().equals(condition)){
						return s;
					}
				}else{
					//do nothing
				}
			}
		}
		return null;
	}

	public static Set<String> getModelConvertPropPaths() {
		return urlConfig.getModelConvertPropPaths();
	}

	public static Set<InterceptorMapping> getInterceptorMappings(){
		return urlConfig.getInterceptorMappings();
	}
	
	
}
