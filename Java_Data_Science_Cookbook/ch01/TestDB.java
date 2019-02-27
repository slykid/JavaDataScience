package com.kilhyunkim.DS;

import java.sql.*;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class TestDB {

	public static void main(String[] args) 
	{
		TestDB test = new TestDB();
		test.readTable("root", "YourPassword", "localhost");
	}
	
	public void readTable(String user, String password, String server)
	{
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setUser(user);
		dataSource.setPassword(password);
		dataSource.setServerName(server);
		
		try
		{
			Connection conn = dataSource.getConnection();
			Statement stmt = (Statement) conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM data_science.books");
			while(rs.next())
			{
				int id = rs.getInt("id");
				String book = rs.getString("book_name");
				String author = rs.getString("author_name");
				Date dateCreated = rs.getDate("date_created");
				System.out.format("%s, %s, %s, %s \n", id, book, author, dateCreated);
			}
			rs.close();
			stmt.close();
			conn.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}

}
