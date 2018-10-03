import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;;

public class ClientOfFile {

	public static void main(String[] args) throws Exception {

		File file = new File("C:\\Users\\USER\\Desktop\\jpeg.jpg");
		
		 Queue<DatagramPacket> qPackets = new LinkedList<DatagramPacket>();
		 
		String servidor = args[0];
		int port = Integer.parseInt(args[1]);
		InetAddress serverAddress = InetAddress.getByName(servidor);

		try (DatagramSocket socket = new DatagramSocket()) {
			
			socket.setSoTimeout(500);
			
			try {
			byte[] sendingBuffer = file.getName().getBytes();

			try (FileInputStream fin = new FileInputStream(file)) {

				DatagramPacket echoRequest = new DatagramPacket(sendingBuffer, sendingBuffer.length, serverAddress,
						port);
				socket.send(echoRequest);

				Thread.sleep(500);
				
				
				while (fin.available() > 0) {
					byte[] bffr = new byte[5000];
					if (fin.available() > 1024) {
						System.out.println(fin.available());
						fin.read(bffr, 0, 1024);
						System.out.println(fin.available());
						echoRequest = new DatagramPacket(bffr, 1024, serverAddress, port);
						qPackets.add(echoRequest);
						socket.send(echoRequest);
						Thread.currentThread().n
						Thread.sleep(500);
					} else {
						System.out.println("---->" + fin.available());
						int leftOver = fin.available();
						fin.read(bffr, 0, fin.available());
						System.out.println("---->" + fin.available());
						echoRequest = new DatagramPacket(bffr, leftOver, serverAddress, port);
						socket.send(echoRequest);
						Thread.sleep(500);
						echoRequest = new DatagramPacket(bffr, 0, serverAddress, port);
						//socket.send(echoRequest);
					}

				}

				byte[] buffer = new byte[65536];
				DatagramPacket echoReply = new DatagramPacket(buffer, buffer.length);

				socket.receive(echoReply);

				if (echoReply.getLength() > 0) {
					String s = new String(echoReply.getData());
					System.out.println(s);
				}

			}

			}catch(SocketTimeoutException e) {
				
				//socket.send();
				
			}
		}
	}

}
