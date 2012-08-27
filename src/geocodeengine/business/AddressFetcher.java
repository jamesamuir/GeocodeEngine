package geocodeengine.business;

import geocodeengine.data.Models.AddressModel;
import geocodeengine.data.SqlServer.DataContext;

import java.util.List;

public class AddressFetcher {

	public List<AddressModel> GetAddressList(int startIndex, int size){
		
		DataContext context = new DataContext();
		return context.GetAddressList(startIndex, size);
		
	}
	
}
