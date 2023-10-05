package application;

import java.util.Locale;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Scanner;

import entities.Product;

public class Program {

	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		
		String url = "jdbc:mysql://localhost:4306/java_curso";
		String username = "root";
		String password = "";
		Scanner sc = new Scanner(System.in);
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection connection = DriverManager.getConnection(url, username, password);
			
			String name;
			double price;
			int quantity;

			System.out.println("Enter an action:");
			System.out.println("Enter 'add product' to add new product to stock," + "\n" 
								+ "'add stock' to add products in stock, " + "\n" 
								+ "'remove stock' to remove products in stock, " + "\n" 
								+ "'edit name' to edit a product's name, " + "\n"
								+ "'edit price' to edit product's price, " + "\n" 
								+ "'show stock' to show products data in stock or " + "\n" 
								+ "'end' to finish program.");
			String action = sc.nextLine();

			while (!(action.equals("end"))) {

				if(action.equals("add product")){
					
					System.out.println("Enter product data:");
					
					System.out.println("Name: ");
					name = sc.nextLine();
					
					System.out.println("Price: ");
					price = sc.nextDouble();
					
					System.out.println("Quantity: ");
					quantity = sc.nextInt();
					
					Product product = new Product(name, price, quantity);

					System.out.println();
					System.out.println("Product data: " + product);
					
					PreparedStatement preparedStmt = connection.prepareStatement(" insert into produto (name, price, quantity)"
																					+ " values (?, ?, ?)");

					preparedStmt.setString(1, name);
					preparedStmt.setDouble(2, price);
					preparedStmt.setInt(3, quantity);

					preparedStmt.execute();

					
				} else if (action.equals("add stock")) {

					System.out.println();
					System.out.println("Enter the name of product you want to add products in stock: ");
					name = sc.nextLine();
					
					System.out.println("Enter the number of products to be added in stock: ");
					quantity = sc.nextInt();
					
					PreparedStatement preparedStmt = connection.prepareStatement(" UPDATE produto \r\n"
																+ "SET quantity = quantity + ? \r\n"
																+ "WHERE name = ?");
					
					preparedStmt.setInt(1, quantity);
					preparedStmt.setString(2, name);
					
					preparedStmt.executeUpdate();

				} else if (action.equals("remove stock")) {

					System.out.println();
					System.out.println("Enter which product you want to remove products in stock: ");
					name = sc.nextLine();

					PreparedStatement preparedStmt = connection.prepareStatement("select quantity from produto WHERE name = ?");

					preparedStmt.setString(1, name);

					ResultSet resultSet = preparedStmt.executeQuery();
					if (resultSet.next()) {
						if (resultSet.getInt(1) > 0) {
							System.out.println("Enter the number of products to be removed in stock: ");
							quantity = sc.nextInt();

							preparedStmt = connection.prepareStatement(
									" UPDATE produto \r\n" + "SET quantity = quantity - ? \r\n" + "WHERE name = ?");

							preparedStmt.setInt(1, quantity);
							preparedStmt.setString(2, name);

							preparedStmt.executeUpdate();
						} else {

							System.out.println("There is no product in the stock to be removed.");
						}
					}

				} else if (action.equals("edit name")) {
					
					System.out.println();
					System.out.println("Enter which product you want to edit the name: ");
					String name0 = sc.nextLine();

					System.out.println("Enter new name:");
					name = sc.nextLine();					
					
					PreparedStatement preparedStmt = connection.prepareStatement(" UPDATE produto \r\n"
																+ "SET name = ? \r\n"
																+ "WHERE name = ?");


					preparedStmt.setString(1, name);
					preparedStmt.setString(2, name0);

					preparedStmt.executeUpdate();
					
					
				} else if (action.equals("edit price")) {
					
					System.out.println();
					System.out.println("Enter which product you want to edit the price: ");
					name = sc.nextLine();

					System.out.println("Enter new price:");
					price = sc.nextDouble();
					
					PreparedStatement preparedStmt = connection.prepareStatement(" UPDATE produto \r\n"
																+ "SET price = ? \r\n"
																+ "WHERE name = ?");


					preparedStmt.setDouble(1, price);
					preparedStmt.setString(2, name);

					preparedStmt.executeUpdate();
					

				} else if (action.equals("show stock")) {
					PreparedStatement preparedStmt = connection.prepareStatement("select * from produto");
					
					ResultSet resultSet = preparedStmt.executeQuery();
					
					while(resultSet.next()) {
						System.out.println(resultSet.getInt(1)+ " " + resultSet.getString(2) + " " +  resultSet.getDouble(3) + " " + resultSet.getInt(4));
				}

				System.out.println("Enter next action:");
				action = sc.nextLine();
			}

			System.out.println("Program terminated");
			
			}
			
			connection.close();
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		sc.close(); 
	}

}
