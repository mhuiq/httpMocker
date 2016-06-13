package com.mhuiq.httpMocker;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class HttpRequset {
	private final static String HEADER = "HEADER";
	private final static String RESPONSE = "RESPONSE";
	
	private int connTmo ;
	private int respTmo;
	
	public HttpRequset(int connTmo, int respTmo) {
		this.connTmo = connTmo;
		this.respTmo = respTmo;
	}
	
	/**
	 * ��ָ��URL����GET����������
	 * 
	 * @param url
	 *            ���������URL
	 * @param param
	 *            ����������������Ӧ���� name1=value1&name2=value2 ����ʽ��
	 * @return URL ������Զ����Դ����Ӧ���
	 */
	public Map<String, Object> sendGet(String url, String param, String charsetName) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		try {
			if (param.length() > 0) {
				param = "?" + param;
			}
			String urlNameString = url + param;
			URL realUrl = new URL(urlNameString);
			// �򿪺�URL֮�������
			URLConnection connection = realUrl.openConnection();
			// ����ͨ�õ���������
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("Content-Type", "text/html;charset=" + charsetName);
			connection.setRequestProperty("Accept-Charset", charsetName);
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			connection.setConnectTimeout(connTmo);
			connection.setReadTimeout(respTmo);
			// ����ʵ�ʵ�����
			connection.connect();
			// ��ȡ������Ӧͷ�ֶ�
			resultMap.put(HEADER, connection.getHeaderFields());
			baos = new ByteArrayOutputStream();
			// ���� BufferedReader����������ȡURL����Ӧ
			is = connection.getInputStream();
			byte[] bytes = new byte[256];
			int len;
			while ((len = is.read(bytes)) != -1) {
				baos.write(bytes, 0, len);
			}
			resultMap.put(RESPONSE, baos.toString(charsetName));
		} catch (Exception e) {
			System.out.println("����GET��������쳣��" + e);
			e.printStackTrace();
		} finally {
			try {
				if (null != is) {
					is.close();
				}
				if (null != baos) {
					baos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultMap;
	}

	/**
	 * ��ָ�� URL ����POST����������
	 * 
	 * @param url
	 *            ��������� URL
	 * @param param
	 *            ����������������Ӧ���� name1=value1&name2=value2 ����ʽ��
	 * @return ������Զ����Դ����Ӧ���
	 */
	public Map<String, Object> sendPost(String url, String param, String charsetName) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		OutputStream os = null;
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		try {
			URL realUrl = new URL(url);
			// �򿪺�URL֮�������
			URLConnection conn = realUrl.openConnection();
			// ����ͨ�õ���������
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Content-Type", "text/html;charset=" + charsetName);
			conn.setRequestProperty("Accept-Charset", charsetName);
			// ����POST�������������������
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// ��ȡURLConnection�����Ӧ�������
			
			os = conn.getOutputStream();
			// �����������
			os.write(param.getBytes(charsetName));
			// flush������Ļ���
			os.flush();
			resultMap.put(HEADER, conn.getHeaderFields());
			// ����BufferedReader����������ȡURL����Ӧ
			is = conn.getInputStream();
			baos = new ByteArrayOutputStream();
			byte[] bytes = new byte[256];
			int len;
			while ((len = is.read(bytes)) != -1) {
				baos.write(bytes, 0, len);
			}
			resultMap.put(RESPONSE, baos.toString(charsetName));
		} catch (Exception e) {
			System.out.println("���� POST ��������쳣��" + e);
			e.printStackTrace();
		}
		// ʹ��finally�����ر��������������
		finally {
			try {
				if (null != baos) {
					baos.close();
				}
				if (null != os) {
					os.close();
				}
				if (null != is) {
					is.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return resultMap;
	}
}
