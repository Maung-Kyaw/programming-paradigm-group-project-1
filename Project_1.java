/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package project_1;


/**
 *
 * @author kyawz
 */
public class Project_1 {

    public static void main(String[] args) {
        ProductManager pm = new ProductManager();
        pm.loadProducts("products.txt");
        Product foundProduct = pm.getProduct("AP");
        
        if(foundProduct != null){
            System.out.println("Product Name: "+foundProduct.getName());
            System.out.println("Price: "+foundProduct.getPrice()+" THB");
        }else{
            System.out.println("Product not found!");
        }
    }
}
