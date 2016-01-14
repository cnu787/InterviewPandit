package com.testmyinterview.portal;

import com.pubnub.api.*;

import org.json.*;

public class VideoController {

	
	Pubnub pubnub = new Pubnub("demo", "demo");
private void test(){
	
	try {
		pubnub.subscribe("my_channel", new Callback() {
			
			
			@Override
		      public void connectCallback(String channel, Object message) {
		          pubnub.publish("my_channel", "Hello from the PubNub Java SDK", new Callback() {});
		      }
		 
		      @Override
		      public void disconnectCallback(String channel, Object message) {
		          System.out.println("SUBSCRIBE : DISCONNECT on channel:" + channel
		                     + " : " + message.getClass() + " : "
		                     + message.toString());
		      }
		 
		      public void reconnectCallback(String channel, Object message) {
		          System.out.println("SUBSCRIBE : RECONNECT on channel:" + channel
		                     + " : " + message.getClass() + " : "
		                     + message.toString());
		      }
		      @Override
		      public void successCallback(String channel, Object message) {
		          System.out.println("SUBSCRIBE : " + channel + " : "
		                     + message.getClass() + " : " + message.toString());
		      }
		 
		      @Override
		      public void errorCallback(String channel, PubnubError error) {
		          System.out.println("SUBSCRIBE : ERROR on channel " + channel
		                     + " : " + error.toString());
		      }
			
		});
	} catch (PubnubException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}}

public static void main(String [] args){
	
	VideoController obj=new VideoController();
	
	obj.test();
	
}
}
	