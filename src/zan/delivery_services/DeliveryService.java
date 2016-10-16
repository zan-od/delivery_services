package zan.delivery_services;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.ibatis.session.SqlSession;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import zan.delivery_services.db.Db;

public class DeliveryService implements DeliveryServiceAPI{
	private Integer id;
	private String code;
	private String name;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void insert() throws IOException{
		SqlSession session = Db.getSession();
		if (session == null) return;
		session.insert("deliveryServiceMapper.insertDeliveryService", this);
		session.commit();
	}

	public static List<DeliveryService> getAll() throws IOException{
		List<DeliveryService> items = Arrays.asList();
		
		SqlSession session = Db.getSession();
		if (session == null) return items;
		items = session.selectList("deliveryServiceMapper.selectAll");
		
		return items;
	}
	
	public void connect(){
		final String key = "8f221f257b783dfb88392afdf33e722a";
		
		try { 
			DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
	        DocumentBuilder build = dFact.newDocumentBuilder();
	        Document doc = build.newDocument();
	        
	        Element root = doc.createElement("file");
	        doc.appendChild(root);
	        
	        Element apiKey = doc.createElement("apiKey");
	        apiKey.setTextContent(key);
	        root.appendChild(apiKey);
	        
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
            
            setId(1); //TODO init service
            
            HTTPConnector con = new HTTPConnector();
            try {
				//con.send("http://api.novaposhta.ua/v2.0/xml/", writer.toString());
				//Document responseDoc = con.getXML(con.getBody());
				Document responseDoc = con.getXML(new File("D:\\tmp\\1.xml"));
				System.out.println(responseDoc.toString());
				NodeList children = responseDoc.getChildNodes();
				for (int i = 0; i < children.getLength(); i++) {
				     Node node = children.item(i);
				     System.out.println(node.getNodeName());
				}
				
				processWarehouses(responseDoc);
				
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
	}
	
	public void processWarehouses(Document doc){
		NodeList dataList = doc.getElementsByTagName("data");
		if (dataList.getLength() == 0) {
			return;
		}
		
		Node data = dataList.item(0);
		
		NodeList items = data.getChildNodes();
		for (int i = 0; i < items.getLength(); i++) {
		     Node item = items.item(i);
		     processWarehouse(item);
		}
	}
	
	public void processWarehouse(Node item) {
		City city = new City();
		city.setService(this);
		NodeList properties = item.getChildNodes();
		for (int i = 0; i < properties.getLength(); i++) {
			Node property = properties.item(i);

			switch (property.getNodeName()) {
			case "CityRef":
				city.setRef(property.getFirstChild().getNodeValue());
				break;
			case "CityDescription":
				city.setName(property.getFirstChild().getNodeValue());
				break;
			}

		}
		
		City savedCity = new City();
		try {
			savedCity = City.findByRef(city.getRef());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		city.setId(savedCity.getId());
		try {
			city.save();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	@Override
	public List<City> getSities() {
		// TODO Auto-generated method stub
		connect();
		return null;
	}
	
	@Override
	public void getOffices() {
		// TODO Auto-generated method stub
		
	}

}
