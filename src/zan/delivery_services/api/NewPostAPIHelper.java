package zan.delivery_services.api;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import zan.delivery_services.HTTPConnector;

public class NewPostAPIHelper {
	private String apiKey;
	
	public class WarehouseData{
		private String ref;
		private String name;
	}
	
	public class CityData{
		private String ref;
		private String name;
		
		public String getRef() {
			return ref;
		}
		
		public void setRef(String ref) {
			this.ref = ref;
		}
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
	}
	
	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public List<WarehouseData> getWarehouses(){
		List<WarehouseData> warehouses = null;
		List<CityData> cities = null;
		
		try { 
			DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
	        DocumentBuilder build = dFact.newDocumentBuilder();
	        Document doc = build.newDocument();
	        
	        Element root = doc.createElement("file");
	        doc.appendChild(root);
	        
	        Element apiKeyEl = doc.createElement("apiKey");
	        apiKeyEl.setTextContent(getApiKey());
	        root.appendChild(apiKeyEl);
	        
	        Element modelName = doc.createElement("modelName");
	        modelName.setTextContent("Address");
	        root.appendChild(modelName);
	        
	        Element calledMethod = doc.createElement("calledMethod");
	        calledMethod.setTextContent("getWarehouses");
	        root.appendChild(calledMethod);
	        
	        TransformerFactory tFact = TransformerFactory.newInstance();
            Transformer trans = tFact.newTransformer();

            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            DOMSource source = new DOMSource(doc);
            trans.transform(source, result);
            
            System.out.println(writer.toString());
            
            HTTPConnector con = new HTTPConnector();
            try {
				con.send("http://api.novaposhta.ua/v2.0/xml/", writer.toString());
				Document responseDoc = con.getXML(con.getBody());
				//Document responseDoc = con.getXML(new File("D:\\tmp\\1.xml"));
				System.out.println(responseDoc.toString());
				NodeList children = responseDoc.getChildNodes();
				for (int i = 0; i < children.getLength(); i++) {
				     Node node = children.item(i);
				     System.out.println(node.getNodeName());
				}
				
				warehouses = new ArrayList<>();
				cities = new ArrayList<>();
				processWarehouses(responseDoc, cities);
				
				//System.out.println(con.getString(con.getBody()));
				//con.toFile(con.getBody(), "C:\\tmp\\1c_tmp\\1.xml");
			} catch (Exception e) {
				e.printStackTrace();
			}
	        
		} catch (TransformerException ex) {
            System.out.println("Error outputting document");
        } catch (ParserConfigurationException ex) {
            System.out.println("Error building document");
        }
		
		return warehouses;
	}
	
	public List<CityData> getCities(){
		List<CityData> cities = null;
		
		try { 
			DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
	        DocumentBuilder build = dFact.newDocumentBuilder();
	        Document doc = build.newDocument();
	        
	        Element root = doc.createElement("file");
	        doc.appendChild(root);
	        
	        Element apiKeyEl = doc.createElement("apiKey");
	        apiKeyEl.setTextContent(getApiKey());
	        root.appendChild(apiKeyEl);
	        
	        Element modelName = doc.createElement("modelName");
	        modelName.setTextContent("Address");
	        root.appendChild(modelName);
	        
	        Element calledMethod = doc.createElement("calledMethod");
	        calledMethod.setTextContent("getWarehouses");
	        root.appendChild(calledMethod);
	        
	        TransformerFactory tFact = TransformerFactory.newInstance();
            Transformer trans = tFact.newTransformer();

            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            DOMSource source = new DOMSource(doc);
            trans.transform(source, result);
            
            System.out.println(writer.toString());
            
            HTTPConnector con = new HTTPConnector();
            try {
				con.send("http://api.novaposhta.ua/v2.0/xml/", writer.toString());
				Document responseDoc = con.getXML(con.getBody());
				//Document responseDoc = con.getXML(new File("D:\\tmp\\1.xml"));
				System.out.println(responseDoc.toString());
				NodeList children = responseDoc.getChildNodes();
				for (int i = 0; i < children.getLength(); i++) {
				     Node node = children.item(i);
				     System.out.println(node.getNodeName());
				}
				
				cities = new ArrayList<>();
				processWarehouses(responseDoc, cities);
				
				//System.out.println(con.getString(con.getBody()));
				con.toFile(con.getBody(), "d:\\tmp\\new_post.xml");
			} catch (Exception e) {
				e.printStackTrace();
			}
	        
		} catch (TransformerException ex) {
            System.out.println("Error outputting document");
        } catch (ParserConfigurationException ex) {
            System.out.println("Error building document");
        }
		
		return cities;
	}
	
	public void processWarehouses(Document doc, List<CityData> cities){
		NodeList dataList = doc.getElementsByTagName("data");
		if (dataList.getLength() == 0) {
			return;
		}
		
		Node data = dataList.item(0);
		
		NodeList items = data.getChildNodes();
		int warehousesCount = items.getLength();
		for (int i = 0; i < warehousesCount; i++) {
		     Node item = items.item(i);
		     cities.add(processWarehouse(item));
		}
	}
	
	public CityData processWarehouse(Node item) {
		CityData data = new CityData();
		
		NodeList properties = item.getChildNodes();
		for (int i = 0; i < properties.getLength(); i++) {
			Node property = properties.item(i);

			switch (property.getNodeName()) {
			case "CityRef":
				data.setRef(property.getFirstChild().getNodeValue());
				break;
			case "CityDescription":
				data.setName(property.getFirstChild().getNodeValue());
				break;
			}
		}
		
		return data;
	}	
	
}
