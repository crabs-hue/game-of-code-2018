package lu.arhs.hackathon.persistance;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;

public class Neo4jConnection implements AutoCloseable {

    private final Driver driver;

    private final Session session;

    public Neo4jConnection(){
        String uri = "bolt://ec2-52-211-57-228.eu-west-1.compute.amazonaws.com:7687";
        String user = "neo4j";
        String password = "password";
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
