package lu.arhs.hackathon.repository;

import lu.arhs.hackathon.domain.Event;
import lu.arhs.hackathon.executor.CypherExecutor;

import java.util.*;


import static org.neo4j.helpers.collection.MapUtil.map;

public class GraphRepository {

    public static List<Event>  getEvents() {

        List<Event> events = new ArrayList<>();

        Iterator<Map<String, Object>>  iterator = CypherExecutor.query("WITH point({latitude: 49.600690970137855, longitude: 6.113794412913669}) as my_position\n" +
                "MATCH (event:Event)\n" +
                "WITH event, distance(point({latitude: event.coordinates[0], longitude: event.coordinates[1]}), my_position)  AS distance\n" +
                "ORDER BY distance\n" +
                "LIMIT 5\n" +
                "RETURN event", new HashMap<>());

        while(iterator.hasNext()) {
            Map<String, Object> event = (Map<String, Object>) iterator.next().get("event");

            Event event1 = new Event();
            events.add(event1);
            event1.setLan((double)((List)event.get("coordinates")).get(0));
            event1.setLon((double)((List)event.get("coordinates")).get(1));
            event1.setStart(((String)event.get("start")));
            event1.setEnd(((String)event.get("end")));
            event1.setDescription(((String)event.get("description")));
        }

        return events;
    }

}
