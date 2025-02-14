
package Project1_6581147;

import java.util.*;

public class Project1_6581147 {
    public static void main(String[] args) {
        HashMap<String,Product> productMap= Product.readProduct();
        for (Product product : productMap.values()) {
            product.displayProduct();
        }
        
        HashMap<Integer, Installment> installmentMap = Installment.readInstallments();
        for(Installment installment : installmentMap.values()){
            installment.displayInstallment();
        }
        
        HashMap<Integer, Order> orderMap = Order.readOrder();
        
        for (Order order : orderMap.values()) {
            order.displayOrder(productMap);
        }
        System.out.println();

        Order.orderProcessing(productMap, orderMap, installmentMap);
        
        for (Product product : productMap.values()) {
            product.getSalesSummary(orderMap);   
            
        }
    }
}
