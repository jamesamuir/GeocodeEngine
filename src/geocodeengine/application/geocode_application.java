 
package geocodeengine.application;



import geocodeengine.business.*;
import geocodeengine.data.Models.*;
import geocodeengine.properties.PropertyManager;

import java.util.*;



public class geocode_application {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		System.out.println("--------------- Starting Geocode Operation ---------------");
		
		//init the address list
		List<AddressModel> addresses;
		
		
		//Set up the paging parameters
		PropertyManager manager = new PropertyManager();
		int chunksize = manager.GetAddressChunkSize();
		
		int startindex = 0;
		int endindex = startindex + chunksize;
		
		
		//Run through addresses until empty
		do{
			
			
		
			System.out.println("--------------- Geocoding records " + startindex + " to " + endindex + "------------------------");
		
			AddressFetcher addressfetcher = new AddressFetcher();
			addresses = addressfetcher.GetAddressList(startindex, endindex);
			
			//Geocode Addresses
			ProcessAddresses(addresses);
			
			//Increment the indexes
			startindex = startindex + chunksize + 1;
			endindex = startindex + chunksize;
			
			System.out.println("--------------- startindex : " + startindex + "  endindex : " + endindex + " ---------------");
		
		
		}while(!addresses.isEmpty());
		
		
		System.out.println("--------------- Ending Geocode Operation ---------------");
		

	}
	
	
	public static void ProcessAddresses(List<AddressModel> addresses){
		
		int i = 0;
		for(AddressModel address : addresses){
			
			
				//Debug test
				System.out.println(address.GetName() + " : " + i);
				System.out.println("Child Thread: " + i);
				
				
				//Run the thread
				GeocodeTask geocodeTask = new GeocodeTask();
				geocodeTask.SetAddressModel(address);
				geocodeTask.run();
				
				
				//Increment counter
				i++;
				
		}
	}
	
	
	
	
	
	

}
