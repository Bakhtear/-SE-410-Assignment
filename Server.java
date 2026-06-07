import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {
    public static void main(String[] args) {
        String[] namesArray = new String[1]; 
        HashMap<Integer, Integer[]> studentMarksMap = new HashMap<>();

        System.out.println("Server is running. Searching for client...");

        try (ServerSocket serverSocket = new ServerSocket(5000);
             Socket socket = serverSocket.accept();
             DataInputStream in = new DataInputStream(socket.getInputStream())) {

            int id = in.readInt();
            String name = in.readUTF();
            int m1 = in.readInt();
            int m2 = in.readInt();

            namesArray[0] = name;
            studentMarksMap.put(id, new Integer[]{m1, m2});

            System.out.println("\n--- Received Student Data ---");
            System.out.println("Name: " + namesArray[0]);
            System.out.print("ID: " + id + " | Marks: ");
            for (int mark : studentMarksMap.get(id)) {
                System.out.print(mark + " ");
            }
            System.out.println("\n--------------------------------");

        } catch (IOException e) {
            System.out.println("Server Error: " + e.getMessage());
        }
    }
}