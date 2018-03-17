package lu.arhs.hackathon.executor;

import lu.arhs.hackathon.persistance.Neo4jConnection;
import org.neo4j.driver.v1.*;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CypherExecutor {

    public  static Iterator<Map<String, Object>> query(String query, Map<String, Object> params) {
        try (Session session = new Neo4jConnection().getSession()) {
            List<Map<String, Object>> list = session.run(query, params)
                    .list( r -> r.asMap(CypherExecutor::convert));
            return list.iterator();
        }
    }

    static Object convert(Value value) {
        switch (value.type().name()) {
            case "PATH":
                return value.asList(CypherExecutor::convert);
            case "NODE":
            case "RELATIONSHIP":
                return value.asMap();
        }
        return value.asObject();
    }

}