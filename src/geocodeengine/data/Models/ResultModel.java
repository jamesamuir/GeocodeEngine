package geocodeengine.data.Models;
import java.util.UUID;

public class ResultModel {
	
	
	private UUID Id;
	private Float latitude;
	private Float longitude;
	private String formattedAddress;
	private String status;
	private int score;
	
	
	public ResultModel(){}
			
	public ResultModel(UUID Id, float latitude, float longitude, String status){
		
		this.Id = Id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.status = status;
		
	}
	
	
	public UUID GetId(){ return this.Id; }
	public float GetLatitude(){ return this.latitude; }
	public float GetLongitude(){ return this.longitude; }
	public String GetFormattedAddress(){ return this.formattedAddress; }
	public String GetStatus(){ return this.status; }
	public int GetScore(){ return this.score; }
	
	public void SetId(UUID value){ this.Id = value;}
	public void SetLatitude(float value){ this.latitude = value;}
	public void SetLongitude(float value){ this.longitude = value;}
	public void SetFormattedAddress(String value){ this.formattedAddress = value; }
	public void SetStatus(String value){ this.status = value;}
	public void SetScore(int value){ this.score = value;}
	
	
	

}
