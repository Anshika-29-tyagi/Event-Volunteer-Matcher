import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VolunteerDAO {

    public void insert(Volunteer v) {
        try {
            Connection con = DB.getConn();
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO volunteers(name, branch, year, interests, skills, experience) VALUES(?,?,?,?,?,?)"
            );
            ps.setString(1, v.name);
            ps.setString(2, v.branch);
            ps.setInt(3, v.year);
            ps.setString(4, v.interests);
            ps.setString(5, v.skills);
            ps.setInt(6, v.experience);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Volunteer> getAll() {
        List<Volunteer> list = new ArrayList<>();
        try {
            Connection con = DB.getConn();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM volunteers");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Volunteer v = new Volunteer();
                v.id = rs.getInt("id");
                v.name = rs.getString("name");
                v.branch = rs.getString("branch");
                v.year = rs.getInt("year");
                v.interests = rs.getString("interests");
                v.skills = rs.getString("skills");
                v.experience = rs.getInt("experience");
                list.add(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ---------------------------------------------------------
    // ✅ PRINT ALL VOLUNTEERS
    // ---------------------------------------------------------
    public static void printAll() {
        VolunteerDAO dao = new VolunteerDAO();
        List<Volunteer> list = dao.getAll();

        System.out.println("\n----- Volunteer List -----");
        for (Volunteer v : list) {
            System.out.println(
                    v.id + " | " +
                            v.name + " | " +
                            v.branch + " | " +
                            v.year + " | " +
                            v.interests + " | " +
                            v.skills + " | " +
                            v.experience
            );
        }
        System.out.println("---------------------------\n");
    }
}
