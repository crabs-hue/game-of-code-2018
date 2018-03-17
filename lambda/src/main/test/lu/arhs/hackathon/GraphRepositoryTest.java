package lu.arhs.hackathon;


import lu.arhs.hackathon.repository.GraphRepository;
import org.junit.Test;

public class GraphRepositoryTest {

    @Test
    public void testEvent() throws Exception {
        GraphRepository.getEvents(49.600690970137855, 6.113794412913669, 5);
    }

}
