package com.steve.rebroadcaster;
import com.savarese.rocksaw.net.RawSocket;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.savarese.vserv.tcpip.*;

import static com.savarese.rocksaw.net.RawSocket.getProtocolByName;

public class RebroadcastClient {

		public static int udpTransportPort = 65002;
		public static String broadCastAddress  = "0.0.0.0";
		public static final int HDHomeRunPort = 65001;
		private RawSocket broadcastSocket = null;
		private boolean keeprunning = true;
		private String unicastServerAddress = null;
		
		
		private List<InetAddress> listAllBroadcastAddresses() throws SocketException {
		    List<InetAddress> broadcastList = new ArrayList<>();
		    Enumeration<NetworkInterface> interfaces 
		      = NetworkInterface.getNetworkInterfaces();
		    while (interfaces.hasMoreElements()) {
		        NetworkInterface networkInterface = interfaces.nextElement();
		 
		        if (networkInterface.isLoopback() || !networkInterface.isUp()) {
		            continue;
		        }
		 
		        networkInterface.getInterfaceAddresses().stream() 
		          .map(a -> a.getBroadcast())
		          .filter(Objects::nonNull)
		          .forEach(broadcastList::add);

		        
		    }
		    Iterator <InetAddress> broadCastReport = broadcastList.iterator();
		    while ( broadCastReport.hasNext() ) {
		    	System.out.println(broadCastReport.next());
		    }
		    return broadcastList;
		}

		public void init()
		{
			try {
				//broadcastSocket  = new DatagramSocket(HDHomeRunPort, InetAddress.getByName(broadCastAddress));
				//broadcastSocket.setBroadcast(true);
				byte[] data = new byte[512];
				
				listAllBroadcastAddresses();
				
				broadcastSocket = new RawSocket();
				broadcastSocket.setBroadcast(0);
				broadcastSocket.open(RawSocket.PF_INET, getProtocolByName("udp") );
				
				System.out.println("Starting to listen....!" );

				InetAddress bcastAddr =  InetAddress.getByName("192.168.1.255");
				System.out.println(bcastAddr.getHostAddress());
				
				broadcastSocket.read(data, bcastAddr.getAddress() );
				System.out.println("GOT SOMETHING!" );
				
			}catch(Exception e) {
				e.printStackTrace();
				keeprunning=false;

				if ( broadcastSocket != null ) {
					try {
						broadcastSocket.close();
					}catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}


		public void startProcessing() {

			while (keeprunning) {
				//Keep listening for broadcast on the client-side
				//Keep sending those packets to the server side
			}

		}



		public static void main(String[] args) {

			// TODO Auto-generated method stub


			if ( args.length > 0 ) {
				//unicast Address on args 1
				RebroadcastClient bc = new RebroadcastClient();
				bc.setUnicastServerAddress(args[0]);
				bc.init();
				bc.startProcessing();
			} else {
				System.err.println("Usage: BroadcastCleint <udpserverIP> ");
			}
		}



		public String getUnicastServerAddress() {
			return unicastServerAddress;
		}



		public void setUnicastServerAddress(String unicastServerAddress) {
			this.unicastServerAddress = unicastServerAddress;
		}



}