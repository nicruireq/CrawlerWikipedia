package CrawlerWikipedia.test;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;

public class TestURLUtf8 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			URI uri = new URI(args[0]);
			String test = "/wiki/Gl%C3%A1ndula_pineal";
			URI conbug = new URI("https","es.wikipedia.org",test,null);	// mete %25	
			URI sinbug = new URI("https://es.wikipedia.org" + test);
			System.out.println("uri de test sin bug: " + sinbug.toASCIIString());
			System.out.println("uri de test con bug: " + conbug.toASCIIString());
			
			// split by hash
			String pat = "/wiki/Alcohol#Alcoholes_primarios,_secundarios_y_terciarios#Oxidación_de_alcoholes";
			pat = (pat.split("#"))[0];
			System.out.println("Me quedo primer trozo: " + pat);
			pat = "/wiki/Memoria_sem%C3%A1ntica";
			pat = (pat.split("#"))[0];
			System.out.println("Me quedo primer trozo: " + pat);
			
			//otro fallo
			// la cadena no cumple el RFC2396
			String falla = "https://es.wikipedia.org/wiki/Friedrich_von_Wieser#teoría_monetaria_de_Wieser";
			System.out.println("caracter en index 55: " + falla.charAt(77));
			URI almohadilla = new URI(falla);	// lanza excepcion
			
			
			//URL wiki = new URL(URLDecoder.decode(uri.toString(), "UTF-8"));
			URL wiki = new URL(uri.toASCIIString());
			System.out.println("con toString: " + uri.toString());
			System.out.println("con toString: " + uri.toASCIIString());
			System.out.println("la url ahora: " + wiki.toString());
			
			wiki.openStream();
			
			System.out.println("Conectado");
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
