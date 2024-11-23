package menu_driven;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class book {

	private static void addBook(Connection con, Scanner sc) throws SQLException {
	
		System.out.println("Enter Book name: ");
		String name = sc.nextLine();
		System.out.println("Enter Book author: ");
		String author = sc.nextLine();
		System.out.println("Enter Book price: ");
		float price = sc.nextFloat();

		String sql = "INSERT INTO book (name,author,price) VALUES (?,?,?)";
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, name);
			pstmt.setString(2, author);
			pstmt.setFloat(3, price);
			
			int rows = pstmt.executeUpdate();
			System.out.println(rows > 0 ? "Book added successfully!" : "Failed to add book");
		}
	}
	
	private static void removeBook(Connection con, Scanner sc) throws SQLException {
		System.out.println("Enter book ID to remove: ");
		int id = sc.nextInt();
		
		String sql = "DELETE FROM book where id = ?";
		try(PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setInt(1, id);
			int rows = pstmt.executeUpdate();
			System.out.println(rows > 0 ? "Book removed successfully" : "book not found.");
		}
	}
	
	private static void updateBookName(Connection con, Scanner sc) throws SQLException {
		System.out.println("Enter Book ID to Upadte: ");
		int id = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter a new book name: ");
		String newName = sc.nextLine();
		
		String sql = "UPDATE book SET name = ? WHERE id = ?";
		try(PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setString(1, newName);
			pstmt.setInt(2, id);
			int rows = pstmt.executeUpdate();
			System.out.println(rows > 0 ? "Book name update successfully":"Book not found.");
		}
	}
	
	private static void updateBookAuthor(Connection con, Scanner sc) throws SQLException {
		System.out.println("Enter Book ID to Upadte: ");
		int id = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter a new author: ");
		String newAuthor = sc.nextLine();
		
		String sql = "UPDATE book SET author = ? WHERE id = ?";
		try(PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setString(1, newAuthor);
			pstmt.setInt(2, id);
			int rows = pstmt.executeUpdate();
			System.out.println(rows > 0 ? "Book author update successfully":"Book not found.");
		}
	}
	
	private static void updateBookPrice(Connection con, Scanner sc) throws SQLException {
		System.out.println("Enter Book ID to Upadte: ");
		int id = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter a new price: ");
		float newPrice = sc.nextFloat();
		
		String sql = "UPDATE book SET price = ? WHERE id = ?";
		try(PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setFloat(1, newPrice);
			pstmt.setInt(2, id);
			int rows = pstmt.executeUpdate();
			System.out.println(rows > 0 ? "Book price update successfully":"Book not found.");
		}
	}
	
	private static void searchBookByName(Connection con, Scanner sc) throws SQLException {
		System.out.println("Enter a book name to search: ");
		String name = sc.nextLine();
		
		String sql = "SELECT * FROM book WHERE name LIKE ?";
		try(PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setString(1,"%" + name + "%");
			try(ResultSet rs = pstmt.executeQuery()){
				if(!rs.next()) {
					System.out.println("No Book found with the given name.");
				} else {
					do {
						System.out.printf("ID: %d, Name: %s, Author: %s, Price: %.2f%n", rs.getInt("id"),rs.getString("name"),rs.getString("author"),rs.getDouble("price"));
					}while(rs.next());
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Scanner sc = new Scanner(System.in);

		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Books", "root", "root")) {
			System.out.println("Database Connected.....");

			while (true) {
				System.out.println("\nMenu:");
				System.out.println("1. Add book");
				System.out.println("2. Remove book");
				System.out.println("3. Update book name");
				System.out.println("4. Update book author");
				System.out.println("5. Update book price");
				System.out.println("6. Search book by name");
				System.out.println("7. Exit");
				System.out.print("Enter your choice: ");
				int choice = sc.nextInt();
				sc.nextLine();

				switch (choice) {
				case 1:
					addBook(con, sc); break;
				case 2:
					removeBook(con, sc); break;
				case 3:
					updateBookName(con, sc); break;
				case 4:
					updateBookAuthor(con, sc); break;
				case 5:
					updateBookPrice(con, sc); break;
				case 6:
					searchBookByName(con, sc); break;
				case 7: {
					System.out.println("Exiting...");
					return;
				}
				default:
					System.out.println("Invaild Choice!. Try again idiot");
					break;
				}
			}
		} catch (SQLException e) {
			System.out.println("Error" + e.getMessage());
		}
	}
}
