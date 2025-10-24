/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MAIN;

import Config.config;
import static MAIN.MAIN.viewUcr;
import static MAIN.MAIN.viewUser;
import static MAIN.MAIN.viewrentalRecord;
import java.util.Scanner;

/**
 *
 * @author Angie
 */
public class Admin {
    Scanner sc = new Scanner(System.in);
    config con = new config();
    public void Admin(){
        
          System.out.println("WELCOME TO ADMIN DASHBOARD");
                        System.out.println("1. Approve Account!");
                        System.out.println("2. Manage Accounts");
                        System.out.println("3. Manage Rental Record");
                        System.out.println("4. Logout");
                        System.out.println("Enter choice: ");
                        int respo = sc.nextInt();
                                        
                        switch(respo){
                         case 1:
                         
                       System.out.print("Enter ID to Approve: ");
                      int ids = sc.nextInt();
                                                    
                   String sql = "UPDATE User SET u_status = ? WHERE u_id = ?";
                      con.updateRecord(sql, "Approved", ids);
                    break;
                               case 2:
                        System.out.println("1. View Accounts");          
                        System.out.println("2. Update Accounts");
                        System.out.println("3. Delete Accounts");
                        System.out.println("4. Back");
                        System.out.print("Enter Choice: ");
                        int respond = sc.nextInt();
                        
                        switch(respond){
                            case 1:
                                viewUser();
                                break;
                           case 2:
                          viewUser();
                        System.out.println("Enter Id to update: ");
                        int u_id = sc.nextInt();
                        
                        System.out.println("Enter Customer Name: ");
                        String u_name = sc.next();
                         sc.nextLine();
                        System.out.println("Enter Contact Number: ");
                        String u_number = sc.next();
                        System.out.println("Enter Email: ");
                       String u_email = sc.next();
                        
                        
                        String u_update = "UPDATE User SET u_name = ?, u_contactNumber = ?, u_email = ?  WHERE u_id = ?";
                        con.updateRecord(u_update, u_name, u_number, u_email, u_id);
                        System.out.println("Updated Successfully.");
                       viewUser();
                        break;
                    case 3:
                        System.out.println("Enter Customer Id to delete: ");
                        u_id = sc.nextInt();
                        
                        String u_delete = "DELETE FROM User WHERE u_id = ?";
                        con.deleteRecord(u_delete, u_id);
                        System.out.println("Customer Deleted Successfully.");
                        break;
                            case 4:
                          return;      
                        }
                        break;
                    
                        case 3:
                        System.out.println("1. Add Rental Date");
                        System.out.println("2. View Rental Record");
                        System.out.println("3. Update Rental");
                        System.out.println("4. Delete Rental");  
                        System.out.println("5. Back");
                        System.out.print("Enter Choice: ");
                        int ch = sc.nextInt();
                        sc.nextLine();
                        
                        switch(ch){
                            case 1:
            System.out.println("=== YOUR RENTALS ===");   
              viewUcr();
              System.out.print("\nEnter Item ID: ");
             int item_id = sc.nextInt();
             sc.nextLine();

             String getPriceQuery = "SELECT i_price FROM Item WHERE i_id=?";
             java.util.List<java.util.Map<String, Object>> priceResult = con.fetchRecords(getPriceQuery, item_id);

             if (priceResult.isEmpty()) {
                 System.out.println("Item not found!");
             } else {
                 double item_price = Double.parseDouble(priceResult.get(0).get("i_price").toString());

                 System.out.print("Enter Rental Date (YYYY-MM-DD): ");
                 String rent_date = sc.next();

                 System.out.print("Enter Return Date (YYYY-MM-DD): ");
                 String return_date = sc.next();

                 java.time.LocalDate start = java.time.LocalDate.parse(rent_date);
                 java.time.LocalDate end = java.time.LocalDate.parse(return_date);
                 long days = java.time.temporal.ChronoUnit.DAYS.between(start, end);

                 if (days <= 0) {
                     System.out.println(" Return date must be after rental date!");
                 } else {
                     double total_amount = item_price * days;

           String insertQry = "INSERT INTO Rental (i_id, r_date, return_date, total_amount) VALUES (?, ?, ?, ?)";
           con.updateRecord(insertQry, item_id, rent_date, return_date, total_amount);

                     System.out.println(" Rental record created successfully!");
                     System.out.println(" Total Amount: ₱" + total_amount);
                 }
             }
                                break;
                            case 2:
                                 viewrentalRecord(); 
                                break;
                            case 3:
             System.out.println("=== UPDATE RENTAL RECORD ===");
            System.out.print("Enter Record ID to Update: ");
            int recordId = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter New Return Date (YYYY-MM-DD): ");
            String newReturnDate = sc.nextLine();

            String updateRental = "UPDATE Rental_Record SET return_date=? WHERE r_id=?";
            con.updateRecord(updateRental, newReturnDate, recordId);

            System.out.println(" Rental record updated successfully!"); 
                  break;
               case 4:
             System.out.println("=== DELETE RENTAL RECORD ===");
            System.out.print("Enter ID to Delete: ");
            int deleteId = sc.nextInt();
            sc.nextLine();

            String deleteRental = "DELETE FROM Rental WHERE r_id=?";
            con.updateRecord(deleteRental, deleteId);

            System.out.println(" Rental record deleted successfully!");
               break;
                 case 5:
                   return;
                        }
                         break;
                    case 4:
                        System.out.println("Logging out ...");
                        return;
                         default:
                             System.out.println("Invalid Choice.");
                             break;
                        }
    }
}
