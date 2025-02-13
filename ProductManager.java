/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project_1;

import java.io.*;
import java.util.*;

/**
 *
 * @author kyawz
 */
public class ProductManager {
    private HashMap<String, Product> products = new HashMap<>();
    
    public void loadProducts(String filename){
        try(BufferedReader br = new BufferedReader(new FileReader(filename))){
            String line = br.readLine();
            
            while((line=br.readLine()) != null){
                String[] parts = line.split(",\\s*");
                if(parts.length!=3) continue;
                String code = parts[0].trim();
                String name = parts[1].trim();
                double price = Double.parseDouble(parts[2].trim());
                
                products.put(code, new Product(code,name,price));
                
            }}catch(IOException e){
                    System.out.println("Error reading products file:"+e.getMessage());
                    }}
            public Product getProduct(String code){
                return products.get(code);
            }
            
            public void displayProducts(){
                for(Product p: products.values()){
                    System.out.println(p.getCode()+" - "+p.getName()+" - "+p.getPrice()+" THB");
                }
            }
    
        }
    

