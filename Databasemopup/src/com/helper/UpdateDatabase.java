package com.helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.helper.DbConnection;
import com.model.Domain;

public class UpdateDatabase {

	static final Logger logger = Logger.getLogger(UpdateDatabase.class);

	public static int updateDb(List<Domain> listOfKeys) {
		BasicConfigurator.configure();
		Connection con = DbConnection.getConnection();
		int i = 0;
		 int rowsAffected=0;
		 int size;
		String table="customers";
		String column="postalcode";
		String updateSql = "update " + table + " set " + column + "=? where " + column + "=?";
		try {
			PreparedStatement stmt = con.prepareStatement(updateSql);
			/*stmt.setString(1, "27764110");//27764111 27764110
		    stmt.setString(2, "27764110");
		    stmt.addBatch();
		    stmt.setString(1, "67237479");// 67237479 67237478
		    stmt.setString(2, "67237478");
		    stmt.addBatch();
		    stmt.setString(1, "52638751");// 67237479 67237478
		    stmt.setString(2, "52638752");
		    stmt.addBatch();
		    */

			for(Domain keys : listOfKeys) {
		        stmt.setString(1, keys.getNewKey());
		        stmt.setString(2, keys.getOldKey());
		       Reporter.log(keys.getOldKey() + " row updated with " + keys.getNewKey());
				Reporter.log("------------------------------------------------------------------");
		        stmt.addBatch();
		    }

		    int[] affectedRecords = stmt.executeBatch();
		   
		   for (size=0;size<affectedRecords.length;size++){
			   rowsAffected=affectedRecords[size]+rowsAffected;
		   }
		   System.out.println("Total No. of queries excecuted in the batch : "+size);
		   System.out.println("Total No. of rows affected : "+rowsAffected);
		   Reporter.log("Total No. of queries excecuted in batch "+size+" and No. of rows affected is "+rowsAffected);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

}
