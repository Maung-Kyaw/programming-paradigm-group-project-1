
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
    
    public Product(String code, String name, double price){
        this.productCode=code;
        this.productName=name;
        this.unitPrice=price;
    }
    public static HashMap<String, Product> readProduct(){
        HashMap<String,Product> productMap= new HashMap<>();
        Scanner userInput= new Scanner(System.in);
        Scanner productScan=null;
        String fileName="product.txt";
        
        File InFile=null;

        while(productScan==null){
            try{
            
                File producttxt=new File("src/main/java/Project1_6581147/"+fileName);
                productScan= new Scanner(producttxt);
               
                if(productScan.hasNextLine()) productScan.nextLine();
            
                while(productScan.hasNextLine()){
                    String line= productScan.nextLine();
                    try{
                    String [] cols=line.split(",");
                    String code= cols[0].trim();
                    String name= cols[1].trim();
                    double unitPrice= Double.parseDouble(cols[2].trim());

                    productMap.put(code, new Product(code, name, unitPrice));
                    }
                    catch(NumberFormatException | ArrayIndexOutOfBoundsException e){
                        System.out.println(e.getClass().getName()+e.getMessage());
                        System.out.println(line);
                        System.out.println();
                    }
                }
                productScan.close();
            }
            catch(FileNotFoundException e){
                System.err.println(e);
                System.out.println("New file name:");
                fileName=userInput.next();
            }
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
        
        System.out.printf("%-16s total sales= %d units %,13.2f THB    lucky draw winner= %s\n",productName,totalUnits,totalSales,generateWinner(orderMap));
    }

    private String generateWinner(HashMap<Integer, Order> orderMap) {
        List<Order> productOrders = new ArrayList<>();

        for (Order order : orderMap.values()) {
            if (order.getCode().equals(this.productCode)) {
                productOrders.add(order);
            }
        }

        if (productOrders.isEmpty()) {
            return "No winner (No orders)";
        }

        Random rand = new Random();
        Order luckyOrder = productOrders.get(rand.nextInt(productOrders.size()));

        return luckyOrder.getCustomerName()+" (order "+luckyOrder.getorderId()+")";
    }

    public void displayProduct(){
        System.out.println("==================================");
        System.out.println("Product Name: " + productName);
        System.out.println("Product Code: " + productCode);
        System.out.println("Unit Price: " + unitPrice);
        System.out.println("==================================");
        System.out.println();
    }
    
}
