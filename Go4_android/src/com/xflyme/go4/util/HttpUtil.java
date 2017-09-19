package com.xflyme.go4.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.util.Pair;
import android.xutil.Singlton;

/**
 * 
 * @Description:http工具类
 * @author:wennan
 * @see:
 * @since:
 * @copyright © ciyun.cn
 * @Date:2014年8月18日
 */
public class HttpUtil {

	private static final int TIME_OUT = 30000;

	private static final String CHARSET = "UTF-8";

	private long totalSize;

	public static HttpUtil getInstance() {
		return Singlton.getInstance(HttpUtil.class);
	}

	/**
	 * 监听服务器状态是否正常的接口
	 * 
	 * @Description:
	 * @author: huangyongxing
	 * @see:
	 * @since:
	 * @copyright © 35.com
	 * @Date:2012-3-15
	 */
	public interface IServerStateListener {

		public void serverState(boolean canUse);
	}

	private IServerStateListener serverListener;

	public void addServerListener(IServerStateListener serverListener) {
		this.serverListener = serverListener;
	}

	public void removeServerListener() {
		this.serverListener = null;
	}

	/**
	 * 提交POST请求
	 * 
	 * @Description:
	 * @param url
	 * @param params
	 * @return
	 * @throws IOException
	 * @see:
	 * @since:
	 * @author: huangyongxing
	 * @date:2012-8-8
	 */
	private String postMethod(String url, List<NameValuePair> params)
			throws IOException {

		HttpURLConnection conn = getConnection(url);
		conn.setRequestMethod("POST");
		// Post 请求不能使用缓存
		conn.setUseCaches(false);

		conn.setRequestProperty(" Content-Type ",
				" application/x-www-form-urlencoded ");
		// 连接，从postUrl.openConnection()至此的配置必须要在 connect之前完成，
		// 要注意的是connection.getOutputStream()会隐含的进行调用 connect()，所以这里可以省略
		// connection.connect();
		DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		// 正文内容其实跟get的URL中'?'后的参数字符串一致
		StringBuffer buffer = new StringBuffer();
		int i = 0;
		for (NameValuePair value : params) {
			buffer.append(i == 0 ? "" : "&");
			buffer.append(value.getName());
			buffer.append("=");
			if (!StringUtil.isEmpty(value.getValue(), true)) {
				buffer.append(URLEncoder.encode(value.getValue(), CHARSET));
			}
			i++;
		}
		// DataOutputStream.writeBytes将字符串中的16位的 unicode字符以8位的字符形式写道流里面
		out.writeBytes(buffer.toString());
		out.flush();
		out.close(); // flush and close

		String result = null;
		if (conn.getResponseCode() == 200) {
			if ("gzip".equalsIgnoreCase(conn.getContentEncoding())) {
				result = requestResult(conn.getInputStream(), true);
			} else {
				result = requestResult(conn.getInputStream(), false);
			}
		} else if (serverListener != null) {
			serverListener.serverState(false);
		}
		conn.disconnect();

		return result;
	}

