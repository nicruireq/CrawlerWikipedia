package CrawlerWikipedia.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class testURLGetContent {

	public static void main(String[] args)
	{
		InputStream in = null;
		try {
			URL wiki = new URL("https://es.wikipedia.org/wiki/Penicilina");
			Class<?>[] types = new Class[2];
			types[0] = String.class;
			types[1] = InputStream.class;
			Object o = wiki.getContent(types);
			
			System.out.println(wiki.getContent().getClass().getName());
			
			if (o instanceof String) {
				System.out.println(o);
			} else if(o instanceof InputStream) {
				Reader r = new InputStreamReader((InputStream) o);
				int c;
				while ((c = r.read()) != -1) {
					System.out.print((char)c);
				}
			}
			
			System.out.println("TEST 2 URIS");
			URI uno = new URI("/wiki/Penicilina");
			URI dos = new URI("/wiki/Penicilina");
			System.out.println("resultado equals: " + uno.equals(dos));
			System.out.println("resultado equals: " + dos.equals(uno));
			System.out.println("resultado compareTo: " + uno.compareTo(dos));
			System.out.println("resultado compareTo: " + dos.compareTo(uno));
			
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}	finally {
			if (in != null)
				try {in.close();} catch(IOException e) {}
			in = null;
		}
	}
	
}
