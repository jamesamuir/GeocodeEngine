
package geocodeengine.business;
import geocodeengine.data.Models.*;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.xml.sax.SAXException;


public class GoogleGeocodeEngine {


	public ResultModel GeocodeAddress(AddressModel addressmodel){
		
		ResultModel model = new ResultModel();
		model.SetId(addressmodel.GetAddressId());
		
		// URL prefix to the geocoder
		final String GEOCODER_REQUEST_PREFIX_FOR_XML = "http://maps.google.com/maps/api/geocode/xml";
		
		//Construct address string
		StringBuilder sb = new StringBuilder();
		sb.append(addressmodel.GetAddress1());
		if (addressmodel.GetAddress2() != null)
			sb.append(", " + addressmodel.GetAddress2());
		sb.append(", " + addressmodel.GetCity());
		sb.append(", " + addressmodel.GetState());
		sb.append(" " + addressmodel.GetZip());
		
		try{
		
			// prepare a URL to the geocoder
		    URL url = new URL(GEOCODER_REQUEST_PREFIX_FOR_XML + "?address=" + URLEncoder.encode(sb.toString(), "UTF-8") + "&sensor=false");
			
			// prepare an HTTP connection to the geocoder
		    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		    
		    Document geocoderResultDocument = null;
		    try {
			      // open the connection and get results as InputSource.
			      conn.connect();
			      InputSource geocoderResultInputSource = new InputSource(conn.getInputStream());
		
			      // read result and parse into XML Document
			      geocoderResultDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(geocoderResultInputSource);
		    } finally {
		    	conn.disconnect();
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
		    

		    	
		    //Add values to the model
		    model.SetId(addressmodel.GetAddressId());
		    System.out.println("$$$$$$$$$$$$$$$$$$$$$$$" + lat);
		    model.SetLatitude(lat);
		    model.SetLongitude(lng);
		   
		
			
			
		    
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
			
			
		}
		  
	    //Return the model
		return model;
		
	}
	
}
