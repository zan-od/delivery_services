package zan.delivery_services;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class HTTPConnector {
	private HttpURLConnection con;
	
	public int send(String url, Object data) throws Exception {
		URL obj = new URL(url);
		con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		//con.setRequestProperty("User-Agent", USER_AGENT);
		
		if (data != null){
			con.setRequestMethod("POST");
			
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes((String) data);
			wr.flush();
			wr.close();
		}
		
		int responseCode = con.getResponseCode();
		System.out.print(responseCode);
		
		return responseCode;
	}
	
	public boolean isConnected(){
		return (con != null);
	}
	
	public InputStream getBody(){
		if (!isConnected()) return null;
		
		try {
			return con.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public InputStream copyStream(InputStream from)
		      throws IOException {
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len;
		while ((len = from.read(buffer)) > -1 ) {
		    baos.write(buffer, 0, len);
		}
		baos.flush();
		
		return new ByteArrayInputStream(baos.toByteArray());
	}
	
	public Document getXML(InputStream is){
		Document doc = null;
		
		try {
			InputStream copy = copyStream(is);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(copy);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		return doc;
	}
	
	public Document getXML(File file){
		Document doc = null;
		
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(file);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		return doc;
	}
	
	public Document getXML(String filename){
		Document doc = null;
		
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(filename);
			System.out.println(doc.getDocumentElement().toString());
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		return doc;
	}
	
	public String getString(InputStream is){
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(is));
		
		String inputLine;
		StringBuffer buffer = new StringBuffer();

		try {
			while ((inputLine = in.readLine()) != null) {
				buffer.append(inputLine);
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return buffer.toString();
	}
	
	public void toFile(InputStream is, String filename){
		FileOutputStream os = null;

	    try {
	        os = new FileOutputStream(filename);
	        
	        int read = 0;
	        byte[] bytes = new byte[1024];

	        while ((read = is.read(bytes)) != -1) {
	            os.write(bytes, 0, read);
	        }
	    } catch (Exception e) {
			e.printStackTrace();
	    } finally {
	        if (os != null) {
	            try {
	                os.close();
	            } catch (IOException e) {
	    			e.printStackTrace();
	            }
	        }
	    }
	}
}
