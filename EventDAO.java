import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {

    public void insert(Event e) {
        try {
            Connection con = DB.getConn();
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO events(eventName, requiredSkills, volunteersNeeded, eventDate) VALUES(?,?,?,?)"
            );
            ps.setString(1, e.eventName);
            ps.setString(2, e.requiredSkills);
            ps.setInt(3, e.volunteersNeeded);
            ps.setString(4, e.eventDate);
            ps.executeUpdate();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<Event> getAll() {
        List<Event> list = new ArrayList<>();
        try {
            Connection con = DB.getConn();  // FIXED HERE
            PreparedStatement ps = con.prepareStatement("SELECT * FROM events");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Event e = new Event();
                e.id = rs.getInt("id");
                e.eventName = rs.getString("eventName");
                e.requiredSkills = rs.getString("requiredSkills");
                e.volunteersNeeded = rs.getInt("volunteersNeeded");
                e.eventDate = rs.getString("eventDate");
                list.add(e);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    // ---------------------------------------------------------
    // ✅ PRINT ALL EVENTS
    // ---------------------------------------------------------
    public static void printAll() {
        EventDAO dao = new EventDAO();
        List<Event> list = dao.getAll();

        System.out.println("\n----- Event List -----");
        for (Event e : list) {
            System.out.println(
                    e.id + " | " +
                            e.eventName + " | " +
                            e.requiredSkills + " | " +
                            e.volunteersNeeded + " | " +
                            e.eventDate
            );
        }
        System.out.println("------------------------\n");
    }

    public Event findById(int eid) {
        Event e = null;
        try {
            Connection con = DB.getConn();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM events WHERE id = ?");
            ps.setInt(1, eid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                e = new Event();
                e.id = rs.getInt("id");
                e.eventName = rs.getString("eventName");
                e.requiredSkills = rs.getString("requiredSkills");
                e.volunteersNeeded = rs.getInt("volunteersNeeded");
                e.eventDate = rs.getString("eventDate");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return e;
    }
}


