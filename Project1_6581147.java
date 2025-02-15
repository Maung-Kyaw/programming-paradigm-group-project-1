
package Project1_6581147;

import java.util.*;

class InvalidInputException extends Exception{
    public InvalidInputException(String message){
        super(message);
    }
}

public class Project1_6581147 {
    public static void main(String[] args) {
        HashMap<String,Product> productMap= Product.readProduct();
        for (Product product : productMap.values()) {
            product.displayProduct();
        }
        
        HashMap<Integer, Installment> installmentMap= Installment.readInstallments();
        for(Installment installment : installmentMap.values()){
            installment.displayInstallment();
        }
        System.out.println();
        
        HashMap<Integer, Order> orderMap= Order.readOrder();
        
        for (Order order : orderMap.values()) {
            order.displayOrder(productMap);
        }
        System.out.println();
        
        System.out.println("=== Order Processing ===");
        HashMap<String, Customer> customerMap = new HashMap<>();
        Order.orderProcessing(productMap, orderMap, installmentMap,customerMap);
        System.out.println();
        
        System.out.println("=== Product Summary ===");
        for (Product product : productMap.values()) {
            product.getSalesSummary(orderMap);   
            
        }
        
        Order.customerSummary(customerMap);
    }
}
