package geocodeengine.business;

import geocodeengine.data.Models.*;
import geocodeengine.data.SqlServer.*;
import geocodeengine.properties.PropertyManager;

public class GeocodeTask implements Runnable {
	
	   Thread t;
	   
	   //CONSTRUCTOR
	   public GeocodeTask() {
		   
	      // Create a new thread
	      t = new Thread(this, "Geocode Thread");
	      // Start the thread
	      t.start(); 
	   }

	   //PROPERTIES
		public AddressModel model;
		public void SetAddressModel(AddressModel model){
			this.model = model;
		}
	

		
		@Override
		public void run() {

		    System.out.println("Child thread: " + t);
		    System.out.println("Thread : " + model.GetName());
		    
		 
				//Try google api first. Can get overloaded with requests so might return null
				GoogleGeocodeEngine googleEngine = new GoogleGeocodeEngine();
				ResultModel result = googleEngine.GeocodeAddress(model);
				
				Float lat = result.GetLatitude();
				Float lng = result.GetLongitude();
				
				
				System.out.println("!!!!!!!!!!!!!" + (lat.equals(Float.NaN) ) );
				
				if ( !lat.equals(Float.NaN) | !lng.equals(Float.NaN) ){
					
					if (result.GetFormattedAddress() == null){
						
						System.out.print("fda");
					}
					
					//Save result to database if we got a result back
					DataContext context = new DataContext();
					context.SaveResult(result);
					
				}else{
					
					BingGeocodeEngine mapquestEngine = new BingGeocodeEngine();
					result = mapquestEngine.GeocodeAddress(model);
					
					
				
					
				
				}
				
				
		    
			
		}
}
