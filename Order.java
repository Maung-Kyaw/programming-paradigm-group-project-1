package Project1_6581147;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Order {
    private int orderId;
    private String customerName;
    private String productCode;
    private int units;
    private int installmentPlan;
    private double subtotal1;
    private double subtotal2;
    private double totalPayment;
    private double monthlyPayment;

    public Order(int orderId, String customerName, String code, int units, int installmentPlan){
        this.orderId = orderId;
        this.customerName = customerName;
        this.productCode = code;
        this.units = units;
        this.installmentPlan = installmentPlan;
    }
    public String getCode(){
        return productCode;
    }
    public String getCustomerName(){
        return customerName;
    }
    public int getInsPlan(){
        return installmentPlan;
    }
    public int getorderId(){
        return orderId;
    }
    public int getUnit(){
        return units;
    }
    public static HashMap<Integer,Order> readOrder(){
        HashMap<Integer,Order> orderMap= new HashMap<>();
        try{
            File orderstxt=new File("src/main/java/Project1_6581147/orders.txt");
            Scanner orderScan= new Scanner(orderstxt);
            if(orderScan.hasNextLine()) orderScan.nextLine();
            
            while(orderScan.hasNextLine()){
                String line= orderScan.nextLine();
                String [] cols=line.split(",");
                int orderId= Integer.parseInt(cols[0].trim());
                String customerName= cols[1].trim();
                String code= cols[2].trim();
                int units= Integer.parseInt(cols[3].trim());
                int installmentPlan= Integer.parseInt(cols[4].trim());
                
                orderMap.put(orderId,new Order(orderId, customerName, code, units, installmentPlan));
            }
            orderScan.close();
        }
        catch(FileNotFoundException e){
            System.err.println(e);
        }
        catch(ArrayIndexOutOfBoundsException e){
            System.err.println(e.getClass().getName());
        }
        return orderMap;
    }
    public double calculateSubTotal1(HashMap<String, Product> productMap) {
        Product product = productMap.get(productCode);
        if (product!=null) {
            return product.getPrice()* units;
        }
        return 0;
    }
    public void displayOrder(HashMap<String, Product> productMap){
        Product product = productMap.get(productCode);
        System.out.printf("Order %2d >> %s %15s x %2d %5d-month installment\n",orderId,customerName,product.getName(),units,installmentPlan);
    }
   public static void orderProcessing(HashMap<String, Product> productMap, 
                                   HashMap<Integer, Order> orderMap, 
                                   HashMap<Integer, Installment> installmentMap) {
        HashMap<String, Customer> customerMap = new HashMap<>();

        for (Order order : orderMap.values()) {
            Product product = productMap.get(order.getCode());
            String customerName = order.getCustomerName();

            boolean firstOrder = !customerMap.containsKey(customerName);
        
            if (firstOrder) {
            customerMap.put(customerName, new Customer(customerName));
            }

            Customer customer = customerMap.get(customerName);
            int initialPoints = customer.getPoints();
            
            double subTotal1= order.calculateSubTotal1(productMap);
            double subTotal2= subTotal1;
            
            if (firstOrder) {
                subTotal2 -= 200;
                if (subTotal2 < 0) subTotal2 = 0;
            }else if (customer.getPoints() >= 100) {
            subTotal2 = customer.redeemPoints(subTotal1);
            }
            
            int insPlan= order.getInsPlan();
            Installment installment= installmentMap.get(insPlan);
            
            double totalPayment = 0;
            double monthlyPayment = 0;
            double interest = 0;
            
            if (installment != null && insPlan > 0) {
            totalPayment = installment.calculateTotalPayment(subTotal2);
            monthlyPayment = installment.calculateMonthlyPayment(subTotal2);
            interest = totalPayment - subTotal2;
            }
            else if(installment!=null&&insPlan==0){
                totalPayment=subTotal2;
            }

            customer.addPoints(subTotal1);
            int pointsEarned= (int)(subTotal1/500);

            System.out.printf("%d (%d pts) %s, %s x %d sub-total(1)= %,.2f, %d points, (+%d pts next order)\n",
                order.getorderId(),
                initialPoints,    
                customerName,
                product.getName(),
                order.getUnit(),
                subTotal1,
                customer.getPoints(),
                pointsEarned   );  
            
            System.out.printf("sub-total(2)= %,.2f\n", subTotal2);
            
            if(order.getInsPlan()==0){
                System.out.printf("full payment\n");
                System.out.printf("total= %,.2f \n\n", totalPayment);
            }
            else{
                System.out.printf("%d-month installments %,.2f\n", order.getInsPlan(),interest);
                System.out.printf("total= %,.2f monthly total= %,.2f\n\n", totalPayment, monthlyPayment);
            }
            
            
    }
}




    
}
