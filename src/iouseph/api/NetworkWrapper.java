package iouseph.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLEncoder;
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

/**
 * classe NetworkWrapper pour faciliter l'envoie de requete http
 *
 * @author Marcial Lopez-Ferrada, Youssef Zemmahi, Aymen Zalila
 *
 */
public final class NetworkWrapper {

	static Thread MyServerThread;
	/**
	 * methode permettant d'envoyer une requete http GET
	 *
	 * @param url
	 *            url demander
	 * @return object contenant la reponse qui respecte le format json
	 */
	public NetworkWrapper() {
	}

	public static String encode(String s){
		try {
			return URLEncoder.encode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}

	public static JSONObject get(String url) {
		return get(url, null, null);
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
	public static JSONObject get(String url, String HeaderName, String HeaderValue) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet get = new HttpGet(url);
		if ((HeaderName != null) && (HeaderValue != null))
			get.setHeader(HeaderName, HeaderValue);

		CloseableHttpResponse httpresponse = null;
		try {
			httpresponse = httpclient.execute(get);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if (httpresponse.getStatusLine().getStatusCode() == 200) {
			try {
				return read_response_object(httpresponse.getEntity().getContent());
			} catch (UnsupportedOperationException | IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("error");
			System.out.println(httpresponse.getStatusLine().getStatusCode());
		}
		return null;
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
		HttpGet get = new HttpGet(url);

		CloseableHttpResponse httpresponse = null;
		try {
			httpresponse = httpclient.execute(get);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (httpresponse.getStatusLine().getStatusCode() == 200) {
			try {
				return read_response_array(httpresponse.getEntity().getContent());
			} catch (UnsupportedOperationException | IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("error");
			System.out.println(httpresponse.getStatusLine().getReasonPhrase());
		}
		return null;
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
		return post(url, body_args, null, null);
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
	public static JSONObject post(String url, List<NameValuePair> body_args, String HeaderName, String HeaderValue) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);//TODO encripter
		post.setEntity(new UrlEncodedFormEntity(body_args, Consts.UTF_8));
		// client_id:secret_id en base 64
		if ((HeaderName != null) && (HeaderValue != null))
			post.setHeader(HeaderName, HeaderValue);

		CloseableHttpResponse httpresponse = null;
		try {
			httpresponse = httpclient.execute(post);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (httpresponse.getStatusLine().getStatusCode() == 200) {
			System.out.println("access granted");
			try {
				return read_response_object(httpresponse.getEntity().getContent());
			} catch (UnsupportedOperationException | IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("access denied");
		}
		return null;
	}

	/**
	 * methode permettant d'analyser la reponse recu (parsing)
	 *
	 * @param r
	 *            reponse recu
	 * @return retourne la reponse sous forme de JSONObject
	 */
	private static String read_response(InputStream r) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(r));

		String inputLine;
		StringBuffer response = new StringBuffer();

		try {
			while ((inputLine = reader.readLine()) != null) {
				response.append(inputLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(response);
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response.toString();
	}

	private static JSONObject read_response_object(InputStream r) {
		return new JSONObject(read_response(r));
	}

	/**
	 * methode permettant d'analyser la reponse recu (parsing)
	 *
	 * @param r
	 *            reponse recu
	 * @return retourne la reponse sour forme de list JSONArray
	 */
	private static JSONArray read_response_array(InputStream r) {
		return new JSONArray(read_response(r));
	}

	// un serveur se met en ecoute pour recuperer le code d'authorization
	@SuppressWarnings("resource")
	public static void runServerToListen(int port, Object object, Method methodToInvoke )  {
		MyServerThread = new Thread(new Runnable() {

			public void run() {
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
				try {
					socket = serverSocket.accept();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					OutputStream os = socket.getOutputStream();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				BufferedReader br = null;
				try {
					br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String str = null;
				try {
					str = br.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				String response = "<html><h3>MERCI</h3></html>";
				PrintWriter out = null;
				try {
					out = new PrintWriter(socket.getOutputStream());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				out.println("HTTP/1.1 200 OK");
				out.println("Content-Type: text/html");
				out.println("Content-Length: " + response.length());
				out.println();
				out.println(response);
				out.flush();
				out.close();
				try {
					socket.close();
					InvokeMethod(object,methodToInvoke,str);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		MyServerThread.start();

	}
	static private void InvokeMethod(Object object ,Method method, String message)
	{
		try {
			method.invoke(object, message);
			stopServer();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	static private void stopServer() {
		System.out.println("server closed");
		MyServerThread.interrupt();
	}
}
