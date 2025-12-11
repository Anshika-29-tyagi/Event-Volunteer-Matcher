import java.util.List;

public class JSONBuilder {

    public static String volunteersJSON() {
        var list = new VolunteerDAO().getAll();
        String json = "[";

        for (var v : list) {
            json += "{"
                    + "\"id\":" + v.id + ","
                    + "\"name\":\"" + v.name + "\","
                    + "\"branch\":\"" + v.branch + "\","
                    + "\"year\":" + v.year + ","
                    + "\"interests\":\"" + v.interests + "\","
                    + "\"skills\":\"" + v.skills + "\","
                    + "\"experience\":" + v.experience
                    + "},";
        }

        if (json.endsWith(",")) json = json.substring(0, json.length() - 1);

        return json + "]";
    }



    public static String eventsJSON() {
        var list = new EventDAO().getAll();
        String json = "[";

        for (var e : list) {
            json += "{"
                    + "\"id\":" + e.id + ","
                    + "\"eventName\":\"" + e.eventName + "\","
                    + "\"requiredSkills\":\"" + e.requiredSkills + "\","
                    + "\"volunteersNeeded\":" + e.volunteersNeeded + ","
                    + "\"eventDate\":\"" + e.eventDate + "\""
                    + "},";
        }

        if (json.endsWith(",")) json = json.substring(0, json.length() - 1);

        return json + "]";
    }




    //  FINAL MATCH API 

    public static String matchJSON(int eventId) {

        List<Event> events = new EventDAO().getAll();
        List<Volunteer> volunteers = new VolunteerDAO().getAll();

        Event selected = null;

        // find event by ID
        for (Event e : events) {
            if (e.id == eventId) {
                selected = e;
                break;
            }
        }

        if (selected == null) return "[]";   // No event found ⇒ No match


        String json = "[";

        for (Volunteer v : volunteers) {

            int score = 0;

            // Skills matching
            if (v.skills != null &&
                    selected.requiredSkills != null &&
                    v.skills.toLowerCase().contains(selected.requiredSkills.toLowerCase())) {
                score += 60;
            }

            // Experience bonus
            score += v.experience * 10;

            // Interest matching
            if (v.interests != null &&
                    selected.eventName != null &&
                    v.interests.toLowerCase().contains(selected.eventName.toLowerCase())) {
                score += 20;
            }

            json += "{"
                    + "\"volunteer\":\"" + v.name + "\","
                    + "\"score\":" + score
                    + "},";
        }

        if (json.endsWith(",")) json = json.substring(0, json.length() - 1);

        return json + "]";
    }
}
