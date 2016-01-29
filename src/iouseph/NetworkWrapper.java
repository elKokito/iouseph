package iouseph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;

/** classe NetworkWrapper pour faciliter l'envoie de requete http
 * @author Marcial Lopez-Ferrada
 *
 */
public final class NetworkWrapper {

	/** methode permettant d'envoyer une requete http GET
	 * @param url 	url demander
	 * @return 		object contenant la reponse qui respecte le format json	
	 */
	private NetworkWrapper()
	{
		
	}
	
	public static JSONObject get(String url) {
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		System.out.println("sending GET: " + url);
		HttpGet get = new HttpGet(url);

		CloseableHttpResponse httpresponse = null;
		try {
			httpresponse = httpclient.execute(get);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JSONObject res_json = null;
		if(httpresponse.getStatusLine().getStatusCode() == 200) {
			try {
				res_json = read_response(httpresponse.getEntity().getContent());
			} catch (UnsupportedOperationException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println(res_json.toString());
		}
		else {
			System.out.println("error");
			System.out.println(httpresponse.getStatusLine().getReasonPhrase());
		}
		return res_json;
	}
	
	/** methode permettant d'envoyer une requete http GET avec une liste comme reponse
	 * @param url	url demander
	 * @return 		object retourner par la requete sous forme de liste
	 */
	public static JSONArray get_array(String url) {
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		System.out.println("sending GET: " + url);
		HttpGet get = new HttpGet(url);

		CloseableHttpResponse httpresponse = null;
		try {
			httpresponse = httpclient.execute(get);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JSONArray res_json = null;
		if(httpresponse.getStatusLine().getStatusCode() == 200) {
			try {
				res_json = read_response_array(httpresponse.getEntity().getContent());
			} catch (UnsupportedOperationException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println(res_json.toString());
		}
		else {
			System.out.println("error");
			System.out.println(httpresponse.getStatusLine().getReasonPhrase());
		}
		return res_json;
	}

	/** methode permettant d'envoyer une requete http POST
	 * @param url			url demander
	 * @param body_args		argument a ajouter dans le corps(body) de la requete
	 * @return				object contenant la reponse qui respecte le format json
	 */
	public static JSONObject post(String url, List<NameValuePair> body_args) {
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		post.setEntity(new UrlEncodedFormEntity(body_args, Consts.UTF_8));
		
		CloseableHttpResponse httpresponse = null;
		try {
			httpresponse = httpclient.execute(post);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JSONObject res_json = null;
		if(httpresponse.getStatusLine().getStatusCode() == 200) {
			try {
				res_json = read_response(httpresponse.getEntity().getContent());
			} catch (UnsupportedOperationException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			System.out.println("error");
			System.out.println(httpresponse.getStatusLine().getReasonPhrase());
		}
		
		return res_json;
	}
	
	/** methode permettant d'analyser la reponse recu (parsing)
	 * @param r 	reponse recu
	 * @return		retourne la reponse sous forme de JSONObject
	 */
	private static JSONObject read_response(InputStream r) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(r)); 
 
        String inputLine;
        StringBuffer response = new StringBuffer();
 
        try {
			while ((inputLine = reader.readLine()) != null) {
			    response.append(inputLine);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(response);
        try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        JSONObject res_json = new JSONObject(response.toString());
        return res_json;
	}
	
	/** methode permettant d'analyser la reponse recu (parsing)
	 * @param r	reponse recu
	 * @return	retourne la reponse sour forme de list JSONArray
	 */
	private static JSONArray read_response_array(InputStream r) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(r)); 
		 
        String inputLine;
        StringBuffer response = new StringBuffer();
 
        try {
			while ((inputLine = reader.readLine()) != null) {
			    response.append(inputLine);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(response);
        try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        JSONArray res_json = new JSONArray(response.toString());
        return res_json;
	
	}
}
