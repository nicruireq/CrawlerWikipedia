package CrawlerWikipedia.types;

import java.net.URI;
import java.util.Set;

public final class Register {
	private final long seconds;
	private final Set<URI> links;
	
	public Register(long t, Set<URI> l) {
		seconds = t;
		links = l;
	}

	public long getSeconds() {
		return seconds;
	}

	public Set<URI> getLinks() {
		return links;
	}
	
}
