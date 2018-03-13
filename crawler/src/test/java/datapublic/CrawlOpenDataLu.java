package datapublic;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CrawlOpenDataLu {
    static Logger log = LoggerFactory.getLogger(CrawlOpenDataLu.class);

    @Test
    public void crawlRandomPage() throws Exception {
        int page = new Random(System.currentTimeMillis()).nextInt(20) + 1;
        Crawler.crawlAPage(page, true);
    }

    @Ignore("Works but we want to avoid that every time we build...")
    @Test
    public void crawlAllPages() throws Exception {
        int datasets = 0;
        for (int page = 1; page <= 20; page++)
            datasets += Crawler.crawlAPage(page, false).size();
        log.info("Crawled {} datasets", datasets);
    }

    @Test
    public void crawlOnePagesAndShowCSV() throws Exception{
        List<Source> sources = new ArrayList<>();
        int page = 30;
        for (OpenDataset ds : Crawler.crawlAPage(page, false)) {
            if(ds.sources!=null)
            for (Source s : ds.sources) {
                sources.add(s);
            }
        }

        for (Source s : sources) {
            if ("CSV".equalsIgnoreCase(s.format)) {
                log.info("Source:{}", s.link);
            }
        }
    }

    @Test
    public void testOneDetailedPage() throws Exception {
        Crawler.getDataset(
                new URL("https://data.public.lu/en/datasets/comptes-nationaux-comptes-annuels-agregats-par-branche/"));
    }

}
