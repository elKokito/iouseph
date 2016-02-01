package iouseph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * classe NetworkWrapper pour faciliter l'envoie de requete http
 * 
 * @author Marcial Lopez-Ferrada
 * 
 */
public final class NetworkWrapper {

	/**
	 * methode permettant d'envoyer une requete http GET
	 * 
	 * @param url
	 *            url demander
	 * @return object contenant la reponse qui respecte le format json
	 */
	private NetworkWrapper() {

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
		if (httpresponse.getStatusLine().getStatusCode() == 200) {
			try {
				res_json = read_response(httpresponse.getEntity().getContent());
			} catch (UnsupportedOperationException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// System.out.println(res_json.toString());
		} else {
			System.out.println("error");
			System.out.println(httpresponse.getStatusLine().getReasonPhrase());
		}
		return res_json;
	}

	/**
	 * methode permettant d'envoyer une requete http GET
	 * 
	 * @param url
	 *            url demandé ,
	 * @param HeaderName
	 *            Nom de l'entete
	 * @param HeaderValue
	 *            valeur de l'entete
	 * @return object contenant la reponse qui respecte le format json
	 */
	public static JSONObject get(String url, String HeaderName,
			String HeaderValue) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet get = new HttpGet(url);

		get.setHeader(HeaderName, HeaderValue);
		CloseableHttpResponse httpresponse = null;
		try {
			httpresponse = httpclient.execute(get);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		JSONObject res_json = null;
		if (httpresponse.getStatusLine().getStatusCode() == 200) {
			System.out.println("access granted");
			try {
				res_json = read_response(httpresponse.getEntity().getContent());
			} catch (UnsupportedOperationException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			System.out.println("access denied");
			System.out.println(httpresponse.getStatusLine().getStatusCode());
		}
		return res_json;
	}

	/**
	 * methode permettant d'envoyer une requete http GET avec une liste comme
	 * reponse
	 * 
	 * @param url
	 *            url demander
	 * @return object retourner par la requete sous forme de liste
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
		if (httpresponse.getStatusLine().getStatusCode() == 200) {
			try {
				res_json = read_response_array(httpresponse.getEntity()
						.getContent());
			} catch (UnsupportedOperationException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// System.out.println(res_json.toString());
		} else {
			System.out.println("error");
			System.out.println(httpresponse.getStatusLine().getReasonPhrase());
		}
		return res_json;
	}

	/**
	 * methode permettant d'envoyer une requete http POST
	 * 
	 * @param url
	 *            url demander
	 * @param body_args
	 *            argument a ajouter dans le corps(body) de la requete
	 * @return object contenant la reponse qui respecte le format json
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
		if (httpresponse.getStatusLine().getStatusCode() == 200) {
			try {
				res_json = read_response(httpresponse.getEntity().getContent());
			} catch (UnsupportedOperationException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("error");
			System.out.println(httpresponse.getStatusLine().getReasonPhrase());
		}

		return res_json;
	}

	/**
	 * methode permettant d'envoyer une requete http POST
	 * 
	 * @param url
	 *            url demander
	 * @param body_args
	 *            argument a ajouter dans le corps(body) de la requete
	 * @param HeaderName
	 *            Nom de l'entete
	 * @param HeaderValue
	 *            valeur de l'entete
	 * @return object contenant la reponse qui respecte le format json
	 */
	public static JSONObject post(String url, List<NameValuePair> body_args,
			String HeaderName, String HeaderValue) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		post.setEntity(new UrlEncodedFormEntity(body_args, Consts.UTF_8));
		// client_id:secret_id en base 64
		post.setHeader(HeaderName, HeaderValue);

		CloseableHttpResponse httpresponse = null;
		try {
			httpresponse = httpclient.execute(post);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject res_json = null;
		if (httpresponse.getStatusLine().getStatusCode() == 200) {
			System.out.println("access granted");
			try {
				res_json = read_response(httpresponse.getEntity().getContent());
			} catch (UnsupportedOperationException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("access denied");
		}

		return res_json;
	}

	/**
	 * methode permettant d'analyser la reponse recu (parsing)
	 * 
	 * @param r
	 *            reponse recu
	 * @return retourne la reponse sous forme de JSONObject
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

	/**
	 * methode permettant d'analyser la reponse recu (parsing)
	 * 
	 * @param r
	 *            reponse recu
	 * @return retourne la reponse sour forme de list JSONArray
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

	// un serveur se met en ecoute pour recuperer le code d'authorization
	public static String runServerToListen(int port) throws Exception {
		final int portNumber = port;
		System.out.println("Creating server socket on port " + portNumber);
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(portNumber);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		Socket socket = null;
		socket = serverSocket.accept();
		OutputStream os = socket.getOutputStream();
		BufferedReader br = null;
		br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String str = null;
		str = br.readLine();

		String response = "<html><h3>MERCI</h3></html>";
		PrintWriter out = new PrintWriter(socket.getOutputStream());
		out.println("HTTP/1.1 200 OK");
		out.println("Content-Type: text/html");
		out.println("Content-Length: " + response.length());
		out.println();
		out.println(response);
		out.flush();
		out.close();
		socket.close();

		return str;

	}
}
