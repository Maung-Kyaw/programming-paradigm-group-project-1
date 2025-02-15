
package Project1_6581147;

import java.util.*;

class InvalidInputException extends Exception{
    public InvalidInputException(String message){
        super(message);
    }
}

public class Project1_6581147 {
    public static void main(String[] args) {
        HashMap<String, String> fileNames = new HashMap<>();
        HashMap<String,Product> productMap= Product.readProduct(fileNames);
        for (Product product : productMap.values()) {
            product.displayProduct();
        }
        System.out.println();
        
        HashMap<Integer, Installment> installmentMap= Installment.readInstallments(fileNames);
        for(Installment installment : installmentMap.values()){
            installment.displayInstallment();
        }
        System.out.println();
        
        HashMap<Integer, Order> orderMap= Order.readOrder(fileNames);
        
        for (Order order : orderMap.values()) {
            order.displayOrder(productMap);
        }
        System.out.println();
        
        System.out.println("=== Order Processing ===");
        HashMap<String, Customer> customerMap = new HashMap<>();
        Order.orderProcessing(productMap, orderMap, installmentMap,customerMap);
        System.out.println();
        
        System.out.println("=== Product Summary ===");
        List<Product> productList = new ArrayList<>(productMap.values());

        productList.sort((p1, p2) -> Integer.compare(p2.getTotalUnits(), p1.getTotalUnits()));

        for (Product product : productList) {
            product.getSalesSummary(orderMap);
        }
        
        Order.customerSummary(customerMap);
    }
}
