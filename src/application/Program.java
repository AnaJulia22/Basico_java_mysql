package application;

import java.util.Locale;
import java.util.Scanner;

import entities.Product;

public class Program {

	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter product data:");
		
		System.out.println("Name: ");
		String name = sc.nextLine();
		
		System.out.println("Price: ");
		double price = sc.nextDouble();
		
//		System.out.println("Quantity in stock: ");
//		int quantity = sc.nextInt();
		
		Product product = new Product(name, price);
		
		System.out.println();
		System.out.println("Product data: " + product);
		
		System.out.println("Enter an action:");
		System.out.println("Enter 'add' to add products in stock, 'remove' to remove products in stock or 'end' to finish program.");
		String action = sc.next();
		
		int quantity;
		
		while (!(action.equals("end"))) {
			
			if (action.equals("add")) {
				
				System.out.println();
				System.out.println("Enter the number of products to be added in stock: ");
				quantity = sc.nextInt();
				product.addProducts(quantity);
				
			} else if (action.equals("remove")) {
				if (product.quantity > 0) {
					
					System.out.println();
					System.out.println("Enter the number of products to be removed in stock: ");
					quantity = sc.nextInt();
					product.removeProducts(quantity);
					
				} 
				else {
					
					System.out.println("There is no product in the stock to be removed.");
				}
				
			}
			
			System.out.println();
			System.out.println("Updated data: " + product);			
			
			System.out.println("Enter next action:");
			action = sc.next();
		}
		
		System.out.println("Program terminated");
		sc.close();

	}

}
