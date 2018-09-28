import java.io.*;
import java.net.*;

public class ServerOfFile {

	static final int PORT = 8000;

	public static void main(String[] args) throws Exception {

		
		try (DatagramSocket socket = new DatagramSocket(PORT)) {

			for (;;) { // server endless loop
				
				byte[] buffer = new byte[65536];
				DatagramPacket echoRequest = new DatagramPacket(buffer, buffer.length);
				
				socket.receive(echoRequest);
				
				byte[] echoRequestData = echoRequest.getData();
				int echoRequestLength = echoRequest.getLength();
				int flag = 0;
				String filename = new String(echoRequestData);
				System.out.println("c:\\Users\\USER\\Desktop\\" + filename);
				File file = new File("c:\\Users\\USER\\Desktop\\" + filename);
				System.out.println(file.canWrite());
				try (FileOutputStream fout = new FileOutputStream(file)) {
					System.out.println(echoRequestLength);
					do {
						socket.receive(echoRequest);
						echoRequestData = echoRequest.getData();
						echoRequestLength = echoRequest.getLength();
						if(echoRequestLength > 0)
						fout.write(echoRequestData, 0, echoRequestLength);
						Thread.sleep(500);
						System.out.println("Recebi: " + echoRequestLength);
						flag++;
					}while (echoRequest.getLength() >= 1024);

					System.out.println(echoRequestLength);

					if (flag > 0) {
						String doneD = "File sent";
						System.out.println(doneD);
						byte[] byteArray = doneD.getBytes();
						DatagramPacket echoReply = new DatagramPacket(byteArray, byteArray.length);
						echoReply.setAddress(echoRequest.getAddress());
						echoReply.setPort(echoRequest.getPort());
						socket.send(echoReply);
						flag = 0;
					}

				}
			}
		}
	}

}
