package lu.arhs.hackathon.executor;

import lu.arhs.hackathon.persistance.Neo4jConnection;
import org.neo4j.driver.v1.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CypherExecutor {

    private static final Logger log = LoggerFactory.getLogger(CypherExecutor.class);

    public  static Iterator<Map<String, Object>> query(String query, Map<String, Object> params) {
        List<Map<String, Object>> list = new ArrayList<>();
        try (Session session = new Neo4jConnection().getSession()) {
            list = session.run(query, params)
                    .list( r -> r.asMap(CypherExecutor::convert));

        }catch (Exception e){
            log.error(e.getMessage(),e);
        }finally {
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