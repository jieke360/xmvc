package xmvc.core;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class URLConvert {
	public static final String SUFFIX = ".do";
	public static final String SERVLET_PATH="javax.servlet.include.servlet_path";
	public static final String ROOT_NAME="url-config";
	public static final String URLMAPPING_NAME="URLMapping";
	public static final String IMPORT="import";
	public static final String URI_NAME="uri";
	public static final String CLASSNAME_NAME="className";
	public static final String METHODNAME_NAME="methodName";
	public static final String ACTION_NAME="action";
	private static List<URLMapping> urlMappings = null;
	public static final Logger log = LoggerFactory.getLogger(XServlet.class);
	public static void load(String urlConfigPath) throws ParserConfigurationException, SAXException, IOException {
		
		if(urlConfigPath == null || urlConfigPath.trim().equals("")) return;
		
		log.info("初始化URL配置文件:{}",urlConfigPath.trim());
		
		if(urlMappings == null){
			urlMappings = new ArrayList<URLMapping>();
		}
		
		InputStream is = URLConvert.class.getClassLoader().getResourceAsStream(urlConfigPath.trim());

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.parse(is);
		NodeList roots = document.getChildNodes();
		if(roots.getLength() == 1){
			Node root = roots.item(0);
			if(ROOT_NAME.equalsIgnoreCase(root.getNodeName())){
				NodeList turlMappings = root.getChildNodes();
				for (int j = 0; j < turlMappings.getLength(); j++) {
					Node urlMapping = turlMappings.item(j);
					if(URLMAPPING_NAME.equalsIgnoreCase(urlMapping.getNodeName())){
						
						URLMapping urlMaping = new URLMapping();
						
						NodeList urlMappingInfo = urlMapping.getChildNodes();
						for (int k = 0; k < urlMappingInfo.getLength(); k++) {
							Node node = urlMappingInfo.item(k);
							if(URI_NAME.equalsIgnoreCase(node.getNodeName())){
								String uri = node.getTextContent();
								if(uri != null){
									uri = uri.trim();
								}
								urlMaping.setUri(uri);
							}else if(CLASSNAME_NAME.equalsIgnoreCase(node.getNodeName())){
								String className = node.getTextContent();
								if(className != null){
									className = className.trim();
								}
								urlMaping.setClassName(className);
							}else if(METHODNAME_NAME.equalsIgnoreCase(node.getNodeName())){
								String methodName = node.getTextContent();
								if(methodName != null){
									methodName = methodName.trim();
								}
								urlMaping.setMethodName(methodName);
							}else if(ACTION_NAME.equalsIgnoreCase(node.getNodeName())){
								String action = node.getTextContent();
								if(action != null){
									action = action.trim();
								}
								urlMaping.setAction(action);
							}
						}
						urlMappings.add(urlMaping);
					}else if(IMPORT.equalsIgnoreCase(urlMapping.getNodeName())){
						load(urlMapping.getTextContent());
					}
				}
			}
		}else{
			throw new RuntimeException("XML格式错误,没有根元素"+ROOT_NAME);
		}
	
		if (is != null) {
			is.close();
		}
	}
	
	public static URLMapping getURLMappingByCondition(String condition, URLMapping.Type type){
		if(urlMappings != null && condition !=null && type != null){
			for(URLMapping s : urlMappings){
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
}
