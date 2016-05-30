package bussiness;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class NetworkBussiness {

	private List<String> cookies;
	private HttpURLConnection conn;
	private final String charset = "UTF-8";
	private final String USER_AGENT = "Mozilla/5.0";
	CloseableHttpClient httpclient = HttpClients.createDefault();

	public static void main(String[] args) throws Exception {

		String url = "http://bachkhoagiasu.com/admin/public/login";

		NetworkBussiness http = new NetworkBussiness();

		// make sure cookies is turn on
		CookieHandler.setDefault(new CookieManager());

		String postParams = "username=admin&password=12345678";

		// 2. Construct above post's content and then send a POST request for
		// authentication
		http.sendPost(url, postParams, "");

		// page = http.GetPageContent(url);
		String addMember = "name=NguyenTri&order=1";
		// &title=&description=&keyword=&uploadthumb=&gioitinh=Nam&namhoc=Năm4&"
		// +
		// "khoahoc=Toán&nganhhoc=CNTT&order=&birthday=25/04/2016&dienthoai=01675202157&mssv=51204052&"
		// +
		// "email=tri@cse.hcmut.edu&daihoc=BK&youtobe=&giasu=&chuyen=&diachi=P801.8-KTXBK,497HoàHảo,P7,Q10,TP.HCM&"
		// +
		// "facebook=xxx&thanhtich=&quequan=&giongnoi=Bắc&display=0&display=1thongtinkhac=&detail=";
		url = "http://bachkhoagiasu.com/admin/giasu/add";
		http.sendPost(url, addMember, "");
	}

	public boolean register(Student student) throws Exception {

		String url = "http://bachkhoagiasu.com/admin/public/login";
		// make sure cookies is turn on
		CookieHandler.setDefault(new CookieManager());
		getPageContent(url);

		String postParams = "username=admin&password=12345678";

		// 2. Construct above post's content and then send a POST request for
		// authentication
		sendPost(url, postParams, "");

		// page = http.GetPageContent(url);
		String addMember = student.buildParamPost();
		// &title=&description=&keyword=&uploadthumb=&gioitinh=Nam&namhoc=Năm4&"
		// +
		// "khoahoc=Toán&nganhhoc=CNTT&order=&birthday=25/04/2016&dienthoai=01675202157&mssv=51204052&"
		// +
		// "email=tri@cse.hcmut.edu&daihoc=BK&youtobe=&giasu=&chuyen=&diachi=P801.8-KTXBK,497HoàHảo,P7,Q10,TP.HCM&"
		// +
		// "facebook=xxx&thanhtich=&quequan=&giongnoi=Bắc&display=0&display=1thongtinkhac=&detail=";
		url = "http://bachkhoagiasu.com/admin/giasu/add";
		if (sendPost(url, addMember, student.avatarUrl)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean sendPost(String url, String postParams, String avatarUrl)
			throws Exception {
		String charset = "UTF-8";

		String boundary = Long.toHexString(System.currentTimeMillis()); // Just
																		// generate
																		// some
																		// unique
																		// random
																		// value.
		String CRLF = "\r\n"; // Line separator required by multipart/form-data.

		URL obj = new URL(url);
		conn = (HttpURLConnection) obj.openConnection();

		// Acts like a browser
		conn.setUseCaches(false);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Host", "accounts.google.com");
		conn.setRequestProperty("User-Agent", USER_AGENT);
		conn.setRequestProperty("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		for (String cookie : this.cookies) {
			conn.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
		}
		conn.setRequestProperty("Connection", "keep-alive");
		// conn.setRequestProperty("Referer",
		// "https://accounts.google.com/ServiceLoginAuth");
		conn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		conn.setRequestProperty("Content-Length",
				Integer.toString(postParams.length()));

		conn.setDoOutput(true);
		conn.setDoInput(true);

		// Send file
		// if (avatarUrl.compareTo("")!=0) {
		// File binaryFile = new File(avatarUrl);
		// OutputStream output = conn.getOutputStream();
		// PrintWriter writer = new PrintWriter(new OutputStreamWriter(output,
		// charset), true);
		// writer.append("--" + boundary).append(CRLF);
		// writer.append(
		// "Content-Disposition: form-data; name=\"binaryFile\"; filename=\""
		// + binaryFile.getName() + "\"").append(CRLF);
		// writer.append("Content-Type: " +
		// URLConnection.guessContentTypeFromName(binaryFile.getName())).append(CRLF);
		// writer.append("Content-Transfer-Encoding: binary").append(CRLF);
		// writer.append(CRLF).flush();
		// Files.copy(binaryFile.toPath(), output);
		// output.flush(); // Important before continuing with writer!
		// writer.append(CRLF).flush(); // CRLF is important! It indicates end
		// of boundary.
		// //writer.append("--" + boundary + "--").append(CRLF).flush();
		// }
		// Send post request
		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		wr.writeBytes(postParams);
		wr.flush();
		wr.close();

		int responseCode = conn.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + postParams);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		System.out.println(response.toString());
		if (responseCode == 200) {
			return true;
		} else {
			return false;
		}
	}

	private String getPageContent(String url) throws Exception {

		URL obj = new URL(url);
		conn = (HttpURLConnection) obj.openConnection();

		// default is GET
		conn.setRequestMethod("GET");

		conn.setUseCaches(false);

		// act like a browser
		conn.setRequestProperty("User-Agent", USER_AGENT);
		conn.setRequestProperty("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		if (cookies != null) {
			for (String cookie : this.cookies) {
				conn.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
			}
		}
		int responseCode = conn.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// Get the response cookies
		setCookies(conn.getHeaderFields().get("Set-Cookie"));

		return response.toString();

	}

	public List<String> getCookies() {
		return cookies;
	}

	public void setCookies(List<String> cookies) {
		this.cookies = cookies;
	}

	public NetworkBussiness() {

		// login();
	}

	public void login() {
		String url = "http://bachkhoagiasu.com/admin/public/login";
		HttpPost httpPost = new HttpPost(url);

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", "admin"));
		nvps.add(new BasicNameValuePair("password", "12345678"));
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CloseableHttpResponse response2 = null;
		try {
			response2 = httpclient.execute(httpPost);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			System.out.println(response2.getStatusLine());
			HttpEntity entity2 = response2.getEntity();
			// do something useful with the response body
			// and ensure it is fully consumed
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response2.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			System.out.println(result.toString());

			EntityUtils.consume(entity2);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				response2.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void checkLogin() throws ClientProtocolException, IOException {
		HttpGet httpGet = new HttpGet("http://bachkhoagiasu.com/admin");
		CloseableHttpResponse response1 = httpclient.execute(httpGet);
		// The underlying HTTP connection is still held by the response object
		// to allow the response content to be streamed directly from the
		// network socket.
		// In order to ensure correct deallocation of system resources
		// the user MUST call CloseableHttpResponse#close() from a finally
		// clause.
		// Please note that if response content is not fully consumed the
		// underlying
		// connection cannot be safely re-used and will be shut down and
		// discarded
		// by the connection manager.
		try {
			System.out.println(response1.getStatusLine());
			HttpEntity entity1 = response1.getEntity();
			// do something useful with the response body
			// and ensure it is fully consumed
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response1.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			System.out.println(result.toString());

			EntityUtils.consume(entity1);
		} finally {
			response1.close();
		}
	}

	public void addNewMember(Student student) {
		String url = "http://bachkhoagiasu.com/admin/giasu/add";
		HttpPost httpPost = new HttpPost(url);

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("name", student.name));
		nvps.add(new BasicNameValuePair("gioitinh",student.gioitinh));
		nvps.add(new BasicNameValuePair("namhoc",student.nam));
		nvps.add(new BasicNameValuePair("nganhhoc",student.khoa));
		nvps.add(new BasicNameValuePair("birthday",student.ngaysinh));
		nvps.add(new BasicNameValuePair("dienthoai",student.sdt));
		nvps.add(new BasicNameValuePair("mssv",student.mssv));
		nvps.add(new BasicNameValuePair("email",student.email));
		nvps.add(new BasicNameValuePair("youtobe",student.youtube));
		nvps.add(new BasicNameValuePair("diachi",student.diachi));
		nvps.add(new BasicNameValuePair("facebook",student.fb));
		nvps.add(new BasicNameValuePair("thanhtich",student.thanhtich));
		nvps.add(new BasicNameValuePair("quequan",student.quequan));
		nvps.add(new BasicNameValuePair("monhoc[]",student.daymon));
		nvps.add(new BasicNameValuePair("giongnoi",student.giongnoi));
		nvps.add(new BasicNameValuePair("thongtinkhac",student.thongtinkhac));
		nvps.add(new BasicNameValuePair("order",student.order));
		
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(httpPost);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			System.out.println(response.getStatusLine());
			HttpEntity entity2 = response.getEntity();
			// do something useful with the response body
			// and ensure it is fully consumed
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			System.out.println(result.toString());

			EntityUtils.consume(entity2);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}