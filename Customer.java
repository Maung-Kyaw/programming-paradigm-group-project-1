/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project_1;

/**
 *
 * @author kyawz
 */
public class Customer {
    private String name;
    private int points;
    
    public Customer(String name){
        this.name=name;
        this.points=0;
    }
    //Getters
    public String getName(){
        return name;
    }
    public int getPoints(){
        return points;
    }
    
    public void addPoints(double orderTotal){
        points += (int)(orderTotal/500);
    }
    
    public double redeemPoints(double orderTotal){
        if(points>=100){
            points -= 100;
            return orderTotal * 0.95;
        }
        return orderTotal; // No discount, no enough points
    }
    
    public String toString(){
        return name+" -Points: "+points;
    }
    
}
