package geocodeengine.data.SqlServer;

import java.beans.PropertyVetoException;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionManager {
	
	private static ComboPooledDataSource cpds;
	
	public static void InitConnectPool(){
		
		cpds = new ComboPooledDataSource();
		
		try {
			
			cpds.setDriverClass( "com.microsoft.sqlserver.jdbc.SQLServerDriver" );  
			cpds.setJdbcUrl( "jdbc:sqlserver://us194nbo5syzzl1\\sqlexpress:1433;DatabaseName=GeocodeEngine" );
			cpds.setUser("DATAOWNER");                                  
			cpds.setPassword("p455w0rd");  
			cpds.setMaxStatements( 180 );   
			
		} catch (PropertyVetoException e) {

			e.printStackTrace();
		}  
	}
	
	public static ComboPooledDataSource GetComboPooledDataSource(){
		
		//Initialize ComboPooledDataSource
		if (cpds == null) {
			InitConnectPool();
		}
		
		//Return ComboPooledDataSource
		return cpds;
	
	}
	
}
