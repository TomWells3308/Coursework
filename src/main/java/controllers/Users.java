package controllers;

import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import server.Main;

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
    @Path("get/{userID}")
    public String userGet(@PathParam("userID") Integer userID) {
        System.out.println("Invoked Users.userGet() with UserID " + userID);
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT UserID, Username, Password, Email, StartDate, Activity, Token FROM Users");
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
        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Users (Username, Password, Email) VALUES (?, ?, ?)");
            ps.setString(1, Username);
            ps.setString(2, Password);
            ps.setString(3, Email);
            ps.execute();
            return "{\"OK\": \"Added user.\"}";
        }
        catch (Exception exception){
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to create new item, please see server console for more info.\"}";
        }
    }
}
