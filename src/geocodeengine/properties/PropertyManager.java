package geocodeengine.properties;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;


public class PropertyManager {

	
	//PROPERTIES
	private String enableGeocode;
	private String bingAPIKey;
	private int addressChunkSize = 0;
	
	
	//KEY STRINGS
	private final String ENABLEGEOCODE = "ENABLEGEOCODE";
	private final String BINGAPIKEY = "BINGAPIKEY";
	private final String ADDRESSCHUNKSIZE = "ADDRESSCHUNKSIZE";
	
	
	
	
	public String GetValue(String key){
		
		try {
			//load a properties file
			Properties prop = new Properties();
    		prop.load(this.getClass().getResourceAsStream("jamie.properties"));
    		String value = prop.getProperty(key);
 
			return value;
			
		} catch (IOException e) {
			
			System.out.println("testthreading.properties.PropertyManager - IO Exception");
			return new String();
		}
	}
	
	public String GetEnableGeocode(){
		
		if (enableGeocode == null){
			String value = GetValue(ENABLEGEOCODE);
			enableGeocode = value;
		}
		
		return enableGeocode;
	
	}
	
	
	public String GetBingAPIKey(){
		
		if (bingAPIKey == null){
			
			String value = GetValue(BINGAPIKEY);
			bingAPIKey = value;
		}
		
		return bingAPIKey;
	
	}
	
	public int GetAddressChunkSize(){
		
		if (addressChunkSize == 0){
			
			String value = GetValue(ADDRESSCHUNKSIZE);
			addressChunkSize = Integer.parseInt(value);
		}
		
		return addressChunkSize;
	
	}
	
	
	
}
