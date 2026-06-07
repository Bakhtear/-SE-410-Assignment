import java.sql.*;
import java.util.HashMap;
import java.util.Scanner;

public class StudentResultSystem {
    private static final String URL = "jdbc:mysql://localhost:3306/Student_DB";
    private static final String USER = "root"; 
    private static final String PASSWORD = "bakhtear1";
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter total number of students: ");
        int count = scanner.nextInt();

        String[] names = new String[count];
        int[] ids = new int[count];
        HashMap<Integer, Integer[]> studentMarks = new HashMap<>();

        for (int i = 0; i < count; i++) {
            System.out.println("\n- Student " + (i + 1) + " -");
            System.out.print("Enter ID: ");
            ids[i] = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter Name: ");
            names[i] = scanner.nextLine();

            System.out.print("Enter 3 Marks: ");
            studentMarks.put(ids[i], new Integer[]{scanner.nextInt(), scanner.nextInt(), scanner.nextInt()});
        }

        String insertSQL = "INSERT INTO students (id, name, mark1, mark2, mark3, total, grade) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            for (int i = 0; i < count; i++) {
                Integer[] m = studentMarks.get(ids[i]);
                int total = m[0] + m[1] + m[2];
                double avg = total / 3.0;

                String grade = (avg >= 80) ? "A+" : (avg >= 70) ? "A" : (avg >= 60) ? "B" : (avg >= 50) ? "C" : (avg >= 40) ? "D" : "F";

                pstmt.setInt(1, ids[i]);
                pstmt.setString(2, names[i]);
                pstmt.setInt(3, m[0]);
                pstmt.setInt(4, m[1]);
                pstmt.setInt(5, m[2]);
                pstmt.setInt(6, total);
                pstmt.setString(7, grade);
                pstmt.executeUpdate();
            }
            System.out.println("\n>>> Records successfully saved to database.");
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM students")) {

            System.out.println("\n========================= DB RECORDS =========================");
            System.out.printf("%-6s | %-15s | %-5s | %-5s | %-5s | %-5s | %-5s\n", "ID", "Name", "M1", "M2", "M3", "Total", "Grade");
            System.out.println("--------------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%-6d | %-15s | %-5d | %-5d | %-5d | %-5d | %-5s\n",
                        rs.getInt("id"), rs.getString("name"), rs.getInt("mark1"),
                        rs.getInt("mark2"), rs.getInt("mark3"), rs.getInt("total"), rs.getString("grade"));
            }
            System.out.println("==============================================================");
        } catch (SQLException e) {
            System.out.println("Database Retrieval Error: " + e.getMessage());
        }
        scanner.close();
    }
}
