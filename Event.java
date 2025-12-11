public class Event {
    public int id;
    public String eventName;
    public String requiredSkills;
    public int volunteersNeeded;
    public String eventDate; // YYYY-MM-DD


    // No-args constructor
    public Event() {}

    public Event(int id, String eventName, String requiredSkills, int volunteersNeeded, String eventDate) {
        this.id = id; this.eventName = eventName; this.requiredSkills = requiredSkills;
        this.volunteersNeeded = volunteersNeeded; this.eventDate = eventDate;
    }

    @Override
    public String toString() {
        return String.format("Event[id=%d,name=%s,date=%s,needed=%d]", id, eventName, eventDate, volunteersNeeded);
    }
}
