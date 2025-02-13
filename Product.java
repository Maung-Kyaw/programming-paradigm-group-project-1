/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project_1;

/**
 *
 * @author kyawz
 */
public class Product {
    
    private String code;
    private String name;
    private double price;
    
    private int totalUnits =0;
    private double totalSales =0;
    
    //Constructor for setting the values
    public Product(String code, String name, double price){
        this.code=code;
        this.name=name;
        this.price=price;
    }
    //Getters
    public String getCode(){
        return code;
    }
    public String getName(){
        return name;
    }
    public double getPrice(){
        return price;
    }
    
    //Method for updating sales
    
    public void updateSales(int units){
        this.totalUnits += units;
        this.totalSales += units*price;
    }
    
    public String getSalesSummary(){
        return name+" - Units Sold: " + totalUnits + ", Total Sales: "+ totalSales + "THB";
    }
    
}


