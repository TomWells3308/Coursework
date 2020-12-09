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

@Path("game/")
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.APPLICATION_JSON)

public class Games {
    @GET
    @Path("get-rules/{GameID}")
    public String rulesGet(@PathParam("GameID") Integer GameID) {
        System.out.println("Invoked Games.rulesGet() with GameID " + GameID);
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT Rules FROM Games");
            ResultSet results = ps.executeQuery();
            JSONObject response = new JSONObject();
            if (results.next()) {
                response.put("Rules", results.getString(1));
            }
            return response.toString();
        } catch (Exception exception){
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to list items.  Error code xx.\"}";
        }
    }

    @GET
    @Path("list")
    public String gameList(){
        System.out.println("Invoked Games.gameList()");
        JSONArray response = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT GameID, Title, Rules, GamePage FROM Games");
            ResultSet results = ps.executeQuery();
            while (results.next()==true) {
                JSONObject row = new JSONObject();
                row.put("GameID", results.getInt(1));
                row.put("Title", results.getString(2));
                row.put("Rules", results.getString(3));
                row.put("GamePage", results.getString(4));
                response.add(row);
            }
            return response.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to list items.  Error code xx.\"}";
        }
    }
}
