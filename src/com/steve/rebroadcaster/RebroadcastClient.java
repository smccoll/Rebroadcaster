package com.steve.rebroadcaster;
import com.savarese.rocksaw.net.RawSocket;
import org.savarese.vserv.tcpip.*;


public class RebroadcastClient {

		public static int udpTransportPort = 65002;
		public static String broadCastAddress  = "0.0.0.0";
		public static final int HDHomeRunPort = 65001;
		private RawSocket broadcastSocket = null;
		private boolean keeprunning = true;
		private String unicastServerAddress = null;


		public void init()
		{
			try {
				//broadcastSocket  = new DatagramSocket(HDHomeRunPort, InetAddress.getByName(broadCastAddress));
				//broadcastSocket.setBroadcast(true);
				broadcastSocket.open(RawSocket.PF_INET, IPPacket.PROTOCOL_UDP );

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