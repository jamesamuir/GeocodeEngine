package geocodeengine.business;

import geocodeengine.data.Models.AddressModel;
import geocodeengine.data.Models.ResultModel;
import geocodeengine.properties.PropertyManager;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;





public class BingGeocodeEngine {
	
public ResultModel GeocodeAddress(AddressModel addressmodel){
		
		//Init new ResultModel
		ResultModel model = new ResultModel();
		model.SetId(addressmodel.GetAddressId());
		model.SetLatitude(Float.NaN);
		model.SetLongitude(Float.NaN);
			
		//Get API Key
		PropertyManager manager = new PropertyManager();
		
		String APIKey = manager.GetBingAPIKey();
		
		// URL prefix to the geocoder
		StringBuilder sb = new StringBuilder();
		
		sb.append("/" + addressmodel.GetState());
		sb.append("/" + addressmodel.GetCity());
		sb.append("/" + addressmodel.GetZip());
		sb.append("/" + addressmodel.GetAddress1());
		
		
		
		
		JSONObject resultJSON;
		try{
		
			// prepare a URL to the geocoder
			URI uri = new URI(
				    "http", 
				    "dev.virtualearth.net/REST/v1/Locations/US", 
				    sb.toString(),
				    null);
		    URL url = new URL(uri.toString() + "?key=" + APIKey);
			
			// prepare an HTTP connection to the geocoder
		    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		    
		    Document geocoderResultDocument = null;
		    try {
			    // open the connection and get results as InputSource.
		    	resultJSON = new JSONObject(conn.getInputStream());

			      
			      
		    } finally {
		    	conn.disconnect();
		    }
	
		    
			try {
				
				JSONArray resourceSetArray = resultJSON.getJSONArray("resourceSets");
				JSONObject resourceJSON = new JSONObject(resourceSetArray.get(0).toString());
				JSONArray resourceArray = resourceJSON.getJSONArray("resources");
				JSONObject locationJSON = new JSONObject(resourceArray.get(0).toString());
				
				//Get the confidence
				String confidence = locationJSON.getString("confidence");
				if (confidence == "HIGH")
					model.SetScore(100);
				else
					model.SetScore(50);
				
				//Get the formatted address
				 String formattedAddress = locationJSON.getJSONObject("address").get("formattedAddress").toString();
				 model.SetFormattedAddress(formattedAddress);
				
				//Get the coordinates
				JSONArray pointsArray = locationJSON.getJSONArray("geocodePoints");
				JSONObject pointsJSON = new JSONObject(pointsArray.get(0).toString());
				JSONArray coordsArray = pointsJSON.getJSONArray("coordinates");
				double lat =  coordsArray.getDouble(0);
				double lng =  coordsArray.getDouble(1);
				
				
				
				
				
				
				System.out.println("this is json" + resourceArray.toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    // prepare XPath
		    XPath xpath = XPathFactory.newInstance().newXPath();
	
		    // extract the result
		    NodeList resultNodeList = null;
	
		    // a) obtain the formatted_address field for every result
		    resultNodeList = (NodeList) xpath.evaluate("/GeocodeResponse/result/formatted_address", geocoderResultDocument, XPathConstants.NODESET);
		    for(int i=0; i<resultNodeList.getLength(); ++i) {
		    	System.out.println(resultNodeList.item(i).getTextContent());
		    }
	
		    // b) extract the locality for the first result
		    resultNodeList = (NodeList) xpath.evaluate("/GeocodeResponse/result[1]/address_component[type/text()='locality']/long_name", geocoderResultDocument, XPathConstants.NODESET);
		    for(int i=0; i<resultNodeList.getLength(); ++i) {
		    	System.out.println(resultNodeList.item(i).getTextContent());
		    }
	
		    // c) extract the coordinates of the first result
		    resultNodeList = (NodeList) xpath.evaluate("/GeocodeResponse/result[1]/geometry/location/*", geocoderResultDocument, XPathConstants.NODESET);
		    float lat = Float.NaN;
		    float lng = Float.NaN;
		    for(int i=0; i<resultNodeList.getLength(); ++i) {
		    	Node node = resultNodeList.item(i);
		    	if("lat".equals(node.getNodeName())) lat = Float.parseFloat(node.getTextContent());
		    	if("lng".equals(node.getNodeName())) lng = Float.parseFloat(node.getTextContent());
		    }
		    
		    System.out.println("lat/lng=" + lat + "," + lng);
		    
		    // c) extract the coordinates of the first result
//		    resultNodeList = (NodeList) xpath.evaluate("/GeocodeResponse/result[1]/address_component[type/text() = 'administrative_area_level_1']/country[short_name/text() = 'US']/*", geocoderResultDocument, XPathConstants.NODESET);
//		    float lat = Float.NaN;
//		    float lng = Float.NaN;
//		    for(int i=0; i<resultNodeList.getLength(); ++i) {
//		      Node node = resultNodeList.item(i);
//		      if("lat".equals(node.getNodeName())) lat = Float.parseFloat(node.getTextContent());
//		      if("lng".equals(node.getNodeName())) lng = Float.parseFloat(node.getTextContent());
//		    }
	    
	    
		}catch(Exception e){
			
			System.out.println("Exception in BingGeocode  - " + e.getMessage());
			
		}
		  
		return model;
	}



}
