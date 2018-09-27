import java.io.*;
import java.net.*;;

public class ClientOfFile {

	public static void main(String[] args) throws Exception {

		File file = new File("c:\\Users\\USER\\Desktop\\tpc1.txt");

		String servidor = args[0];
		int port = Integer.parseInt(args[1]);
		InetAddress serverAddress = InetAddress.getByName(servidor);

		try (DatagramSocket socket = new DatagramSocket()) {

			byte[] sendingBuffer = file.getName().getBytes();

			try (FileInputStream fin = new FileInputStream(file)) {

				DatagramPacket echoRequest = new DatagramPacket(sendingBuffer, sendingBuffer.length, serverAddress,
						port);
				socket.send(echoRequest);

				Thread.sleep(100);

				
				while (fin.available() > 0) {
					byte[] bffr = new byte[5000];
					if (fin.available() > 1024) {
						System.out.println("fek");

						fin.read(bffr, 0, 1024);
						echoRequest = new DatagramPacket(bffr, bffr.length, serverAddress, port);
						socket.send(echoRequest);
						Thread.sleep(100);
					} else {
						System.out.println("fek2");

						fin.read(bffr, 0, fin.available());
						echoRequest = new DatagramPacket(bffr, bffr.length, serverAddress, port);
						socket.send(echoRequest);
						Thread.sleep(100);
					}

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
