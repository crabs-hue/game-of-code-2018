package lu.arhs.hackathon.repository;

import lu.arhs.hackathon.domain.Event;
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
        }

        return events;
    }

}
