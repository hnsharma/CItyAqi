package com.cityaqi.service;

import android.content.Context;

import com.cityaqi.database.DatabaseHelper;
import com.cityaqi.models.CityAqiData;
import com.cityaqi.models.DataUpdate;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import tech.gusavila92.websocketclient.WebSocketClient;

public class WebSocketClientAIQ {
	Context context;
	public WebSocketClientAIQ(Context context)
	{
		this.context  = context;
	}
	private WebSocketClient webSocketClient;
	public void createWebSocketClient() {
		URI uri;
	        try {
	            uri = new URI("ws://city-ws.herokuapp.com/");
	        }
	        catch (URISyntaxException e) {
	            e.printStackTrace();
	            return;
	        }

	        webSocketClient = new WebSocketClient(uri) {
	            @Override
	            public void onOpen() {
	                System.out.println("onOpen");
	               // webSocketClient.send("Hello, World!");
	            }

	            @Override
	            public void onTextReceived(String message) {
					Gson gson  = new Gson();
					ArrayList<CityAqiData> cityAqiData = gson.fromJson(message,new TypeToken< ArrayList< CityAqiData > >(){}.getType());
					DatabaseHelper databaseHelper = DatabaseHelper.getIntance(context);
					databaseHelper.insertCityAqi(cityAqiData);
					EventBus.getDefault().post(new DataUpdate());
	                System.out.println("onTextReceived  "+message);
	            }

	            @Override
	            public void onBinaryReceived(byte[] data) {
	            	System.out.println("onPingReceived  "+new String(data));
	            }

	            @Override
	            public void onPingReceived(byte[] data) {
	                System.out.println("onPingReceived  "+new String(data));
	            }

	            @Override
	            public void onPongReceived(byte[] data) {
	                System.out.println("onPongReceived");
	            }

	            @Override
	            public void onException(Exception e) {
	                System.out.println(e.getMessage());
	            }

	            @Override
	            public void onCloseReceived() {
	                System.out.println("onCloseReceived");
	            }
	        };

	        webSocketClient.setConnectTimeout(10000);
	        webSocketClient.setReadTimeout(60000);
	        
	        webSocketClient.enableAutomaticReconnection(5000);
	        webSocketClient.connect();
	}
	public void close() {
		webSocketClient.close();
	}

}
