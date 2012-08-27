package geocodeengine.data.SqlServer;


import geocodeengine.data.Models.*;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class DataContext {

	public void dbConnect(String db_connect_string,
            String db_userid,
            String db_password)
	{
	      try {
	         Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	         Connection conn = DriverManager.getConnection(db_connect_string,
	                  db_userid, db_password);
	         System.out.println("connected");
	         Statement statement = conn.createStatement();
	         String queryString = "select * from sysobjects where type='u'";
	         ResultSet rs = statement.executeQuery(queryString);
	         while (rs.next()) {
	            System.out.println(rs.getString(1));
	         }
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	}
	
	public int GetTestCount(){
		
		 int result = 0;
		 Connection conn = null;
		 PreparedStatement st = null;
		 ResultSet rs = null;
		 try {
			  // ConnectionManager.GetComboPooledDataSource() is an instance of c3p0's ComboPooledDataSource
			  conn = ConnectionManager.GetComboPooledDataSource().getConnection();
			  
			  System.out.println("Connection exists? " + conn != null );
			  
			  st = conn.prepareStatement("SELECT COUNT(*) AS 'RECCOUNT' FROM Address");
			  
			  //st.setInt(1, sessionID);
			  
			  rs = st.executeQuery();
			  
			  if ( rs.next() ) {
					   result = rs.getInt("RECCOUNT");
				   
			  }
		 }
		 
		 catch (SQLException e) {
		  e.printStackTrace();
		 }
		 catch(Exception e){
			 e.printStackTrace();
		 }
		 finally {
		  if ( rs != null ) {
		   try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
		  }
		  if ( st != null ) {
		   try { st.close(); } catch (SQLException e) { e.printStackTrace(); }
		  }
		  if ( conn != null ) {
		   try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
		  }
		 }
		 return result;
	
	}
	
	private ResultSet ExecutePooledQuery(String query){
		
		 Connection conn = null;
		 PreparedStatement st = null;
		 ResultSet rs = null;
		 try {
			  // ConnectionManager.GetComboPooledDataSource() is an instance of c3p0's ComboPooledDataSource
			  conn = ConnectionManager.GetComboPooledDataSource().getConnection();
			  
			  
			  st = conn.prepareStatement(query);
			  
			  
			  rs = st.executeQuery();
			  
		 }
		 
		 catch (SQLException e) {
		  e.printStackTrace();
		 }
		 finally {
		  if ( rs != null ) {
		   try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
		  }
		  if ( st != null ) {
		   try { st.close(); } catch (SQLException e) { e.printStackTrace(); }
		  }
		  if ( conn != null ) {
		   try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
		  }
		 }
		 return rs;
	}
	
	
	
	
	public List<AddressModel> GetAddressList(int startIndex, int endIndex){
		
		//Init model
		List<AddressModel> model = new ArrayList<AddressModel>();
		
		//Get Data
		 Connection conn = null;
		 PreparedStatement st = null;
		 ResultSet rs = null;
		 try {
			  // ConnectionManager.GetComboPooledDataSource() is an instance of c3p0's ComboPooledDataSource
			  conn = ConnectionManager.GetComboPooledDataSource().getConnection();
			  
			  
		
	  
			  st = conn.prepareStatement("SELECT RowId, Id, Name, Address1, Address2, City, State, Zip, DML_TS FROM  [GeocodeEngine].[dbo].[Address] WHERE RowId >= " + startIndex + " AND RowId <= " + endIndex);  
			  rs = st.executeQuery();
			  
			  
			  //Add data to model
			  while ( rs.next() ) {
					   AddressModel temp = new AddressModel(
							   								UUID.fromString(rs.getString("Id")),
							   								rs.getString("Name"),
							   								rs.getString("Address1"),
							   								rs.getString("Address2"),
							   								rs.getString("City"),
							   								rs.getString("State"),
							   								rs.getString("Zip")
							   								);
					   model.add(temp);
				   
			  }
		 }
		 
		 catch (SQLException e) {
		  e.printStackTrace();
		 }
		 finally {
		  if ( rs != null ) {
		   try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
		  }
		  if ( st != null ) {
		   try { st.close(); } catch (SQLException e) { e.printStackTrace(); }
		  }
		  if ( conn != null ) {
		   try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
		  }
		 }
	
		
		return model;
	}
	
	public void SaveResult(ResultModel model){
		
		
		//Declare variables
		 Connection conn = null;
		 PreparedStatement st = null;
		 Date currentDate = new Date();
		 
		 try {
			  // ConnectionManager.GetComboPooledDataSource() is an instance of c3p0's ComboPooledDataSource
			  conn = ConnectionManager.GetComboPooledDataSource().getConnection();
			  
			  StringBuilder statement = new StringBuilder();
			  statement.append("INSERT INTO GeocodeEngine.dbo.Result( Id, Latitude, Longitude, Status, Score) VALUES ");
			  statement.append("(");
			  statement.append("CONVERT(uniqueidentifier, '" + model.GetId() + "'),");  //This converts our UUID into a SQL GUID or UniqueIdentifier
			  statement.append(model.GetLatitude() + ",");
			  statement.append(model.GetLongitude() + ",");
			  statement.append("'OK',");
			  statement.append(100);
			  statement.append(")");
			  
					  
			  st = conn.prepareStatement(statement.toString());  
			  st.execute();
			  
		 }
		 
		 catch (SQLException e) {
		  e.printStackTrace();
		 }
		 finally {
		  
		  if ( st != null ) {
		   try { st.close(); } catch (SQLException e) { e.printStackTrace(); }
		  }
		  if ( conn != null ) {
		   try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
		  }
		 }
	}
	
	
}
