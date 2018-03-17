package lu.arhs.hackathon;


import lu.arhs.hackathon.domain.Event;
import lu.arhs.hackathon.executor.CypherExecutor;
import lu.arhs.hackathon.repository.GraphRepository;
import org.junit.Test;
import org.omg.PortableInterceptor.PolicyFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GraphRepositoryTest {

    @Test
    public void testEvent() throws Exception {
        List<Event> liste =GraphRepository.getEvents(49.600690970137855, 6.113794412913669, 5);

        Thread.sleep(1000);
    }

    @Test
    public void testParking() throws Exception {
        GraphRepository.getParkings(49.600690970137855, 6.113794412913669, 5);
    }


    @Test
    public void filterBadStopPoints() throws MalformedURLException, IOException{
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Iterator<Map<String, Object>> iterator =  CypherExecutor.query("CALL apoc.load.json('https://api.tfl.lu/v1/Line/Route') YIELD value as routeJ\n" +
                "WITH routeJ\n" +
                "UNWIND routeJ.stopPoints AS stopPointID\n" +
                "return stopPointID", new HashMap<>());

        int i=0;
        while(iterator.hasNext()) {
            System.out.println(i++);
            long id = (long)iterator.next().get("stopPointID");
            if(checkStatusCode("https://api.tfl.lu/v1/StopPoint/"+id) == 404) {
                sb.append(id);
                sb.append(",");
            }
        }

        System.out.print(sb.toString());
    }

    private static int checkStatusCode(String url) throws MalformedURLException, IOException {
        URL u = new URL( url);
        HttpURLConnection huc =  (HttpURLConnection)  u.openConnection ();
        huc.setRequestMethod ("GET");  //OR  huc.setRequestMethod ("HEAD");
        huc.connect () ;
        return huc.getResponseCode() ;
    }

}
