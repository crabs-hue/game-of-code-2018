package datapublic;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Crawler {
	private static final int TIMEOUT = 5 * 1000;
	static Logger log = LoggerFactory.getLogger(Crawler.class);

	public static List<OpenDataset> crawlAPage(int page, boolean stopQuick) throws Exception {
		List<OpenDataset> ret = new ArrayList<>();
		URL url = new URL("https://data.public.lu/en/datasets/?page=" + page);
		try {
			Document doc = Jsoup.parse(url, TIMEOUT);
			log.info("Doc '{}' parsed from '{}'", doc.title(), url);
			Elements datasets = doc.select("li[class~=dataset-result]");
			for (Element dataset : datasets) {
				Element a = dataset.getElementsByTag("a").first();
				String link = a.attr("href");
				URL datasetUrl = new URL(url, link);
				String title = a.attr("title");
				String description = dataset.getElementsByClass("result-description").first().text();
				log.info("crawled {}\n {}\n href='{}'", title, description, link);
				log.info("Could get more from '{}'", datasetUrl);
				ret.add(getDataset(datasetUrl));
				// Just get one for the test...
				if (stopQuick)
					break;
			}
		} catch (SocketTimeoutException e) {
			log.error("Url {} timed out", url);
			return new ArrayList<>();
		}
		return ret;
	}

	public static OpenDataset getDataset(URL url) throws Exception {
		OpenDataset ret = new OpenDataset();
		ret.origin = url;

		try {
			Document doc = Jsoup.parse(url, TIMEOUT);
			ret.title = doc.title();
			log.info("Doc '{}' parsed from '{}'", doc.title(), url);
			StringBuilder description = new StringBuilder();
			for (TextNode descr : doc.getElementsByAttributeValue("class", "markdown").first()
					.getElementsByTag("p").first().textNodes()) {
				description.append(descr.text());
				description.append(" ");
			}
			ret.description = description.toString();
			log.info("Extracted description :'{}'", description);
			String author = getItemPropAttribute(doc, "author", "title");
			ret.author = author;
			log.info("Extracted provider :'{}'", author);

			for (Element keywordElement : doc.getElementsByAttributeValue("itemprop", "keywords")) {
				String keyword = keywordElement.attr("content");
				log.info("Keyword extracted {}", keyword);
				ret.keywords.add(keyword);
			}

			log.debug("End of Eurovoc extraction");
			for (Element dist : doc.getElementsByAttributeValue("class", "list-group-item")) {
				Elements as = dist.getElementsByTag("a");
				if(as.size()>1){
					Source src = new Source();
				String href = as.get(1).attr("href");
				src.link = new URL(href);
					src.format = dist.getElementsByAttribute("data-format").attr("data-format");
					ret.sources.add(src);
					log.info("Distro {} ", src);
				}
				//src.description = getItemPropContent(dist, "description");
				//src.createdOn = getItemPropContent(dist, "dateCreated");
				//src.size = getItemPropContent(dist, "contentSize");
				//src.modifiedOn = getItemPropContent(dist, "dateModified");
			}
		} catch (SocketTimeoutException e) {
			log.error("Url {} timed out", url);
			return null;
		}
		return ret;
	}

	private static String getItemPropContent(Element el, String property) {

		return getItemPropAttribute(el, property, "content");
	}

	private static String getItemPropAttribute(Element el, String property, String attribute) {
		String ret = null;
		Elements els = el.getElementsByAttributeValue("itemprop", property);
		if (els != null && els.size() > 0) {
			return els.first().attr(attribute);
		}
		return ret;
	}
}
