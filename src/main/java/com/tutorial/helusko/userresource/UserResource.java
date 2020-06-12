package com.tutorial.helusko.userresource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.database.DBConnection;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tutorial.helusko.pojos.User;

@Path("user")
public class UserResource {
	@GET
	@Path("getusers")
	@Produces(MediaType.APPLICATION_XML)
	public List<User> getUsers(){
		List<User> userList=getUsersList();
		
		return userList;
	}
	
	@POST
	@Path("newuser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String newUser(String usercredentials) {
		JsonElement jsonElement=JsonParser.parseString(usercredentials);
		String response="";
		if(jsonElement.isJsonObject()) {
			JsonObject jsonObject=jsonElement.getAsJsonObject();
			String name=jsonObject.get("name").getAsString();
			String id=jsonObject.get("id").getAsString();
			Connection connection=DBConnection.getConnection();
			try {
				PreparedStatement preparedStatement=connection.prepareStatement("INSERT INTO user(username,usernumber) VALUES (?,?)");
				preparedStatement.setString(1,name);
				preparedStatement.setString(2,id);
				int rows=preparedStatement.executeUpdate();
				if(rows > 0) {
					response="{"
							+ "\n"+"resposne : "+"USER REGISTERED"+"\n"
							+"}";
				}else {
					response="{"
							+ "\n"+"resposne : "+"USER NOT REGISTERED"+"\n"
							+"}";
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				if(connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		}
		return response;
	}
	private List<User> getUsersList(){
		Connection connection=DBConnection.getConnection();
		List<User> userList=new ArrayList<>();
		try {
			PreparedStatement preparedStatement=connection.prepareStatement("SELECT username,usernumber FROM user");
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				String name=resultSet.getString(1);
				String id=resultSet.getString(2);
				User user=new User(name,Integer.parseInt(id));
				userList.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return userList;
	}
}
