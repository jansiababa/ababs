/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MAIN;

import Config.config;
import static MAIN.MAIN.viewCostumes;
import static MAIN.MAIN.viewrentalRecord;
import java.util.Scanner;

/**
 *
 * @author Angie
 */
public class Customer {
    Scanner sc = new Scanner(System.in);
    config con = new config();
    public void Customer(){
        
         System.out.println("=== Welcome to Customer Dashboard ===");
               System.out.println("1. View Custome Items");
               System.out.println("2. Select Costume to Rent");
               System.out.println("3. View My Rentals");
               System.out.println("4. Logout");
               System.out.println("Enter choice");
               int ch = sc.nextInt();
               switch(ch){
                   case 1:
                       viewCostumes();
                       break;
                   case 2:
                 System.out.println("==========================");
                 System.out.println("1. Modern Outfits");
                 System.out.println("2. Cultural Outfits");
                 System.out.println("3. Festival/Cheer Outfits");
                 System.out.println("==========================");
                 System.out.print("Enter Category Choice: ");
                 int category = sc.nextInt();
                 sc.nextLine(); 
                 String category_name = "";
                 String category_menu = "";

                 switch (category) {
                     case 1:
                         category_name = "Modern Outfits";

                         category_menu = "---------Modern Outfits----------\n" +
                                         "*Hip-Hop Streetwear*\n" +
                                         "*Jazz Dance Sequin Outfit*\n" +
                                         "*Modern Ballet Leotard with Skirt*\n" +
                                         "*Contemporary Lyrical Flow Dress*\n" +
                                         "*K-pop Inspired Dance Outfit*\n" +
                                         "---------------------------------";
                         break;
                     case 2:
                         category_name = "Cultural Outfits";
                         category_menu = "---------Cultural Outfits---------\n" +
                                         "*Barong Tagalog with Black Pants*\n" +
                                         "*Maria Clara Dress (Philippines)*\n" +
                                         "*Tinikling Bamboo Dance Outfit*\n" +
                                         "----------------------------------";
                         break;
                     case 3:
                          category_name = "Festival/Cheer Outfits";
                         category_menu = "------Festival/Cheer Outfits------\n" +
                                         "*Cheerleader Uniform*\n" +
                                         "*Mardi Gras Carnival Costume*\n" +
                                         "*Festival Street Dance Outfit*\n" +
                                         "*Neon Glow Dance Costume*\n" +
                                         "*Disco 80s Sequin Costume*\n" +
                                         "----------------------------------";
                         break;
                     default:
                         System.out.println("Invalid Choice.");
                         return;
                 }

                 System.out.println(category_menu);
                 System.out.print("Enter Item Name: ");
                 String i_name = sc.nextLine(); 

                System.out.println("---Available sizes---");
                System.out.println("1. Small - ₱350.00");
                System.out.println("2. Medium - ₱500.00");
                System.out.println("3. Large - ₱850.00");
                System.out.println("4. Extra Large - ₱1000.00");

                String size = "";
                float price = 0;
                boolean valid_size = false;

            while (!valid_size) {
                 System.out.print("Enter Size (1-4): ");
                    int i_size = sc.nextInt();
                    sc.nextLine();
                    switch (i_size) {
                        case 1:
                            size = "Small";
                            price = 350;
                            valid_size = true;
                            break;
                        case 2:
                            size = "Medium";
                            price = 500;
                            valid_size = true;
                            break;
                        case 3:
                            size = "Large";
                            price = 850;
                            valid_size = true;
                            break;
                        case 4:
                            size = "Extra-Large";
                            price = 1000;
                            valid_size = true;
                            break;
                        default:
                            System.out.println("Invalid Choice! Try again.");
                    }
                }

                    String checkQuery = "SELECT * FROM Item WHERE i_name = ? AND i_size = ?";
           java.util.List<java.util.Map<String, Object>> items = con.fetchRecords(checkQuery, i_name, size);

           String availability_status = "Available";

           if (!items.isEmpty()) {
               java.util.Map<String, Object> item = items.get(0);
               String currentStatus = item.get("i_availability_status").toString();

               if (currentStatus.equalsIgnoreCase("Rented")) {
                   System.out.println(" Sorry, this costume in size " + size + " is already rented.");
                   availability_status = "Rented";

               } else if (currentStatus.equalsIgnoreCase("Pending")) {
                   System.out.println(" This costume request is still pending staff approval.");
                   availability_status = "Pending";

               } else if (currentStatus.equalsIgnoreCase("Available")) {
                   System.out.println(" Costume is available. Submitting request for approval...");
                   availability_status = "Pending";


                   String updateStatus = "UPDATE Item SET i_availability_status = ? WHERE i_name = ? AND i_size = ?";
                   con.updateRecord(updateStatus, "Pending", i_name, size);
                   System.out.println(" Costume is now 'Pending'. Waiting for staff approval...");
               }

           } else {

               System.out.println("Costume Added...");
               String sqlitem = "INSERT INTO Item(i_category, i_name, i_size, i_rental_price, i_availability_status) VALUES (?, ?, ?, ?, ?)";
               con.addRecord(sqlitem, category_name, i_name, size, price, "Available");


               String updateStatus = "UPDATE Item SET i_availability_status = ? WHERE i_name = ? AND i_size = ?";
               con.updateRecord(updateStatus, "Pending", i_name, size);
               System.out.println(" Costume request sent! Status is now 'Pending' for staff approval.");
           }
                       break;
                   case 3:
                       viewrentalRecord();
                       break;
                   case 4:
                       System.out.println("Logging out...");
                       return;
               }
    }
}
