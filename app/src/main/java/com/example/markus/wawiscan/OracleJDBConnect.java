package com.example.markus.wawiscan;

import java.sql.*;

public class OracleJDBConnect {

	public static void main(String[] argv) {
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = 
DriverManager.getConnection("jdbc:oracle:thin:@//oracle-srv.edvsz.hs-osnabrueck.de:1521/oraclestud","omaescher","omaescher");
			
			Statement st = con.createStatement();
			String sql = "select anr, bezeichnung, bestandsmenge from arti";
			ResultSet rs=st.executeQuery(sql);

			
			
			while(rs.next()){
				System.out.println(rs.getInt(1)+ " "+ rs.getString(2)+ " "+ rs.getString(3));

			}
			
			con.close();
		}catch(Exception e){
			System.out.println(e);
		}

	}

}