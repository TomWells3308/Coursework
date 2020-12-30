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

@Path("leaderboard/")
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.APPLICATION_JSON)

public class Leaderboards {
  @POST
  @Path("update")
  public String updateLeaderboard(@FormDataParam("Score") Integer Score, @FormDataParam("UserID") Integer UserID, @FormDataParam("GameID") Integer GameID){
      try {
            System.out.println("Invoked Leaderboards.updateLeaderboard/update id=" + UserID);
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Leaderboards SET Score = ? WHERE UserID = ? AND GameID = ?");
            ps.setInt(1, Score);
            ps.setInt(2, UserID);
            ps.setInt(3, GameID);
            ps.execute();
            return "{\"OK\": \"Leaderboard updated\"}";
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to update item, please see server console for more info.\"}";
        }
    }
  
  @POST
  @Path("new")
  public String leaderboardNew(@FormDataParam("UserID") Integer UserID, @FormDataParam("GameID") Integer GameID) {
        System.out.println("Invoked Leaderboards.leaderboardNew()");
        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Leaderboards (UserID, GameID, Score) VALUES (?, ?, ?)");
            ps.setInt(1, UserID);
            ps.setInt(2, GameID);
            ps.setInt(3, 0);
            ps.execute();
            return "{\"OK\": \"Added leaderboard.\"}";
        }
        catch (Exception exception){
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to create new item, please see server console for more info.\"}";
        }
    }
  
  @GET
  @Path("list-user/{UserID}")
  public String leaderboardListUser(@PathParam("UserID") Integer UserID){
        System.out.println("Invoked Leaderboards.leaderboardList()");
        JSONArray response = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT UserID, GameID, Score FROM Leaderboards WHERE UserID = ?");
            ps.setInt(1, UserID);
            ResultSet results = ps.executeQuery();
            while (results.next()==true) {
                JSONObject row = new JSONObject();
                row.put("UserID", results.getInt(1));
                row.put("GameID", results.getInt(2));
                row.put("Score", results.getInt(3));
                response.add(row);
            }
            return response.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to list items.  Error code xx.\"}";
        }
    }
  
  @GET
  @Path("list-game/{GameID}")
  public String leaderboardListGame(@PathParam("GameID") Integer GameID){
        System.out.println("Invoked Leaderboards.leaderboardList()");
        JSONArray response = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT * FROM Leaderboards WHERE GameID = ?");
            ps.setInt(1, GameID);
            ResultSet results = ps.executeQuery();
            while (results.next()==true) {
                JSONObject row = new JSONObject();
                row.put("UserID", results.getInt(1));
                row.put("GameID", results.getInt(2));
                row.put("Score", results.getInt(3));
                response.add(row);
            }
            return response.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to list items.  Error code xx.\"}";
        }
    }

    @GET
    @Path("list")
    public String leaderboardList(){
        System.out.println("Invoked Leaderboards.leaderboardList()");
        JSONArray response = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT UserID, GameID, Score FROM Leaderboards");
            ResultSet results = ps.executeQuery();
            while (results.next()==true) {
                JSONObject row = new JSONObject();
                row.put("UserID", results.getInt(1));
                row.put("GameID", results.getInt(2));
                row.put("Score", results.getInt(3));
                response.add(row);
            }
            return response.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to list items.  Error code xx.\"}";
        }
    }

    @GET
    @Path("list-game10/{GameID}")
    public String leaderboardListGame10(@PathParam("GameID") Integer GameID){
        System.out.println("Invoked Leaderboards.leaderboardList10()");
        JSONArray response = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT * FROM Leaderboards WHERE GameID = ? ORDER BY Score DESC");
            ps.setInt(1, GameID);
            ResultSet results = ps.executeQuery();
            while (results.next()==true) {
                JSONObject row = new JSONObject();
                row.put("UserID", results.getInt(1));
                row.put("GameID", results.getInt(2));
                row.put("Score", results.getInt(3));
                response.add(row);
            }
            return response.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to list items.  Error code xx.\"}";
        }
    }
}
