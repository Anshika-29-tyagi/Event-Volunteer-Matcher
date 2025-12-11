public class Volunteer {
    public int id;
    public String name;
    public String branch;
    public int year;
    public String interests;
    public String skills;
    public int experience;


    public Volunteer() {}


    public Volunteer(int id, String name, String branch, int year, String interests, String skills, int experience) {
        this.id = id; this.name = name; this.branch = branch; this.year = year;
        this.interests = interests; this.skills = skills; this.experience = experience;
    }

    @Override
    public String toString() {
        return String.format("Volunteer[id=%d,name=%s,skills=%s,exp=%d]", id, name, skills, experience);
    }
}
