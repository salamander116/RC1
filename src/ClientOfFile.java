import java.io.*;
import java.net.*;
;

public class ClientOfFile {

	public static void main(String[] args) throws Exception {

		File file = new File("c:\\Users\\USER\\Desktop\\tpc1.txt");
		
		String servidor = args[0];
		int port = Integer.parseInt(args[1]);
		InetAddress serverAddress = InetAddress.getByName(servidor);

		try (DatagramSocket socket = new DatagramSocket()) {

			byte[] sendingBuffer = file.getName().getBytes();
			byte[] bffr = new byte[1024];
			
			try (FileInputStream fin = new FileInputStream(file)) {
			

				DatagramPacket echoRequest = new DatagramPacket(sendingBuffer, sendingBuffer.length, serverAddress,
						port);
				socket.send(echoRequest);
				Thread.sleep(100);

				while (fin.available() > 0) {
					System.out.println("fek");
					fin.read(bffr);
					echoRequest = new DatagramPacket(bffr, bffr.length, serverAddress, port);
					Thread.sleep(100);

				}

				byte[] buffer = new byte[65536];
				DatagramPacket echoReply = new DatagramPacket(buffer, buffer.length);

				socket.receive(echoReply);

				if (echoReply.getLength() > 0) {
					System.out.println(echoReply.getData().toString());
				}

			}

		}
	}

}
