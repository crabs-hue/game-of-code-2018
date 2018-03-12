package datapublic;

import java.net.URL;

public class Source {
	@Override
	public String toString() {
		return "Source [link=" + link + ", format=" + format + ", description=" + description + ", createdOn="
				+ createdOn + ", modifiedOn=" + modifiedOn + "]";
	}

	public URL link;
	public String format;
	public String description;
	public String createdOn;
	public String modifiedOn;
	public String size;
}
