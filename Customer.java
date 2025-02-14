package Project1_6581147;

import java.util.*;

public class Customer {
    private String customerName;
    private int points;
    
    public Customer(String name){
        this.customerName=name;
        this.points=0;
    }
    public String getName(){
        return customerName;
    }
    public int getPoints(){
        return points;
    }
    public void addPoints(double orderTotal){
        points+=(int)(orderTotal/500);
    }
    public double redeemPoints(double orderTotal){
        if(points>=100){
            points-=100;
            return orderTotal*0.95;
        }
        return orderTotal;
    }
    public static HashMap<String, Customer> customerMap(HashMap<Integer, Order> orderMap, HashMap<String, Product> productMap) {
        HashMap<String, Customer> customerMap = new HashMap<>();

        for (Order order : orderMap.values()) {
            Product product = productMap.get(order.getCode());
            if (product != null) {
                double orderTotal = order.calculateSubTotal1(productMap); 
                String customerName = order.getCustomerName();

                if (!customerMap.containsKey(customerName)) {
                    customerMap.put(customerName, new Customer(customerName));
                }
                
                Customer customer = customerMap.get(customerName);
                customer.addPoints(orderTotal); 
            }
        }

        return customerMap;
    }
}
