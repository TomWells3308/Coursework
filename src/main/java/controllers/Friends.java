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

@Path("friend/")
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.APPLICATION_JSON)

public class Friends {
    @POST
    @Path("add")
    public String friendAdd(@FormDataParam("UserID_1") Integer UserID_1, @FormDataParam("UserID_2") Integer UserID_2, @FormDataParam("token") String token) {
        System.out.println("Invoked Friends.friendAdd()");
        if (Users.validToken(token)) {
            try {
                PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Friendships (UserID_1, UserID_2) VALUES (?, ?)");
                ps.setInt(1, UserID_1);
                ps.setInt(2, UserID_2);
                ps.execute();
                return "{\"OK\": \"Added friendship.\"}";
            } catch (Exception exception) {
                System.out.println("Database error: " + exception.getMessage());
                return "{\"Error\": \"Unable to create new item, please see server console for more info.\"}";
            }
        }
        else{
            return "{\"Error\": \"Invalid token.\"}";
        }
    }

    @POST
    @Path("delete/{UserID_1}")
    public String deleteFriend(@PathParam("UserID_1") Integer UserID_1, @FormDataParam("UserID_2") Integer UserID_2){
        System.out.println("Invoked Users.deleteFriendship()");
        try{
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Friendships WHERE UserID_1 = ? AND UserID_2 = ?");
            ps.setInt(1, UserID_1);
            ps.setInt(2, UserID_2);
            ps.execute();
            return "{\"OK\": \"Friendship deleted\"}";
        }
        catch (Exception exception){
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to delete item, please see server console for more info.\"}";
        }
    }
}
