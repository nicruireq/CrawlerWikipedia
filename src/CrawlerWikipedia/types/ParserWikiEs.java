package CrawlerWikipedia.types;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserWikiEs {
	private String sContent;
	private final Pattern pLinks = Pattern.compile(
			"<\\s*a[^>]*href=\"(?<href>.*?)\"[^>]*>.*?<\\s*\\/\\s*a>");
	private final Pattern esWikisRel = Pattern.compile(
			"^\\/wiki\\/[^\\.:\\/]*$");
	
	
	public ParserWikiEs() {
		sContent = null;
	}
	
	public void setContent(String sC) {
		sContent = sC;
	}
	
	public List<URI> getFilteredLinks() {
		// It's not explicitly stated in the javadoc of Pattern#matcher, 
		// but it will not work with a null input
		Matcher matLinks = pLinks.matcher(sContent);
		Matcher matEsWikisRel = esWikisRel.matcher("");
		List<URI> lFilteredLinks = new LinkedList<URI>();
		
		while (matLinks.find()) {
			String pat = matLinks.group("href");
			matEsWikisRel.reset(pat);
			if (matEsWikisRel.matches())
				try {
					// split the string by the hash character and throw away
					// the tail behind the hash (#) (if present)
					pat = (pat.split("#"))[0];
					//URI temp = new URI("https","es.wikipedia.org",pat,null);	// constructor con bug
					// si le doy de path: /wiki/Gl%C3%A1ndula_pineal 
					// internamente genera: https://es.wikipedia.org/wiki/Gl%25C3%25A1ndula_pineal
					URI temp = new URI("https://es.wikipedia.org"+pat);
					lFilteredLinks.add(temp);
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		return lFilteredLinks;
	}
	
	public void reset() {sContent = null;}
}
