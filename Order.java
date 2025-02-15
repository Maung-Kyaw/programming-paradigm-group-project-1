package Project1_6581147;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        HashMap<String, Product> productMap = Product.readProduct();
        HashMap<Integer, Installment> installmentMap = Installment.readInstallments();
        Scanner userInput= new Scanner(System.in);
        String fileName="orders.txt";
        File InFile=null;
        
        while(true){
            try{
                File orderstxt=new File("src/main/java/Project1_6581147/"+fileName);
                Scanner orderScan= new Scanner(orderstxt);

                if(orderScan.hasNextLine()) orderScan.nextLine();
            
                while(orderScan.hasNextLine()){
                    String line= orderScan.nextLine();
                    try{
                        String [] cols=line.split(",");
                        int orderId= Integer.parseInt(cols[0].trim());
                        String customerName= cols[1].trim();
                        String code= cols[2].trim();
                        int units= Integer.parseInt(cols[3].trim());
                        int installmentPlan= Integer.parseInt(cols[4].trim());
                        
                        if (!productMap.containsKey(code)) {
                            throw new InvalidInputException("for product: "+code);
                        }
                        if(units<0){
                            throw new InvalidInputException("for units: "+units);
                        }
                        if (!installmentMap.containsKey(installmentPlan)) {
                            throw new InvalidInputException("for installment plan: "+installmentPlan);
                        }
                
                        orderMap.put(orderId,new Order(orderId, customerName, code, units, installmentPlan));
                    }
                    catch(InvalidInputException | NumberFormatException | ArrayIndexOutOfBoundsException e){
                        System.out.println(e.getClass().getName()+": "+e.getMessage());
                        System.out.println(line);
                        System.out.println();
                    }
                }
                break;
            }
            catch(FileNotFoundException e){
            System.err.println(e);
            System.out.println("New file name:");
            fileName=userInput.next();
            }
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
    public void displayOrder(HashMap<String, Product> productMap) {
        Product product = productMap.get(productCode);
        if (product == null) {
            System.out.printf("Order %2d >> %-7s %-15s x %2d %5d-month installments (Product not found)\n",
                orderId, customerName, productCode, units, installmentPlan);
        } else {
            System.out.printf("Order %2d >> %-7s %-15s x %2d %5d-month installments\n",
                orderId, customerName, product.getName(), units, installmentPlan);
        }
    }
    public static void orderProcessing(HashMap<String, Product> productMap, 
                                   HashMap<Integer, Order> orderMap, 
                                   HashMap<Integer, Installment> installmentMap,
                                   HashMap<String, Customer> customerMap) {

        for (Order order : orderMap.values()) {
            Product product = productMap.get(order.getCode());
            if (product != null) {
                product.updateSales(order.getUnit());
            }
            String customerName = order.getCustomerName();

            boolean firstOrder = !customerMap.containsKey(customerName);
        
            if (firstOrder) {
                customerMap.put(customerName, new Customer(customerName));
            }

            Customer customer = customerMap.get(customerName);
            int initialPoints = customer.getPoints();
            
            double subTotal1= order.calculateSubTotal1(productMap);
            double subTotal2= subTotal1;
            double discount=0;
            boolean usedDiscount = false;
            
            if (firstOrder) {
                discount=200;
                subTotal2 -= 200;
                if (subTotal2 < 0) subTotal2 = 0;
            }else if (customer.getPoints() >= 100) {
                discount=subTotal1*0.05;
                subTotal2 *= 0.95;
                customer.redeemPoints();
                usedDiscount= true;
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
            if (usedDiscount) {
                pointsEarned = (int) (subTotal2 / 500);
            }

            System.out.printf("%-3d %s (%5d pts)  %-15s x %2d    sub-total(1)= %,13.2f %8d points (+%d pts next order)\n",
                order.getorderId(),
                customerName,
                initialPoints,   
                product.getName(),
                order.getUnit(),
                subTotal1,
                customer.getPoints(),
                pointsEarned);  
            
            System.out.printf("                       discount= %,10.2f    sub-total(2)= %,13.2f\n", discount, subTotal2);
            
            if(order.getInsPlan()==0){
                System.out.printf("                       full payment\n");
                System.out.printf("                       total= %,.2f \n\n", totalPayment);
            }
            else{
                System.out.printf("                       %-2d%-21s %s %,10.2f\n", order.getInsPlan(),"-month installments", "total interest= ", interest);
                System.out.printf("                       total= %,13.2f    monthly total= %,12.2f\n\n", totalPayment, monthlyPayment);
            }
            
            
    }
}

    public static void customerSummary(HashMap<String, Customer> customerMap) {
        List<Customer> customerList = new ArrayList<>(customerMap.values());

        customerList.sort((c1, c2) -> {
            if (c2.getPoints() == c1.getPoints()) {
                return c1.getName().compareTo(c2.getName());
            }
            return Integer.compare(c2.getPoints(), c1.getPoints());
        });

        System.out.println("\n=== Customer Summary ===");
        for (Customer customer : customerList) {
            System.out.printf(" %-7s remaining points= %,5d\n", customer.getName(), customer.getPoints());
        }
    }
}
