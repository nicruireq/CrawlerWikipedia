package CrawlerWikipedia.test;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class testUrl {
	
	public static String getUTF8String(InputStream in) 
			throws IOException {
		
		BufferedReader isr = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = isr.readLine()) != null)
			sb.append(line).append("\n");
		return sb.toString();
	}
	
	public static void main(String[] args)
	{
		InputStream in = null;
		try {
			URL wiki = new URL("https://es.wikipedia.org/wiki/Tectosilicato");
			in = wiki.openStream();
			
			String html = getUTF8String(in);
			//System.out.println(html);
			
			Pattern pLinks = Pattern.compile("<\\s*a[^>]*href=\"(?<href>.*?)\"[^>]*>.*?<\\s*\\/\\s*a>");
			Matcher matLinks = pLinks.matcher(html);
			List<String> lLinks = new ArrayList<String>();
			while (matLinks.find()) {
				lLinks.add(matLinks.group("href"));
			}
			
			System.out.println("NUM LINKS FOUND = " + lLinks.size());
			System.out.println("LIST OF LINKS:\n");
			for (String s : lLinks)
				System.out.println(s);
			
			// filter only relative links for es.wikipedia.org/wiki
			Pattern esWikisRel = Pattern.compile("^\\/wiki\\/[^\\.:\\/]*$");
			Matcher matEsWikisRel = esWikisRel.matcher("");
			List<String> lFilteredLinks = new ArrayList<String>();
			
			if (!lLinks.isEmpty()) {
				System.out.println("LIST OF FILTERED LINKS:\n");
				for (String pat : lLinks) {
					matEsWikisRel.reset(pat);
					if (matEsWikisRel.matches()) {
						System.out.println(pat);
						lFilteredLinks.add(pat);
					}
				}
			}
			
			/*System.out.println("LIST OF FILTERED LINKS:\n");
			for (String s : lFilteredLinks) {
				System.out.println(s);
			}*/
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null)
				try {in.close();} catch(IOException e) {}
			in = null;
		}
	}

}