	/**
	 * 提交GET请求，不用httpurlconnection是为了和4.0兼容
	 * 
	 * @Description:
	 * @param url
	 * @param params
	 * @return
	 * @throws IOException
	 * @see:
	 * @since:
	 * @author: huangyongxing
	 * @date:2012-8-8
	 */
	private String getMethod(String url, List<NameValuePair> params)
			throws IOException {
		StringBuffer buffer = new StringBuffer();
		int i = 0;
		// 拼音url
		for (NameValuePair value : params) {
			buffer.append(i == 0 ? "?" : "&");
			buffer.append(value.getName());
			buffer.append("=");
			buffer.append(URLEncoder.encode(value.getValue(), CHARSET));
			i++;
		}
		HttpParams httpParams = new BasicHttpParams();
		// 设置超时时间
		HttpConnectionParams.setConnectionTimeout(httpParams, TIME_OUT);
		HttpConnectionParams.setSoTimeout(httpParams, TIME_OUT);
		HttpClient client = new DefaultHttpClient(httpParams);
		HttpGet get = new HttpGet(url + buffer.toString());
		HttpResponse response = client.execute(get);

		String result = null;
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			HttpEntity entity = response.getEntity();
			Header header = entity.getContentEncoding();
			if (header == null || !"gzip".equalsIgnoreCase(header.getValue())) {
				result = requestResult(entity.getContent(), false);
			} else {
				result = requestResult(entity.getContent(), true);
			}
		} else if (serverListener != null) {
			serverListener.serverState(false);
		}
		return result;
	}

	/**
	 * 解析服务器返回数据
	 * 
	 * @Description:
	 * @param is
	 * @param isGzip
	 * @return
	 * @throws IOException
	 * @see:
	 * @since:
	 * @author: huangyongxing
	 * @date:2012-8-8
	 */
	private String requestResult(InputStream is, boolean isGzip)
			throws IOException {
		BufferedReader bufferedReader = null;
		StringBuilder builder = new StringBuilder();
		try {
			if (isGzip) {
				is = new GZIPInputStream(is);
			}
			bufferedReader = new BufferedReader(new InputStreamReader(is,
					CHARSET));
			// 读取服务器返回数据，转换成BufferedReader
			for (String s = bufferedReader.readLine(); s != null; s = bufferedReader
					.readLine()) {
				builder.append(s);
			}
		} finally {
			is.close();
			bufferedReader.close();
		}
		return builder.toString();
	}

	/**
	 * 发送请求
	 * 
	 * @Description:
	 * @param url
	 *            目标url
	 * @param params
	 *            参数
	 * @param isPost
	 *            是否为Post提交
	 * @return
	 * @see:
	 * @since:
	 * @author: huangyongxing
	 * @date:2012-6-29
	 */
	public String sendRequest(String url, List<NameValuePair> params,
			boolean isPost) {
		String result = null;
		// 服务器是否正常
		boolean isServerOk = false;
		try {
			if (isPost) {
				result = postMethod(url, params);
			} else {
				result = getMethod(url, params);
			}
			if (result != null) {
				isServerOk = true;
			}
		} catch (UnsupportedEncodingException e) {
			isServerOk = false;
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			isServerOk = false;
			e.printStackTrace();
		} catch (IOException e) {
			isServerOk = false;
			e.printStackTrace();
		} finally {

			if (serverListener != null) {
				// 广播服务器状态
				serverListener.serverState(isServerOk);
			}
		}

		return result;
	}

	/**
	 * 下载图片（头像）
	 * 
	 * @Description:
	 * @param url
	 * @return
	 * @see:
	 * @since:
	 * @author: huangyongxing
	 * @date:2012-6-29
	 */
	public byte[] downLoadPhoto(String url) {

		ByteArrayOutputStream baos = null;
		InputStream is = null;

		try {
			URL imgUrl = new URL(url.replace('\\', '/'));
			HttpURLConnection conn = (HttpURLConnection) imgUrl
					.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			is = conn.getInputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			baos = new ByteArrayOutputStream();
			while ((len = is.read(buffer, 0, 1024)) > 0) {
				baos.write(buffer, 0, len);
			}
			return baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (baos != null) {
					baos.close();
					baos = null;
				}
				if (is != null) {
					is.close();
					is = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return null;
	}

	private void addStartBoundary(ByteArrayOutputStream bos, String boundaryStr)
			throws IOException {
		bos.write("--".getBytes(CHARSET));
		bos.write(boundaryStr.getBytes(CHARSET));
	}

	private void addEndBoundary(ByteArrayOutputStream bos, String boundaryStr)
			throws IOException {
		bos.write("--".getBytes(CHARSET));
		bos.write(boundaryStr.getBytes(CHARSET));
		bos.write("--".getBytes(CHARSET));
	}

	private void addEndLine(ByteArrayOutputStream bos) throws IOException {
		bos.write("\n".getBytes(CHARSET));
	}

	/*
	 * 
	 * 
	 * Content-disposition: attachment; filename="file2.gif" Content-type:
	 * image/gif Content-Transfer-Encoding: binary
	 */
	private void addFilePart(ByteArrayOutputStream bos, String boundaryStr,
			String fieldname, String filename, byte[] fileData)
			throws IOException {
		addStartBoundary(bos, boundaryStr);
		addEndLine(bos);
		// String disp = "Content-Disposition: form-data; name=\"" + fieldname +
		// "\"; filename=\"" + filename
		// + "\"";
		fieldname = "image";
		String disp = "Content-Disposition: form-data; name=\"" + fieldname
				+ "\"; filename=\"abc.jpg\"";
		bos.write(disp.getBytes("UTF-8"));
		addEndLine(bos);
		String send = "Content-Type:image/png";// ,image/jpeg,image/gif,image/png
		bos.write(send.getBytes("UTF-8"));
		addEndLine(bos);
		String bin = "Content-Transfer-Encoding: binary";
		bos.write(bin.getBytes("UTF-8"));
		addEndLine(bos);
		addEndLine(bos);
		bos.write(fileData);
		addEndLine(bos);
	}

	private void addStringPart(ByteArrayOutputStream bos, String boundaryStr,
			String fieldname, String fieldvalue) throws IOException {
		addStartBoundary(bos, boundaryStr);
		addEndLine(bos);
		String disp = "Content-Disposition: form-data; name=\"" + fieldname
				+ "\"";
		bos.write(disp.getBytes("UTF-8"));
		addEndLine(bos);
		String send = "Content-Type:text/plain; charset=UTF-8";
		bos.write(send.getBytes("UTF-8"));
		addEndLine(bos);
		// String bin = "Content-Transfer-Encoding: 8bit";
		// bos.write(bin.getBytes("UTF-8"));
		// addEndLine(bos);
		addEndLine(bos);
		bos.write(fieldvalue.getBytes("UTF-8"));
		addEndLine(bos);
	}

	/**
	 * http post 上传文件
	 * 
	 * @param url
	 *            地址
	 * @param filePath
	 *            文件路径
	 * @param body
	 *            数据
	 * @return
	 */
	public String upload(String url, String[] filePath, String body) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);

		MultipartEntity mulentity = new MultipartEntity(
				HttpMultipartMode.BROWSER_COMPATIBLE, null,
				Charset.forName("UTF-8"));

		try {
			mulentity.addPart("body",new StringBody(body, Charset.forName("UTF-8")));
			if (filePath != null) {// TODO
				for (int i = 0; i < filePath.length; i++) {
					File f = new File(filePath[i]);
					CLog.e("file", filePath[i]);
					if (f.exists()) {
						mulentity.addPart("file" + i, new FileBody(f));
						totalSize = mulentity.getContentLength();
					}
				}
			}

			httpPost.setEntity(mulentity);
			HttpResponse response;
			response = httpclient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				/*
				 * if(file.exists()){ file.delete(); }
				 */
				String result = EntityUtils.toString(response.getEntity(),
						"UTF-8");
				CLog.e("result==", result);
				return result;
			}
			return null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 上传数据
	 * 
	 * @param url1
	 *            地址
	 * @param body
	 *            数据字符串
	 * @return
	 */
	public int upLoadData(String url1, String body) {
		int code = -1;
		try {
			URL url = new URL(url1);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setReadTimeout(5000);
			conn.setConnectTimeout(5000);
			// 添加post头里特有的两个属性
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length", body.length() + "");
			// 添加流，通过流把数据写给服务器
			// 设置打开流
			conn.setDoOutput(true);
			// 获取输出流
			OutputStream os = conn.getOutputStream();
			os.write(body.getBytes());
			code = conn.getResponseCode();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return code;

	}

	// rfc2616 rfc1867
	/**
	 * 以multipart形式上传文件或字符串<br/>
	 * fileFieldName/filename/fileData 是上传文件用的, 可以为null(不上传文件的情况下)<br/>
	 * fields 上传键值对用的, 可以为null.<br/>
	 * 但是, 文件或字符串不能都是null-----也就是没有上传内容, 此时没有意义<br/>
	 * 
	 * @param url
	 * @param fileFieldName
	 *            要上传文件的域名(field name)
	 * @param filename
	 *            建议的文件名(file name)
	 * @param fileData
	 *            文件数据
	 * @param fields
	 *            字符串键值对
	 * @return server返回的http代码和数据
	 */
	public Pair<Integer, String> httpMultiPartPost(String url,
			String fileFieldName, String filename, byte[] fileData,
			String[] fields) {
		HttpURLConnection con = null;
		int code = 404;
		try {
			String boundaryStr = "12343dfdssfdsfdsfd";
			URL u = new URL(url);
			con = (HttpURLConnection) u.openConnection();

			con.setRequestMethod("POST");
			con.setRequestProperty("connection", "keep-alive");
			con.setRequestProperty("Charsert", "UTF-8");
			con.setRequestProperty("Cache-Control", "no-cache");
			// con.setRequestProperty("Content-Length",String.valueOf(bos.size()));
			con.setRequestProperty("Content-Type",
					"multipart/form-data; boundary=" + boundaryStr);
			con.setDoInput(true);
			con.setDoOutput(true);

			con.setConnectTimeout(TIME_OUT);
			con.setReadTimeout(TIME_OUT);

			DataOutputStream outStream = new DataOutputStream(
					con.getOutputStream());
			ByteArrayOutputStream bos = new ByteArrayOutputStream(
					fileData.length + 2046);

			if (fields != null) {
				for (String value : fields) {
					addStringPart(bos, boundaryStr, null, value);
				}
			}
			if (fileData != null && fileData.length > 0) {
				addFilePart(bos, boundaryStr, fileFieldName, filename, fileData);
			}
			addEndBoundary(bos, boundaryStr);
			// addEndLine(bos);// 可能需要注释掉这行, 调试的时候
			bos.writeTo(outStream);
			bos.flush();
			outStream.flush();
			code = con.getResponseCode();
			if (code == 200) {

				InputStream is = con.getInputStream();

				if ("gzip".equalsIgnoreCase(con.getContentEncoding())) {
					return Pair.create(code, unGzip(is));
				}

				byte[] buffer = new byte[1024];
				ByteArrayOutputStream os = new ByteArrayOutputStream(1024);
				int readsize = 0;
				while ((readsize = is.read(buffer)) >= 0) {
					os.write(buffer, 0, readsize);
				}
				return Pair.create(code, new String(buffer, "UTF-8"));
			} else {
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}
		return Pair.create(code, null);
	}

	private String unGzip(InputStream inputStream) {
		StringBuilder builder = new StringBuilder();
		try {
			// 对服务器返回数据进行GZIP解压
			InputStream in = new GZIPInputStream(inputStream);

			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(in, "UTF-8"));
			// 读取服务器返回数据，转换成BufferedReader
			for (String s = bufferedReader.readLine(); s != null; s = bufferedReader
					.readLine()) {
				builder.append(s);
			}
		} catch (Exception e) {
		}

		return builder.toString();
	}

	/**
	 * 请求服务器，返回状态码
	 * 
	 * @Description:
	 * @param url
	 * @param params
	 * @return
	 * @see:
	 * @since:
	 * @author: huangyongxing
	 * @date:2012-6-29
	 */
	public int postToServerGetStatsCode(String url, List<NameValuePair> params) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpResponse httpResponse = null;
		HttpPost httpPost = new HttpPost(url);
		// 提交参数，UTF-8编码
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			httpResponse = httpClient.execute(httpPost);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 服务器返回状态码
		int res = httpResponse.getStatusLine().getStatusCode();
		CLog.i("http code:", res + "");
		return res;
	}

	private HttpURLConnection getConnection(String uri) {
		HttpURLConnection httpConn = null;
		URL url = null;
		try {
			url = new URL(uri);
			httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setConnectTimeout(TIME_OUT);
			httpConn.setReadTimeout(TIME_OUT);
			// 打开读写属性，默认均为false
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);
			httpConn.setInstanceFollowRedirects(true);
		} catch (IOException ie) {
			ie.printStackTrace();
		}
		return httpConn;
	}

	/**
	 * http post
	 * 
	 * @param url
	 *            请求地址
	 * @param nameValuePair
	 *            数据
	 * @return
	 * @throws Exception
	 */
	public String sendDataToServer(String url, List<NameValuePair> nameValuePair)
			throws Exception {

		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, TIME_OUT);
		HttpConnectionParams.setSoTimeout(httpParameters, TIME_OUT);
		DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);

		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair, CHARSET));
		int res = 0;

		HttpResponse httpResponse = httpClient.execute(httpPost);
		res = httpResponse.getStatusLine().getStatusCode();
		if (res == 200) {
			String result = EntityUtils.toString(httpResponse.getEntity());
			CLog.d("result==", result);
			return result;

		}

		return null;
	}

	/**
	 * http post
	 * 
	 * @param url
	 *            请求地址
	 * @param body
	 *            数据
	 * @return
	 * @throws Exception
	 */
	public String sendDataToServer(String url, String body) throws Exception {

		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("Content-Type", "application/json");
		ByteArrayEntity postdata = new ByteArrayEntity(body.getBytes());
		httpPost.setEntity(postdata);

		CLog.d("request==", EntityUtils.toString(postdata));
		int res = 0;

		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, TIME_OUT);
		HttpConnectionParams.setSoTimeout(httpParameters, TIME_OUT);

		DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
		HttpResponse httpResponse = httpClient.execute(httpPost);
		res = httpResponse.getStatusLine().getStatusCode();
		if (res == 200) {
			String result = EntityUtils.toString(httpResponse.getEntity());
			CLog.d("result==", result);
			return result;

		}
		return null;
	}
}
