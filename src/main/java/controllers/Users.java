package controllers;

import org.eclipse.jetty.server.Authentication;
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

import javax.ws.rs.CookieParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Path("user/")
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.APPLICATION_JSON)

public class Users {
    @GET
    @Path("get/{UserID}/{token}")
    public String userGet(@PathParam("UserID") Integer UserID, @PathParam("token") String token) {
        System.out.println("Invoked Users.userGet() with UserID " + UserID);
        if(validToken(token)) {
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
            } catch (Exception exception) {
                System.out.println("Database error: " + exception.getMessage());
                return "{\"Error\": \"Unable to get item.  Error code xx.\"}";
            }
        }
        else{
            return "{\"Error\": \"Invalid Token.\"}";
        }
    }

    @GET
    @Path("get-username/{Username}")
    public String userGetUsername(@PathParam("Username") String Username){
        System.out.println("Invoked Users.userGetUsername() with Username " + Username);
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT UserID FROM Users WHERE Username = ?");
            ps.setString(1, Username);
            ResultSet results = ps.executeQuery();
            JSONObject response = new JSONObject();
            if (results.next()) {
                response.put("UserID", results.getInt(1));
            }
            System.out.println(response.toString());
            return response.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to get item.  Error code xx.\"}";
        }
    }

    @GET
    @Path("get-token/{token}")
    public String userGet(@PathParam("token") String token) {
        System.out.println("Invoked Users.userGet() with Token " + token);
        if(validToken(token)) {
            try {
                PreparedStatement ps = Main.db.prepareStatement("SELECT UserID, Username, Password, Email, StartDate, Activity, Token FROM Users WHERE Token = ?");
                ps.setString(1, token);
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
            } catch (Exception exception) {
                System.out.println("Database error: " + exception.getMessage());
                return "{\"Error\": \"Unable to list items.  Error code xx.\"}";
            }
        }
        else{
            return "{\"Error\": \"Invalid Token.\"}";
        }
    }

    @POST
    @Path("new")
    public String userNew(@FormDataParam("Username") String Username, @FormDataParam("Password") String Password, @FormDataParam("Email") String Email) {
        System.out.println("Invoked Users.userNew()");
        String date = java.time.LocalDate.now().toString();
        Password = generateHash(Password);

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

    @POST
    @Path("login")
    public String loginUser(@FormDataParam("Username") String Username, @FormDataParam("Password") String Password){
        System.out.println("Invoked loginUser() on path user/login");
        Password = generateHash(Password);
        try{
            PreparedStatement ps1 = Main.db.prepareStatement("SELECT Password FROM Users WHERE Username = ?");
            ps1.setString(1, Username);
            ResultSet loginResults = ps1.executeQuery();
            if (loginResults.next() == true) {
                String correctPassword = loginResults.getString(1);
                if (Password.equals(correctPassword)) {
                    String token = UUID.randomUUID().toString();
                    PreparedStatement ps2 = Main.db.prepareStatement("UPDATE Users SET Token = ? WHERE Username = ?");
                    ps2.setString(1, token);
                    ps2.setString(2, Username);
                    ps2.executeUpdate();
                    JSONObject userDetails = new JSONObject();
                    userDetails.put("token", token);
                    return userDetails.toString();
                } else {
                    return "{\"Error\": \"Incorrect password!\"}";
                }
            } else {
                return "{\"Error\": \"Username and password are incorrect.\"}";
            }
        } catch (Exception exception) {
            System.out.println("Database error during /user/login: " + exception.getMessage());
            return "{\"Error\": \"Server side error!\"}";
        }
    }

    @POST
    @Path("logout/{token}")
    public String logoutUser(@PathParam("token") String token){
        System.out.println("Invoked logoutUser() on path user/logout{token}");
        try{
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Users SET Token = null WHERE Token = ?");
            ps.setString(1, token);
            ps.execute();
            return "{\"OK\": \"Token deleted\"}";
        } catch (Exception exception){
            System.out.println("Database error during /user/logout/{token}: " + exception.getMessage());
            return "{\"Error\": \"Server side error!\"}";
        }
    }

    public static boolean validToken(String Token) {		// this method MUST be called before any data is returned to the browser
                                                            // token is taken from the Cookie sent back automatically with every HTTP request
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT UserID FROM Users WHERE Token = ?");
            ps.setString(1, Token);
            ResultSet logoutResults = ps.executeQuery();
            return logoutResults.next();   //logoutResults.next() will be true if there is a record in the ResultSet
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
            return false;
        }
    }

    public static String generateHash(String text) {
        try {
            MessageDigest hasher = MessageDigest.getInstance("MD5");
            hasher.update(text.getBytes());
            return DatatypeConverter.printHexBinary(hasher.digest()).toUpperCase();
        } catch (NoSuchAlgorithmException nsae) {
            return nsae.getMessage();
        }
    }

    @GET
    @Path("list")
    public String usersList(){
        System.out.println("Invoked Users.usersList()");
        JSONArray response = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT UserID, Username FROM Users");
            ResultSet results = ps.executeQuery();
            while (results.next()==true) {
                JSONObject row = new JSONObject();
                row.put("UserID", results.getInt(1));
                row.put("Username", results.getString(2));
                response.add(row);
            }
            return response.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to list items.  Error code xx.\"}";
        }
    }
}
