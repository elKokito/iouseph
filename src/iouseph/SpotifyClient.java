package iouseph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.sun.corba.se.impl.orbutil.RepositoryIdUtility;

public class SpotifyClient {

	private String host = "https://accounts.spotify.com";
	private String AuthPath = "/authorize/?";
	private String TokenPath = "/api/token";
	private String client_id = "ccb24bc509974a72babd14e92902f816";
	private String client_secret = "9e85225cb1324ee1bb7fa32be121a96c";
	private String redirect_uri = "http://localhost:8888/callback";
	private String access_token ;
	private String token_type ;
	private String refresh_token;
	private String code_retrieved;

	public void retreive_token() throws Exception {
		String url = host + AuthPath;
		String scope="playlist-read-private playlist-read-collaborative playlist-modify-public " +
		"playlist-modify-private streaming user-follow-modify user-follow-read user-library-read " +
		"user-library-modify user-read-private user-read-birthdate user-read-email";
		List<NameValuePair> body_args = new ArrayList<NameValuePair>();
		body_args.add(new BasicNameValuePair("client_id", client_id));
		body_args.add(new BasicNameValuePair("response_type", "code"));
		body_args.add(new BasicNameValuePair("redirect_uri", redirect_uri));
		body_args.add(new BasicNameValuePair("scope", "playlist-read-private user-read-private user-read-email "));
		String paramString = URLEncodedUtils.format(body_args, "utf-8");

		url+=paramString;
		// redirect
		System.out.println(url);
		java.awt.Desktop.getDesktop().browse(new URI(url));
		code_retrieved=null;
		this.waitForauthorizationCode();
	}

	void getProfile() throws ClientProtocolException, IOException {
		String url = "https://api.spotify.com/v1/me";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet get = new HttpGet(url);

		System.out.println(access_token);

		get.setHeader("Authorization", "Bearer " + access_token);

		CloseableHttpResponse httpresponse = httpclient.execute(get);
		JSONObject res_json1 = null;
		if (httpresponse.getStatusLine().getStatusCode() == 200) {
			System.out.println("access granted");
			res_json1 = read_response(httpresponse.getEntity().getContent());
			System.out.println(res_json1);
		} else {
			System.out.println(httpresponse.getStatusLine().getStatusCode());
			System.out.println("access denied");
		}
	}

	// un serveur se met en ecoute pour recuperer le code d'authorization
	void waitForauthorizationCode() throws Exception {
				final int portNumber = 8888;
				System.out.println("Creating server socket on port " + portNumber);
				ServerSocket serverSocket = null;
					try {
						serverSocket = new ServerSocket(portNumber);
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}

				while (code_retrieved==null) {
					Socket socket = null;
				
						socket = serverSocket.accept();

						OutputStream os = socket.getOutputStream();

					BufferedReader br = null;
						br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					String str = null;
						str = br.readLine();

					//System.out.println("Message : " + str);
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
	

					//System.out.println("Message received :" + str);
					if(code_retrieved==null)
					{
						code_retrieved=str;
						requestRefrechAndAccessToken();
					}
				}
			}


	private void requestRefrechAndAccessToken() throws Exception
	{
		// recuperation de la partie qui nous importe 
		// format du code GET /callback?code=AQA1Bw3lyz_oJU3oWBaRPIgQUkCiWFnLLecNrbROeQwJWKl92l9p0XdVqRvXguxjiYcceaceoF_oMNTqMdV6O6SQwnOFq4qXFdPfEEI-jcjk9tkYwNJMwNO8-j_ufyS543p_LZGK7ix7UMlz55_8A-S6q1Phrso3eUa5QLpEcWR3zba8VJf34F0UJp5G0ntCrw18MJTKECU5nS0JxWtkj0yT2PWthr54jgF9BdNj6SAit20M7-x3Qg HTTP/1.1
		String[] parts = code_retrieved.split("=");
		parts= parts[1].split(" ");
		code_retrieved=parts[0];
		System.out.println("code received = " + code_retrieved);	
		
		String url = host + TokenPath ;//+ "?user-read-private%20user-read-email";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		List<NameValuePair> body_args = new ArrayList<NameValuePair>();
		body_args.add(new BasicNameValuePair("grant_type", "authorization_code"));
		body_args.add(new BasicNameValuePair("redirect_uri",redirect_uri));
		body_args.add(new BasicNameValuePair("code",code_retrieved));
		post.setEntity(new UrlEncodedFormEntity(body_args, Consts.UTF_8));
		
		// client_id:secret_id en base 64
		post.setHeader("Authorization",
				"Basic Y2NiMjRiYzUwOTk3NGE3MmJhYmQxNGU5MjkwMmY4MTY6OWU4NTIyNWNiMTMyNGVlMWJiN2ZhMzJiZTEyMWE5NmM=");

		CloseableHttpResponse httpresponse = httpclient.execute(post);
		JSONObject res_json = null;
		if (httpresponse.getStatusLine().getStatusCode() == 200) {
			System.out.println("access granted");
			res_json = read_response(httpresponse.getEntity().getContent());
			access_token= res_json.getString("access_token");
			refresh_token=res_json.getString("refresh_token");
			System.out.println(res_json.toString());
		} else {
			System.out.println("access denied");
		}
		
	}

	private JSONObject read_response(InputStream r) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(r));

		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = reader.readLine()) != null) {
			response.append(inputLine);
		}
		reader.close();
		JSONObject res_json = new JSONObject(response.toString());
		return res_json;
	}
}
