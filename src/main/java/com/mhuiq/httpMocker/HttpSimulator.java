package com.mhuiq.httpMocker;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.UIManager;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import com.mhuiq.httpMocker.listener.MouseListenerHandler;
import com.mhuiq.httpMocker.listener.TextFieldFocusListener;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class HttpSimulator {

	private JFrame frame;
	private JTextField hostField;	 	// ���������
	private JTextField portField; 		// �˿������
	private JComboBox<String> methodComboBox;	// ����ѡ���
	private JTextField urlField; 		// uri�����
	private JTextField connTmoField; 	// ���ӳ�ʱʱ�������
	private JTextField respTmoField; 	// ���ܳ�ʱʱ�������
	private JTextArea paraTextArea;  	// ���������
	private JTabbedPane tabbedPane; 
	private JTextArea headerTextArea;	// ��Ӧͷ�����
	private JTextArea respTextArea;		// ��Ӧ���������
	private JComboBox<String> charsetComboBox;

	private final static String POST = "POST";
	private final static String GET = "GET";
	
	private FocusListener textFieldFocusListener = new TextFieldFocusListener();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
			UIManager.put("RootPane.setupButtonVisible", false);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HttpSimulator window = new HttpSimulator();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public HttpSimulator() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Http����ģ����");
		frame.setResizable(false);
		frame.setBounds(100, 100, 475, 492);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Image image = null;
		try {
			image = ImageIO.read(this.getClass().getResourceAsStream("/logo/logo.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		frame.setIconImage(image);
		
		JPanel panel = new JPanel();
		panel.setName("panel");
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		hostField = new JTextField(ConstDef.HOST_TIP);
		hostField.setName(ConstDef.hostField);
		hostField.setBounds(10, 7, 119, 28);
		hostField.addFocusListener(textFieldFocusListener);
		
		panel.add(hostField);
		hostField.setColumns(10);
		
		portField = new JTextField(ConstDef.PORT_TIP);
		portField.addFocusListener(textFieldFocusListener);
		portField.setName(ConstDef.portField);
		portField.setBounds(154, 7, 69, 28);
		panel.add(portField);
		portField.setColumns(10);
		
		methodComboBox = new JComboBox<String>();
		methodComboBox.setBounds(241, 7, 73, 27);
		methodComboBox.addItem("GET");
		methodComboBox.addItem("POST");
		panel.add(methodComboBox);
		
		urlField = new JTextField(ConstDef.URL_TIP);
		urlField.addFocusListener(textFieldFocusListener);
		urlField.setBounds(10, 45, 119, 28);
		urlField.setName(ConstDef.urlField);
		panel.add(urlField);
		urlField.setColumns(10);
		
		JLabel lblParameters = new JLabel("Parameters:");
		lblParameters.setBounds(6, 81, 73, 16);
		panel.add(lblParameters);
		
		JLabel lblResponses = new JLabel("Responses:");
		lblResponses.setBounds(6, 223, 73, 16);
		panel.add(lblResponses);
		
		JButton button = new JButton("����");
		button.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
		button.setForeground(Color.white);
		button.setBounds(324, 47, 75, 23);
		button.addActionListener(sendListener);
		panel.add(button);
		
		connTmoField = new JTextField(ConstDef.CONNTMO_TIP);
		connTmoField.addFocusListener(textFieldFocusListener);
		connTmoField.setName(ConstDef.connTmoField);
		connTmoField.setBounds(154, 45, 69, 28);
		panel.add(connTmoField);
		connTmoField.setColumns(10);
		
		respTmoField = new JTextField(ConstDef.RESPTMO_TIP);
		respTmoField.addFocusListener(textFieldFocusListener);
		respTmoField.setName(ConstDef.respTmoField);
		respTmoField.setBounds(241, 45, 73, 28);
		panel.add(respTmoField);
		respTmoField.setColumns(10);
		
		JScrollPane jsp = new JScrollPane();
		jsp.setBounds(5, 98, 400, 121);
		panel.add(jsp);
		
		paraTextArea = new JTextArea();
		paraTextArea.setWrapStyleWord(true);
		paraTextArea.setLineWrap(true);
		paraTextArea.setColumns(100);
		jsp.setViewportView(paraTextArea);
		
		JScrollPane headerJsp = new JScrollPane();
		headerJsp.setBounds(6, 126, 446, 121);
		
		headerTextArea = new JTextArea();
		headerJsp.setViewportView(headerTextArea);
		
		
		JScrollPane respJsp = new JScrollPane();
		respJsp.setBounds(6, 126, 446, 121);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(6, 242, 400, 146);
		tabbedPane.add("Header", headerJsp);
		tabbedPane.add("Response", respJsp);
		
		respTextArea = new JTextArea();
		respTextArea.setWrapStyleWord(true);
		respTextArea.setLineWrap(true);
		respTextArea.setColumns(100);
		respJsp.setViewportView(respTextArea);
		
		panel.add(tabbedPane);
		
		charsetComboBox = new JComboBox<String>();
		charsetComboBox.setBounds(324, 8, 75, 25);
		charsetComboBox.addItem("UTF-8");
		charsetComboBox.addItem("GBK");
		panel.add(charsetComboBox);
		
	}
	
	private ActionListener sendListener = new ActionListener() {
		
		public void actionPerformed(ActionEvent e) {
			headerTextArea.setText("");
			respTextArea.setText("");
			try {
				String host = ConstDef.HOST_TIP.equals(hostField.getText().trim()) ? "" : hostField.getText().trim();
				if (host.length() < 1) {
					headerTextArea.append("�������Ʋ���Ϊ��\r\n");
					return;
				}
				String portStr = ConstDef.PORT_TIP.equals(portField.getText().trim()) ? "" : portField.getText().trim();
				int port = portStr.length() < 1 ? 80 : Integer.valueOf(portStr);
				String method = (String) methodComboBox.getSelectedItem();
				String urlStr = ConstDef.URL_TIP.equals(urlField.getText().trim()) ? "" : urlField.getText().trim();
				String connTmoStr = ConstDef.CONNTMO_TIP.equals(connTmoField.getText().trim()) ? "" : connTmoField.getText().trim();
				String respTmoStr = ConstDef.RESPTMO_TIP.equals(respTmoField.getText().trim()) ? "" : respTmoField.getText().trim();
				int connTmo = Integer.valueOf(connTmoStr.length() < 1 ? 0 : Integer.valueOf(connTmoStr));
				int respTmo = Integer.valueOf(respTmoStr.length() < 1 ? 0 : Integer.valueOf(respTmoStr));
				String paraStr = paraTextArea.getText().trim();
				HttpRequset request = new HttpRequset(connTmo, respTmo);
				StringBuilder sb = new StringBuilder();
				sb.append("http://").append(host).append(":").append(port).append((urlStr.length() < 1 ? "" : urlStr));
				Map<String, Object> resultMap;
				switch (method) {
				case GET:
					resultMap = request.sendGet(sb.toString(), paraStr, (String) charsetComboBox.getSelectedItem());
					break;
				case POST:
					resultMap = request.sendPost(sb.toString(), paraStr, (String) charsetComboBox.getSelectedItem());
					break;
				default :
					headerTextArea.append("��֧�ָ�Http����\r\n");
					return;
				}
				if (resultMap == null) {
					headerTextArea.append("����ʧ��\r\n");
					return;
				}
				Map<String, List<String>> headerMap = (Map<String, List<String>>) resultMap.get("HEADER");
				for (String key : headerMap.keySet()) {
					headerTextArea.append(key + " : " + headerMap.get(key) + "\r\n");
				}
				respTextArea.append(resultMap.get("RESPONSE") + "\r\n");
			} catch (Exception e1) {
				if (e1 instanceof NumberFormatException) {
					headerTextArea.append("�˿ںŻ�ʱʱ�䲻��Ϊ����������\r\n");
					return;
				} else {
					headerTextArea.append(e1.getMessage());
					return;
				}
				
			}
			
		}
	};
}
