/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MAIN;

import Config.config;
import static MAIN.MAIN.viewCostumes;
import static MAIN.MAIN.viewUcr;
import static MAIN.MAIN.viewrentalRecord;
import java.util.Scanner;

/**
 *
 * @author Angie
 */
public class Staff {
    Scanner sc = new Scanner(System.in);
    config con = new config();
    
    public void Staff(){
        
                        System.out.println("=== Welcome to staff Dashboard ===");
                System.out.println("1. Manage Costume");
                System.out.println("2. Manage Rental");
                System.out.println("3. Logout");
                System.out.println("Enter Choice: ");
                int item_choice = sc.nextInt();
                
                sc.nextLine();
                
                switch(item_choice){
                    case 1:
                System.out.println("1. View Custome Item");
                System.out.println("2. Update Custome Status");
                System.out.println("3. Delete Rental Custome");
                System.out.println("Enter Choice: ");
                int choi = sc.nextInt();
                switch(choi){
                    case 1:
                        viewCostumes();
                        break;
                         case 2:
                   System.out.print("Enter Item ID to approve/rent: ");
                int approve_id = sc.nextInt();

                String checkPending = "SELECT * FROM Item WHERE i_id = ? AND i_availability_status = 'Pending'";
                java.util.List<java.util.Map<String, Object>> pendingItems = con.fetchRecords(checkPending, approve_id);

                if (pendingItems.isEmpty()) {
                    System.out.println(" No pending request found for that Item ID.");
                } else {
                    String approveQuery = "UPDATE Item SET i_availability_status = 'rented' WHERE i_id = ?";
                    con.updateRecord(approveQuery, approve_id);
                    System.out.println("Costume approved and marked as 'rented' successfully!");
                }
                   break;
                    case 3:
                        viewCostumes();
                        System.out.println("Enter Item Id to Delete: ");
                       int i_id = sc.nextInt();
                        
                        String i_delete = "DELETE FROM Item WHERE i_id = ?";
                        System.out.println("Item Deleted Successfully.");
                        viewCostumes();
                        break;
                }
                        break;
                   
                    case 2:
                        System.out.println("1. Add Rental Date");
                        System.out.println("2. View Rental Record");
                        System.out.println("3. Update Rental");
                        System.out.println("4. Delete Rental");  
                        System.out.println("5. Back");
                        System.out.print("Enter Choice: ");
                        int r_ch = sc.nextInt();
                        sc.nextLine();
                        
                        switch(r_ch){
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

                     String insertQry = "INSERT INTO Rental (i_id, r_date, return_date, total_amount) VALUES (?, ?, ?, ?, ?)";
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

            String deleteRental = "DELETE FROM Rental_Record WHERE r_id=?";
            con.updateRecord(deleteRental, deleteId);

            System.out.println(" Rental record deleted successfully!");
                                break;
                                 case 5:
                               return;
                        }
                        break;
                    case 3:
                        System.out.println("Logging out...");
                        return;
                }
    }
}
