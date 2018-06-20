package com.hxht;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import com.google.gson.JsonObject;

public class Server extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JButton button;

	private MqttClient client;
	//服务器端口 账号 密码
	private String host = "tcp://127.0.0.1:61613";
	private String userName = "admin";
	private String passWord = "123";
	//
	private MqttTopic topic;
	private MqttMessage message;
	
	private String myTopic = "test/topic";
	//界面
	JTextArea editor1;
	JTextArea editor2;
	JLabel history;
	StringBuffer strBuf = null;

	public Server() {
		strBuf = new StringBuffer();
		try {
			//创建客户端
			client = new MqttClient(host, "Server", new MemoryPersistence());
			connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("draw container-----------");
		Container container = this.getContentPane();
		container.setLayout(new GridLayout(3, 2));
		panel = new JPanel();
		button = new JButton("发布话题");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				try {

					JsonObject obg = new JsonObject();
					obg.addProperty("title", editor1.getText());
					obg.addProperty("content", editor2.getText());
					long current = System.currentTimeMillis();
					obg.addProperty("id", current);
					
					String msg = obg.toString();
					message.setPayload(msg.getBytes("utf-8"));
					System.out.println("send========"+msg);
					strBuf.append("Had Send Id: "+current+ " "+msg+"\r\n");
					history.setText(strBuf.toString());
					//发布消息
					MqttDeliveryToken token = topic.publish(message);
					token.waitForCompletion();
					System.out.println(token.isComplete() + "========");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		panel.add(button);
		
		
		
		
		JPanel center = new JPanel();
		center.setLayout(new GridLayout(3, 2));
		
		
		JLabel label = new JLabel("标题");
		editor1 = new JTextArea("MQTT");
		editor1.setBackground(Color.WHITE);
		editor1.setBounds(0,  0, 100, 50);
		JPanel pane2 = new JPanel();
		pane2.add(label);
		pane2.add(editor1);
		
		JPanel pane4 = new JPanel();
		JLabel labe2 = new JLabel("内容");
		editor2 = new JTextArea("hnac");
		editor2.setBackground(Color.WHITE);
		editor2.setBounds(0,  0, 100, 50);

		JPanel pane3 = new JPanel();
		pane3.add(labe2);
		pane3.add(editor2);
		pane4.add(pane3);
		
		history = new JLabel("");

		center.add(label);center.add(editor1);
		center.add(labe2);center.add(editor2);
		
//		container.add(pane2, "North");
//		container.add(pane3, "Center");
		container.add(center, "North");
		container.add(panel, "Center");
		container.add(history, "South");
	}
	

	private void connect() {
		//设置连接参数
		MqttConnectOptions options = new MqttConnectOptions();
		options.setCleanSession(false);
		options.setUserName(userName);
		options.setPassword(passWord.toCharArray());
		// 设置超时时间
		options.setConnectionTimeout(10);
		// 设置会话心跳时间
		options.setKeepAliveInterval(20);
		try {
			//设置客户端回掉
			client.setCallback(new MqttCallback() {

				@Override
				public void connectionLost(Throwable cause) {
					System.out.println("connectionLost-----------");
				}

				@Override
				public void deliveryComplete(IMqttDeliveryToken token) {
					System.out.println("deliveryComplete---------"
							+ token.isComplete());
				}

				@Override
				public void messageArrived(String topic, MqttMessage arg1)
						throws Exception {
					System.out.println("messageArrived----------");

				}
			});

			topic = client.getTopic(myTopic);
			System.out.println("------topic=" + topic.toString());
			
			message = new MqttMessage();
			message.setQos(1);
			message.setRetained(true);
			System.out.println("------ratained状态" + message.isRetained());
			message.setPayload("msg-hnac".getBytes());
			//连接
			client.connect(options);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		Server s = new Server();
		s.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		s.setSize(600, 370);
		s.setLocationRelativeTo(null);
		s.setVisible(true);
	}
}
