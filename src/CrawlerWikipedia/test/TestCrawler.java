package CrawlerWikipedia.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

import CrawlerWikipedia.types.Crawler;

public class TestCrawler {

	private static final String USAGE = "Use: TestCrawler limit [-f file|-u url] [-n|-t time]";
	
	public static void main(String[] args) throws URISyntaxException {
		
		if (args.length < 4 || args.length > 5)
			throw new IllegalArgumentException(USAGE);
		
		int limit = Integer.parseInt(args[0]);
		
		List<URI> seeds;
		if ( (args[1].compareTo("-f") == 0) ) {
			seeds = loadSeedsFromFile(args[2]);
		} else if ((args[1].compareTo("-u") == 0)) {
			seeds = new LinkedList<URI>();
			seeds.add(new URI(args[2]));
		} else { 
			throw new IllegalArgumentException(USAGE);
		}
		
		
		Crawler crawler = new Crawler(seeds, limit);
		if ( args[3].compareTo("-n") == 0 )
			crawler.start();
		else if ( args[3].compareTo("-t") == 0 )
			crawler.start(Long.parseLong(args[4]));
		else
			throw new IllegalArgumentException(USAGE);
		
		crawler.writeLogs();
	}
	
	private static List<URI> loadSeedsFromFile(String f) {
		
		List<URI> seeds = new LinkedList<URI>();
		try ( BufferedReader reader = new BufferedReader(
				new InputStreamReader(new FileInputStream(new File(f)))) )
		{
			String line;
			while ( (line = reader.readLine()) != null ) 
				seeds.add(new URI(line));
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return seeds;
	}

}
