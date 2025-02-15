package Project1_6581147;

import java.util.*;
import java.io.*;

public class Installment {
    private int months;
    private double interestRate;

    public Installment(int months, double interestRate){
        this.months=months;
        this.interestRate=interestRate;
    }
    public int getMonths(){
        return months;
    }
    public double getInterestRate(){
        return interestRate;
    }
    public double calculateTotalPayment(double subTotal){
        return subTotal * (1+(months*interestRate/100));
    }
    public double calculateMonthlyPayment(double subTotal){
        if(months==0) return 0;
        return calculateTotalPayment(subTotal)/months;
    }
    public static HashMap<Integer, Installment> readInstallments() {
        HashMap<Integer, Installment> installmentMap = new HashMap<>();
        Scanner userInput= new Scanner(System.in);
        String fileName="installment.txt";
        File InFile=null;
        
        while(true){
        
            try {
                File installmenttxt = new File("src/main/java/Project1_6581147/"+fileName);
                Scanner installmentScan = new Scanner(installmenttxt);

                if (installmentScan.hasNextLine()) installmentScan.nextLine();
            
                while (installmentScan.hasNextLine()) {
                    String line = installmentScan.nextLine();
                    try{
                    String[] cols = line.split(",");
                
                    int months = Integer.parseInt(cols[0].trim());
                    double interestRate = Double.parseDouble(cols[1].trim());
                
                    installmentMap.put(months, new Installment(months, interestRate));
                    }
                    catch(NumberFormatException | ArrayIndexOutOfBoundsException e){
                        System.out.println(e.getClass().getName()+": "+e.getMessage());
                        System.out.println(line);
                        System.out.println();
                    }
                }
                break;
            } 
            catch (FileNotFoundException e) {
                System.err.println(e);
                System.out.println("New file name:");
                fileName=userInput.next();
            }
        }
        
        
        return installmentMap;
    }
    public void displayInstallment(){
        System.out.printf("%2d-month plan   monthly interest= %.2f \n", months,interestRate);
    }
    
}
