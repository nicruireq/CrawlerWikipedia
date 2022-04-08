package CrawlerWikipedia.test;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;

import CrawlerWikipedia.types.Downloader;

public class TestDownloader {

	public static void main(String[] args) throws URISyntaxException, MalformedURLException, UnsupportedEncodingException {
		// TODO Auto-generated method stub
		URI web = new URI("https://es.wikipedia.org/wiki/Puerta_l%25C3%25B3gica");
		Downloader down = new Downloader();
		down.setUri(web);
		
		String page = down.getStringContent();
		System.out.println(page);
		
		URL wiki = new URL(URLDecoder.decode(web.toURL().toString(), "UTF-8"));
		System.out.println("URL sin decodificar: " + web.toURL().toString());
		System.out.println("URL decodificar: " + wiki.toString());
		
	}

}
