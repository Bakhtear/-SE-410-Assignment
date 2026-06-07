import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        System.out.println("Connected to server...");
        
        try (Socket socket = new Socket("localhost", 5000);
             Scanner scanner = new Scanner(System.in);
             DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {

            System.out.print("Enter Student ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter Student Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter Marks for 2 subjects: ");
            int m1 = scanner.nextInt();
            int m2 = scanner.nextInt();

            out.writeInt(id);
            out.writeUTF(name);
            out.writeInt(m1);
            out.writeInt(m2);
            out.flush();

            System.out.println("Data sent successfully!");

        } catch (Exception e) {
            System.out.println("Client Error: " + e.getMessage());
        }
    }
}
