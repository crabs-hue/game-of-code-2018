package lu.arhs.hackathon.persistance;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;

public class Neo4jConnection implements AutoCloseable {

    private final Driver driver;

    private final Session session;

    public Neo4jConnection(){
        String uri = System.getenv("NEO4J_URI");
        String user = System.getenv("NEO4J_USER");
        String password = System.getenv("NEO4J_PASSWORD");
        this.driver = GraphDatabase.driver( uri, AuthTokens.basic( user, password ) );
        this.session = driver.session();
    }

    @Override
    public void close() {
        this.session.close();
        this.driver.close();
    }


    public Session getSession(){
        return this.session;
    }


}
