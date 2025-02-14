
package Project1_6581147;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Product {
    
    private String productCode;
    private String productName;
    private double unitPrice;
    
    private int totalUnits=0;
    private double totalSales=0;
    
    //Constructor for setting the values
    public Product(String code, String name, double price){
        this.productCode=code;
        this.productName=name;
        this.unitPrice=price;
    }
    public static HashMap<String, Product> readProduct(){
        HashMap<String,Product> productMap= new HashMap<>();
        try{
            File producttxt=new File("src/main/java/Project1_6581147/products.txt");
            Scanner productScan= new Scanner(producttxt);
            if(productScan.hasNextLine()) productScan.nextLine();
            
            while(productScan.hasNextLine()){
                String line= productScan.nextLine();
                String [] cols=line.split(",");
                String code= cols[0].trim();
                String name= cols[1].trim();
                double unitPrice= Double.parseDouble(cols[2].trim());

                productMap.put(code, new Product(code, name, unitPrice));

            }
            productScan.close();
        }
        catch(FileNotFoundException e){
            System.err.println("Product file not found");
        }
        catch(ArrayIndexOutOfBoundsException e){
            System.err.println("Array index error");
        }
        return productMap;
    } 
    public String getCode(){
        return productCode;
    }
    public String getName(){
        return productName;
    }
    public double getPrice(){
        return unitPrice;
    }
    public void updateSales(int units){
        this.totalUnits += units;
        this.totalSales += units*unitPrice;
    }
    
    public void getSalesSummary(HashMap<Integer, Order> orderMap) {
        System.out.println(productName + "\ttotal sales = " + totalSales + 
                           "  units = " + totalUnits + " THB" +
                           "  lucky draw winner = " + generateWinner(orderMap));
    }

    // Method to generate a lucky draw winner from product orders
    private String generateWinner(HashMap<Integer, Order> orderMap) {
        List<Order> productOrders = new ArrayList<>();

        // Collect all orders related to this product
        for (Order order : orderMap.values()) {
            if (order.getCode().equals(this.productCode)) {
                productOrders.add(order);
            }
        }

        if (productOrders.isEmpty()) {
            return "No winner (No orders)";
        }

        // Randomly select a winner from the list of orders
        Random rand = new Random();
        Order luckyOrder = productOrders.get(rand.nextInt(productOrders.size()));

        return "Order ID: " + luckyOrder.getorderId() + ", Customer: " + luckyOrder.getCustomerName();
    }

    public void displayProduct(){
        System.out.println("==================================");
        System.out.println("Product Name: " + productName);
        System.out.println("Product Code: " + productCode);
        System.out.println("Unit Price: " + unitPrice);
        System.out.println("==================================");

    }
    
}
