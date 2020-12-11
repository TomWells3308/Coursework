package controllers;

import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import server.Main;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLOutput;

@Path("user/")
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.APPLICATION_JSON)

public class Users {
    @GET
    @Path("get/{UserID}")
    public String userGet(@PathParam("UserID") Integer UserID) {
        System.out.println("Invoked Users.userGet() with UserID " + UserID);
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT UserID, Username, Password, Email, StartDate, Activity, Token FROM Users WHERE UserID = ?");
            ps.setInt(1, UserID);
            ResultSet results = ps.executeQuery();
            JSONObject response = new JSONObject();
            if (results.next()) {
                response.put("UserID", results.getInt(1));
                response.put("Username", results.getString(2));
                response.put("Password", results.getString(3));
                response.put("Email", results.getString(4));
                response.put("StartDate", results.getString(5));
                response.put("Activity", results.getBoolean(6));
                response.put("Token", results.getString(7));
            }
            return response.toString();
        } catch (Exception exception){
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to list items.  Error code xx.\"}";
        }
    }

    @POST
    @Path("new")
    public String userNew(@FormDataParam("Username") String Username, @FormDataParam("Password") String Password, @FormDataParam("Email") String Email) {
        System.out.println("Invoked Users.userNew()");
        String date = java.time.LocalDate.now().toString();
        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Users (Username, Password, Email, StartDate, Activity) VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, Username);
            ps.setString(2, Password);
            ps.setString(3, Email);
            ps.setString(4, date);
            ps.setString(5, String.valueOf(true));
            ps.execute();
            return "{\"OK\": \"Added user.\"}";
        }
        catch (Exception exception){
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to create new item, please see server console for more info.\"}";
        }
    }

    @POST
    @Path("delete/{userID}")
    public String deleteUser(@PathParam("userID") Integer userID){
        System.out.println("Invoked Users.deleteUser()");
        try{
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Users WHERE UserID = ?");
            ps.setInt(1, userID);
            ps.execute();
            return "{\"OK\": \"User deleted\"}";
        }
        catch (Exception exception){
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to delete item, please see server console for more info.\"}";
        }
    }
}
