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
        
        try {
            File installmentFile = new File("src/main/java/Project1_6581147/installments.txt");
            Scanner installmentScan = new Scanner(installmentFile);
            
            if (installmentScan.hasNextLine()) installmentScan.nextLine();
            
            while (installmentScan.hasNextLine()) {
                String line = installmentScan.nextLine();
                String[] cols = line.split(",");
                
                int months = Integer.parseInt(cols[0].trim());
                double interestRate = Double.parseDouble(cols[1].trim());
                
                installmentMap.put(months, new Installment(months, interestRate));
            }
            installmentScan.close();
        } 
        catch (FileNotFoundException e) {
            System.err.println("Installment file not found");
        } 
        catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Array index error while reading installment file");
        } 
        catch (NumberFormatException e) {
            System.err.println("Error parsing numbers in installment file");
        }
        return installmentMap;
    }
    public void displayInstallment(){
        System.out.printf("Months: %23d\n", months);
        System.out.printf("Interest Rate: %.1f\n\n", interestRate);
    }
    
}
