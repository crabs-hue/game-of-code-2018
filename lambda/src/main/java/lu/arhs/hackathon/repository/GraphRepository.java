package lu.arhs.hackathon.repository;

import lu.arhs.hackathon.domain.Event;
import lu.arhs.hackathon.domain.Parking;
import lu.arhs.hackathon.executor.CypherExecutor;

import java.util.*;


import static org.neo4j.helpers.collection.MapUtil.map;

public class GraphRepository {

    public static List<Event>  getEvents(double lat, double lon, int lim) {
        List<Event> events = new ArrayList<>();

        Iterator<Map<String, Object>>  iterator = CypherExecutor.query("WITH point({latitude: {lat}, longitude: {lon}}) as my_position\n" +
                "MATCH (event:Event)\n" +
                "WITH event, distance(point({latitude: event.coordinates[0], longitude: event.coordinates[1]}), my_position)  AS distance\n" +
                "ORDER BY distance\n" +
                "LIMIT {lim}\n" +
                "RETURN event", map("lat",lat, "lon", lon, "lim", lim ));

        while(iterator.hasNext()) {
            Map<String, Object> event = (Map<String, Object>) iterator.next().get("event");

            Event eventDomain = new Event();
            events.add(eventDomain);
            eventDomain.setLan((double)((List)event.get("coordinates")).get(0));
            eventDomain.setLon((double)((List)event.get("coordinates")).get(1));
            eventDomain.setStart(((String)event.get("start")));
            eventDomain.setEnd(((String)event.get("end")));
            eventDomain.setDescription(((String)event.get("description")));

           String name =  (String)((Map<String, Object>)CypherExecutor.query("WITH point({latitude: {lan}, longitude: {lon}}) as my_position\n" +
                    "MATCH (hn:HouseNumber)\n" +
                    "WITH hn, distance(point({latitude: toFloat(hn.lat), longitude: toFloat(hn.lon)}), my_position)  AS distance\n" +
                    "ORDER BY distance\n" +
                    "LIMIT 1\n" +
                    "MATCH (hn)-[*2..2]-(l:Locality)\n" +
                    "WITH l\n" +
                    "LIMIT 1\n" +
                    "RETURN l", map("lan", eventDomain.getLan(), "lon", eventDomain.getLon())).next().get("l")).get("name");
           eventDomain.setLocality(name);
        }

        return events;
    }

    public static List<Parking>  getParkings(double lat, double lon, int lim) {
        List<Parking> parkings = new ArrayList<>();

        Iterator<Map<String, Object>>  iterator = CypherExecutor.query("WITH point({latitude: {lat}, longitude: {lon}}) as my_position\n" +
                "MATCH (n)\n" +
                "WHERE n:Parking:CoveredParking or n:Parking:ParkingSurface\n" +
                "WITH n, distance(point({latitude: n.coordinates[0], longitude: n.coordinates[1]}), my_position)  AS distance\n" +
                "ORDER BY distance\n" +
                "LIMIT {lim}\n" +
                "RETURN n", map("lat",lat, "lon", lon, "lim", lim ));

        while(iterator.hasNext()) {
            Map<String, Object> parking = (Map<String, Object>) iterator.next().get("n");

            Parking parkingDomain = new Parking();
            parkings.add(parkingDomain);
            parkingDomain.setLan((double)((List)parking.get("coordinates")).get(0));
            parkingDomain.setLon((double)((List)parking.get("coordinates")).get(1));
            parkingDomain.setName(((String)parking.get("name")));

            parkingDomain.setBboxX((double)((List)parking.get("bbox")).get(0));
            parkingDomain.setBboxY((double)((List)parking.get("bbox")).get(1));
            parkingDomain.setBboxW((double)((List)parking.get("bbox")).get(2));
            parkingDomain.setBboxZ((double)((List)parking.get("bbox")).get(3));

            String name =  (String)((Map<String, Object>)CypherExecutor.query("WITH point({latitude: {lan}, longitude: {lon}}) as my_position\n" +
                    "MATCH (hn:HouseNumber)\n" +
                    "WITH hn, distance(point({latitude: toFloat(hn.lat), longitude: toFloat(hn.lon)}), my_position)  AS distance\n" +
                    "ORDER BY distance\n" +
                    "LIMIT 1\n" +
                    "MATCH (hn)-[*2..2]-(l:Locality)\n" +
                    "WITH l\n" +
                    "LIMIT 1\n" +
                    "RETURN l", map("lan", parkingDomain.getLan(), "lon", parkingDomain.getLon())).next().get("l")).get("name");
            parkingDomain.setLocality(name);

        }

        return parkings;
    }

}
