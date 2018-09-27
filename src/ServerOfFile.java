import java.io.*;
import java.net.*;

public class ServerOfFile {

	static final int PORT = 8000;

	public static void main(String[] args) throws Exception {

		// create input / output UDP socket
		try (DatagramSocket socket = new DatagramSocket(PORT)) {

			for (;;) { // server endless loop
				// wait for an incoming datagram
				byte[] buffer = new byte[65536];
				DatagramPacket echoRequest = new DatagramPacket(buffer, buffer.length);
				// get it
				socket.receive(echoRequest);
				// get UDP datagram payload
				byte[] echoRequestData = echoRequest.getData();
				int echoRequestLength = echoRequest.getLength();
				int flag = 0;
				File file = new File("c:\\Users\\USER\\Desktop\\recebido.txt");
				try (FileOutputStream fout = new FileOutputStream(file)) {

					while (echoRequest.getLength() > 0) {
						socket.receive(echoRequest);
						System.out.println("im here");
						echoRequestData = echoRequest.getData();
						echoRequestLength = echoRequest.getLength();
						int sizeReceived = echoRequestLength;
						fout.write(echoRequestData);
						Thread.sleep(1);
						System.out.println("Recebi: " + sizeReceived);
						flag++;
					}
					if (flag > 0) {
						String doneD = "File received";
						System.out.println(doneD);
						byte[] byteArray = doneD.getBytes();
						DatagramPacket echoReply = new DatagramPacket(byteArray, byteArray.length);
						// as well as destination IP address and port
						echoReply.setAddress(echoRequest.getAddress());
						echoReply.setPort(echoRequest.getPort());
						// send reply
						socket.send(echoReply);
						flag = 0;
					}

				}
			}
		}
	}

}
