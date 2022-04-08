package CrawlerWikipedia.types;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Crawler {

	List<URI> seeds;
	int limit, total;
	Map<URI,Register> register;
	
	public Crawler(List<URI> lSeeds, int iLimit) throws URISyntaxException {
		seeds = lSeeds;
		limit = iLimit;
		total = 0;
		register = new TreeMap<URI,Register>();
	}
	
	public void start() {
		reset();
		Iterator<URI> it = seeds.iterator();
		
		while ( (total < limit) && it.hasNext() ) {
			URI first = it.next();
			
			Queue<URI> links = new LinkedList<URI>();  
			Set<URI> visitedLinks = new TreeSet<URI>();
			
			long start = System.nanoTime();
			links.add(first);
			Downloader downloader = new Downloader();
			ParserWikiEs parser = new ParserWikiEs();
			while (!links.isEmpty() && (visitedLinks.size() < limit)) {
				URI current = links.poll();
				if (!visitedLinks.contains(current)) {
					downloader.setUri(current);
					String content = downloader.getStringContent();
					if (content != null) {
						parser.setContent(content);
						links.addAll(parser.getFilteredLinks());
					}
					visitedLinks.add(current);
				}
			}
			long end = System.nanoTime();
			long elapsed = (end-start) / 1000000000;
			total = total + visitedLinks.size();
			register.put(first, new Register(elapsed, visitedLinks));
			
		}
		
	}
	
	/*
	 * time: period of time between seeds in seconds
	 * */
	public void start(long time) {
		if ( time <= 0 )
			throw new IllegalArgumentException("illegal value for \'time\'");
		
		reset();
		Iterator<URI> it = seeds.iterator();
		
		while ( (total < limit) && it.hasNext() ) {
			URI first = it.next();
			
			Queue<URI> links = new LinkedList<URI>();  
			Set<URI> visitedLinks = new TreeSet<URI>();
			
			long start = System.nanoTime();
			long last = start;
			links.add(first);
			Downloader downloader = new Downloader();
			ParserWikiEs parser = new ParserWikiEs();
			while (!links.isEmpty() && (visitedLinks.size() < limit)) {
				URI current = links.poll();
				if (!visitedLinks.contains(current)) {
					downloader.setUri(current);
					String content = downloader.getStringContent();
					if (content != null) {
						parser.setContent(content);
						links.addAll(parser.getFilteredLinks());
					}
					visitedLinks.add(current);
				}
				// check time
				long now = System.nanoTime();
				if ( ((now - last) / 1000000000) >= time ) {
					last = now;
					break;
				}
			}
			long end = System.nanoTime();
			long elapsed = (end-start) / 1000000000;
			total = total + visitedLinks.size();
			register.put(first, new Register(elapsed, visitedLinks));
			
		}
		
	}
	
	public void writeLogs() {
		if (!register.isEmpty()) {
			int i = 1;
			Set<Map.Entry<URI, Register>> allVisited = register.entrySet();
			for (Map.Entry<URI, Register> entry : allVisited) {
				Instant instant = Instant.now();
				long timeStamp = instant.toEpochMilli();
				try (BufferedWriter fWriter = new BufferedWriter(
						new OutputStreamWriter(
								new FileOutputStream(new File(".\\visited" + i + "_" + timeStamp + ".log")),"UTF-8")) )
				{
					i+=1;
					// first line is the seed
					fWriter.write(entry.getKey().toString());
					fWriter.newLine();
					// second line is the elapsed time to retrieve the set (in seconds)
					fWriter.write(String.valueOf(entry.getValue().getSeconds()));
					fWriter.newLine();
					// third line is the number of visited links
					fWriter.write(String.valueOf(entry.getValue().getLinks().size()));
					fWriter.newLine();
					// remain the visited links
					for (URI u : entry.getValue().getLinks()) {
						fWriter.write(u.toString());
						fWriter.newLine();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public void reset() {
		register.clear();
	}

}
