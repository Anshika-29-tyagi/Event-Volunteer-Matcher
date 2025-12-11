import java.util.*;
import java.sql.*;

public class Matcher {
    // Simple matching: for each required skill, if volunteer.skills contains it -> +50
    // plus experience*10, plus interest match +10 per interest matched.
    public static List<MatchResult> matchForEvent(Event e) {
        List<Volunteer> volunteers = new VolunteerDAO().getAll();
        List<MatchResult> results = new ArrayList<>();
        String[] reqSkills = e.requiredSkills.split("\\s*,\\s*");
        for (Volunteer v : volunteers) {
            int score = 0;
            // skill match
            for (String rs : reqSkills) {
                if (rs.isBlank()) continue;
                if (v.skills != null && v.skills.toLowerCase().contains(rs.toLowerCase())) score += 50;
            }
            // interest match
            String[] interests = v.interests != null ? v.interests.split("\\s*,\\s*") : new String[0];
            for (String it : interests) {
                if (it.isBlank()) continue;
                if (e.eventName.toLowerCase().contains(it.toLowerCase())) score += 10;
            }
            // experience
            score += v.experience * 10;
            results.add(new MatchResult(v, score));
        }
        // sort desc
        results.sort((a,b)->Integer.compare(b.score,a.score));
        return results;
    }
}

class MatchResult {
    public Volunteer volunteer;
    public int score;
    public MatchResult(Volunteer v, int score) { this.volunteer = v; this.score = score; }
}
