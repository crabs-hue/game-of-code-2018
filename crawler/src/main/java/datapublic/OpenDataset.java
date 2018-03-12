package datapublic;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OpenDataset {
	@Override
	public String toString() {
		return "OpenDataset [title=" + title + ", sources=" + sources + ", eurovoc=" + eurovoc + ", description="
				+ description + ", author=" + author + ", keywords=" + keywords + "]";
	}

	public String title;
	public List<Source> sources = new ArrayList<>();
	public Set<String> eurovoc = new HashSet<>();
	public String description;
	public String author;
	public List<String> keywords = new ArrayList<>();
	public URL origin;
}
