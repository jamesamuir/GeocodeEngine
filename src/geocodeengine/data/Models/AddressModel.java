package geocodeengine.data.Models;

import java.util.UUID;

public class AddressModel {

	
	//PROPERTIES
	private UUID addressId;
	private String name;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String zip;
	
	//CONSTRUCTORS
	public AddressModel(){
		
	
	}
	
	public AddressModel(UUID addressId, String name, String address1, String address2, String city, String state, String zip ){
		this.addressId = addressId;
		this.name = name;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.state = state;
		this.zip = zip;
		
	}
	
	//GETTERS
	public UUID GetAddressId(){ return this.addressId; }
	public String GetName(){ return this.name; }
	public String GetAddress1(){ return this.address1; }
	public String GetAddress2(){ return this.address2; }
	public String GetCity(){ return this.city; }
	public String GetState(){ return this.state; }
	public String GetZip(){ return this.zip; }
		
	//SETTERS
	public void SetAddressId(UUID value){ this.addressId = value; }
	public void SetName(String value){ this.name = value; }
	public void SetAddress1(String value){ this.address1 = value; }
	public void SetAddress2(String value){ this.address2 = value; }
	public void SetCity(String value){ this.city = value; }
	public void SetState(String value){ this.state = value; }
	public void SetZip(String value){ this.zip = value; }
	
	
	
}
