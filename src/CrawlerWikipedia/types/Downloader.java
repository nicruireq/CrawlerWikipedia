package CrawlerWikipedia.types;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
//import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

public class Downloader {
	
	private URI uri;
	private int iDelay;	// in microseconds
	
	public Downloader() {
		uri = null;
		iDelay = 0;
	}
	
	public void setUri(URI u) {
		uri = u;
	}
	
	public void setDelay(int d) {
		iDelay = d;
	}
	
	/*
	 * returns null if an exception occurs
	 */
	public String getStringContent() {
		
		if (iDelay > 0) {
			try {
				TimeUnit.MICROSECONDS.sleep(iDelay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		}
		
		if (uri != null) {
			InputStream in = null;
			try {
				//URL wiki = uri.toURL();
				//URL wiki = new URL(URLDecoder.decode(uri.toURL().toString(), "UTF-8"));
				URL wiki = new URL(uri.toASCIIString());
				in = wiki.openStream();
				BufferedReader isr = new BufferedReader(new InputStreamReader(in, "UTF-8"));
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = isr.readLine()) != null)
					sb.append(line).append("\n");
				return sb.toString();
				
			} catch (MalformedURLException e) {
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			} finally {
				if (in != null)
					try {in.close();} catch(IOException e) {}
			}
		} else return null;
		
	}
	
	public void reset() {
		uri = null;
		iDelay = 0;
	}
}
