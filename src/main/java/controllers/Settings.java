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
import java.util.concurrent.ExecutionException;

@Path("settings/")
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.APPLICATION_JSON)

public class Settings {
  @POST
  @Path("update/{UserID}")
  public String updateSettings(@FormDataParam(VolumeSFX) Integer VolumeSFX, @FormDataParam(VolumeMusic) Integer VolumeMusic, @FormDataParam(Animations) Boolean Animations, @FormDataParam(Chat) Boolean Chat){
  try {
            System.out.println("Invoked Settings.updateSettings/update id=" + UserID);
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Settings SET VolumeSFX = ?, VolumeMusic = ?, Animations = ?, Chat = ? WHERE UserID = ?");
            ps.setInt(1, VolumeSFX);
            ps.setInt(2, VolumeMusic);
            ps.setBoolean(3, Animations);
            ps.setBoolean(4, Chat);
            ps.setInt(5, UserID)
            ps.execute();
            return "{\"OK\": \"Settings updated\"}";
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to update item, please see server console for more info.\"}";
        }
    }
  
  @POST
  @Path("new")
  public String settingsNew(@FormDataParam(UserID) Integer UserID){
  System.out.println("Invoked Settings.settingsNew()");
  try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Settings (UserID, VolumeSFX, VolumeMusic, Animations, Chat) VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, UserID);
            ps.setInt(2, 10);
            ps.setInt(3, 10);
            ps.setBoolean(4, true);
            ps.setBoolean(5, true)
            ps.execute();
            return "{\"OK\": \"Added settings.\"}";
        }
        catch (Exception exception){
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to create new item, please see server console for more info.\"}";
        }
    }
  
    @GET
    @Path("get/{UserID}")
    public String settinsGet(@PathParam("UserID") Integer UserID) {
        System.out.println("Invoked Settings.settingsGet() with UserID " + UserID);
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT VolumeSFX, VolumeMusic, Animations, Chat FROM Users WHERE UserID = ?");
            ps.setInt(1, UserID)
            ResultSet results = ps.executeQuery();
            JSONObject response = new JSONObject();
            if (results.next()) {
                response.put("VolumeSFX", results.getInt(1));
                response.put("VolumeMusic", results.getInt(2));
                response.put("Animations", results.getBoolean(3));
                response.put("Chat", results.getBoolean(4));
            }
            return response.toString();
        } catch (Exception exception){
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to list items.  Error code xx.\"}";
        }
    }
} 
