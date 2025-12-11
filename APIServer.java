import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import java.net.InetSocketAddress;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class APIServer {

    public static void main(String[] args) throws Exception {
        DB.init();

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // -------------------- GET APIs --------------------
        server.createContext("/volunteers", exchange -> {
            String json = JSONBuilder.volunteersJSON();
            send(exchange, json);
        });

        server.createContext("/events", exchange -> {
            String json = JSONBuilder.eventsJSON();
            send(exchange, json);
        });

        server.createContext("/match", exchange -> {
            String query = exchange.getRequestURI().getQuery(); // eventId=1
            int eventId = Integer.parseInt(query.split("=")[1]);
            String json = JSONBuilder.matchJSON(eventId);
            send(exchange, json);
        });

        // -------------------- POST: ADD VOLUNTEER --------------------
        server.createContext("/addVolunteer", exchange -> {

            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {

                String body = readBody(exchange);

                // body example: name=Harsh&branch=CSE&year=1&interests=coding&skills=java&experience=2
                Volunteer v = parseVolunteer(body);

                new VolunteerDAO().insert(v);

                send(exchange, "{\"status\":\"success\"}");
            }
        });

        // -------------------- POST: ADD EVENT --------------------
        server.createContext("/addEvent", exchange -> {

            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {

                String body = readBody(exchange);

                Event e = parseEvent(body);

                new EventDAO().insert(e);

                send(exchange, "{\"status\":\"success\"}");
            }
        });

        server.start();
        System.out.println("🔥 API Running on http://localhost:8080/");
    }

    // -------------------- Helper: Send Response --------------------
    private static void send(HttpExchange ex, String json) throws IOException {
        ex.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        ex.getResponseHeaders().add("Access-Control-Allow-Headers", "*");
        ex.getResponseHeaders().add("Access-Control-Allow-Methods", "*");

        byte[] bytes = json.getBytes();
        ex.sendResponseHeaders(200, bytes.length);
        ex.getResponseBody().write(bytes);
        ex.close();
    }

    // -------------------- Helper: Read POST Body --------------------
    private static String readBody(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();
        return new String(is.readAllBytes(), StandardCharsets.UTF_8);
    }

    // -------------------- Parse Volunteer --------------------
    private static Volunteer parseVolunteer(String body) {
        Volunteer v = new Volunteer();
        String[] parts = body.split("&");

        for (String p : parts) {
            String[] kv = p.split("=");
            String key = kv[0];
            String value = kv.length > 1 ? kv[1].replace("+", " ") : "";

            switch (key) {
                case "name": v.name = value; break;
                case "branch": v.branch = value; break;
                case "year": v.year = Integer.parseInt(value); break;
                case "interests": v.interests = value; break;
                case "skills": v.skills = value; break;
                case "experience": v.experience = Integer.parseInt(value); break;
            }
        }

        return v;
    }

    // -------------------- Parse Event --------------------
    private static Event parseEvent(String body) {
        Event e = new Event();
        String[] parts = body.split("&");

        for (String p : parts) {
            String[] kv = p.split("=");
            String key = kv[0];
            String value = kv.length > 1 ? kv[1].replace("+", " ") : "";

            switch (key) {
                case "eventName": e.eventName = value; break;
                case "requiredSkills": e.requiredSkills = value; break;
                case "volunteersNeeded": e.volunteersNeeded = Integer.parseInt(value); break;
                case "eventDate": e.eventDate = value; break;
            }
        }

        return e;
    }
}
