package MAIN;

import Config.config;
import java.util.Scanner;

public class MAIN {

    public static void viewUser() {
        String votersQuery = "SELECT * FROM User";
        String[] votersHeaders = {"Customer Id","Customer Name", "Contact Number", "Email", "Type", "Status"};
        String[] votersColumns = {"u_id","u_name", "u_contactNumber", "u_email", "u_type", "u_status"};
        config conf = new config();
        conf.viewRecords(votersQuery, votersHeaders, votersColumns);
    }
    
    public static void viewCostumes() {
        String votersQuery = "SELECT * FROM Item";
        String[] votersHeaders = {"Item Id", "Category", "Name", "Size", "Price", "Status"};
        String[] votersColumns = {"i_id", "i_category", "i_name", "i_size", "i_rental_price", "i_availability_status"};
        config conf = new config();
        conf.viewRecords(votersQuery, votersHeaders, votersColumns);
    }
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        config con = new config();

         String resp;
         do{
        System.out.println("======WELCOME TO MY SYSTEM======");
        System.out.println("-------------------------------");
        System.out.println("-----COSTUME RENTAL SYSTEM-----");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Enter Choice: ");
        int choice = sc.nextInt();
        sc.nextLine(); 

       switch (choice) {
           case 1:
                System.out.println("=== Register Panel ===");
                System.out.println("----Input Registration Details----");

                System.out.print("Enter Customer Name: ");
                String u_name = sc.next();

                System.out.print("Enter Contact Number: ");
                String u_number = sc.next();

                System.out.print("Enter Email: ");
                String u_email = sc.next();
                
                   while (true) {
                            
                  String qry = "SELECT * FROM User WHERE u_email = ?";
                java.util.List<java.util.Map<String, Object>> result = con.fetchRecords(qry, u_email);

                  if (result.isEmpty()) {
                    break;
                  } else {
                     System.out.print("Email already exists, Enter other Email: ");
                      u_email = sc.next();
                            }
                        }
                  System.out.print("Enter user Type (1 - Customer/2 - Admin): ");
                    int type = sc.nextInt();
                        while(type > 3 || type < 1){
                            System.out.print("Invalid, choose between 1 to 3 only: ");
                            type = sc.nextInt();
                        }
                  String tp = "";
                     if(type == 1){
                       tp = "Customer";
                }else if(type == 2){
                       tp = "Admin";
                }else{
                      tp = "Staff";
                        }
                    
                System.out.print("Enter password: ");
                String u_pass = sc.next();
                
                String sqluser = "INSERT INTO User(u_name, u_contactNumber, u_email, u_type, u_status, u_password) VALUES (?, ?, ?, ?, ?, ?)";
                con.addRecord(sqluser, u_name, u_number, u_email, tp, "Pending", u_pass);   
                        System.out.println("Customer Added Successfully. ");
                break;
            case 2:
                System.out.println("=== Login Panel ===");
                System.out.println("Enter Email: ");
                u_email = sc.next();
                System.out.println("Enter password: ");
                u_pass = sc.next();
                
                while(true){
                    String qry = "SELECT * FROM User WHERE u_email = ? AND u_password = ?";
                    java.util.List<java.util.Map<String, Object>> result = con.fetchRecords(qry, u_email, u_pass);
                    
                       if (result.isEmpty()) {
                           System.out.println("INVALID CREDENTIALS");
                                return;
                        } else {
                         java.util.Map<String, Object> user = result.get(0);
                        String stat = user.get("u_status").toString();
                       String u_type = user.get("u_type").toString();
                       if(stat.equals("Pending")){
                          System.out.println("Account is Pending, Contact tha Admin!");
                         return;
                       }else{
                      System.out.println("LOGIN SUCCESS!");
                       if(u_type.equals("Admin")){
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
                        u_name = sc.next();
                         sc.nextLine();
                        System.out.println("Enter Contact Number: ");
                        u_number = sc.next();
                        System.out.println("Enter Email: ");
                        u_email = sc.next();
                        
                        
                        String u_update = "UPDATE User SET u_name = ?, u_contactNumber = ?, u_email = ?  WHERE u_id = ?";
                        con.updateRecord(u_update, u_name, u_number, u_email, u_id);
                        System.out.println("Updated Successfully.");
                         viewUser();
                        break;
                    case 3:
                        System.out.println("Enter Customer Id to delete: ");
                        u_id = sc.nextInt();
                        
                        String u_delete = "DELETE FROM Customer WHERE u_id = ?";
                        con.deleteRecord(u_delete, u_id);
                        System.out.println("Customer Deleted Successfully.");
                        break;
                                
                        }
                        break;
                        case 3:
                        System.out.println("1. Add Rental Date");
                        System.out.println("2. View Rental Record");
                        System.out.println("3. Update Rental");
                        System.out.println("4. Delete Rental");  
                        System.out.print("Enter Choice: ");
                        int ch = sc.nextInt();
                        sc.nextLine();
                        
                        switch(ch){
                            case 1:
                                if(u_type.equals("Approved")){
                                System.out.println("Enter Rental Date: ");
                                String rent_date = sc.next();
                                
                                System.out.println("Enter Return Date: ");
                                String return_date = sc.next();
                                }else{
                                    
                                }
                                break;
                            case 2:
                                break;
                            case 3:
                                break;
                                 case 4:
                                break;
                        }
                         break;
                    case 4:
                        System.out.println("Logging out ...");
                        return;
                         default:
                             System.out.println("Invalid Choice.");
                             break;
                        }
                           }else if(u_type.equals("Customer")){
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
                       System.out.println("2. Cultural Oufits");
                       System.out.println("3. Festival/Cheer Outfits");
                        System.out.println("==========================");
                       System.out.print("Enter Category Choice: ");
                       int category = sc.nextInt();
                      String category_menu = "";
                        switch(category){
                           case 1:
                 category_menu = "  System.out.println(\"---------Modern Outfits----------\");\n" +
"                System.out.println(\"*Hip-Hop Streetwear*\");\n" +
"                System.out.println(\"*Jazz Dance Sequin Outfit*\");\n" +
"                System.out.println(\"*Modern Ballet Leotard with Skirt*\");\n" +
"                System.out.println(\"*Contemporary Lyrical Flow Dress*\");\n" +
"                System.out.println(\"*K-pop Inspired Dance Outfit*\");\n" +
"                System.out.println(\"---------------------------------\");";
                               break;
                           case 2:
                 category_menu = " System.out.println(\"---------Cultural Outfits---------\");\n" +
"                System.out.println(\"*Barong Tagalog with Black Pants*\");\n" +
"                System.out.println(\"*Maria Clara Dress (Philippines)*\");\n" +
"                System.out.println(\"*Tinikling Bamboo Dance Outfit*\");\n" +
"                System.out.println(\"----------------------------------\");";
                               break;
                           case 3:
               category_menu = " System.out.println(\"------Festival/Cheer Outfits------\");\n" +
"                System.out.println(\"*Cheerleader Uniform*\");\n" +
"                System.out.println(\"*Mardi Gras Carnival Costume*\");\n" +
"                System.out.println(\"*Festival Street Dance Outfit*\");\n" +
"                System.out.println(\"*Neon Glow Dance Costume*\");\n" +
"                System.out.println(\"*Disco 80s Sequin Costume*\");\n" +
"                System.out.println(\"----------------------------------\");";

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
               con.addRecord(sqlitem, category_menu, i_name, size, price, "Available");


               String updateStatus = "UPDATE Item SET i_availability_status = ? WHERE i_name = ? AND i_size = ?";
               con.updateRecord(updateStatus, "Pending", i_name, size);
               System.out.println(" Costume request sent! Status is now 'Pending' for staff approval.");
           }
                       break;
                   case 3:
                       break;
                   case 4:
                       System.out.println("Logging out...");
                       return;
               }
                           }else if(u_type.equals("Staff")){
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
                        System.out.print("Enter Choice: ");
                        break;
                    case 3:
                        System.out.println("Logging out...");
                        return;
                }
                        }else{
                           System.out.println("Access Denied!");
                       }
                               
                       break;
                     }
                        }                         
               }
                break;
            case 3:
               System.exit(0);
                break;
            default:
                System.out.println("Invalid Choice. Try again");
        }
             System.out.println("Do you want to continue?(y/n): ");
             resp = sc.next();
         }while(resp.equalsIgnoreCase("y"));
        sc.close();
    }
}
 