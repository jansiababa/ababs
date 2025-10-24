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
    public static void viewUcr(){
         String viewMyRentals = ""
                + "  SELECT \n" +
              "        u.u_id, \n" +
              "        u.u_name, \n" +
              "        r.r_id, \n" +
              "        i.i_id,  \n" +
              "        i.i_name, \n" +
              "        i.i_availability_status\n" +
              "    FROM User u\n" +
              "    JOIN Rental r ON u.u_id = r.u_id\n" +
              "    JOIN Item i ON r.item_id = i.i_id\n" +
              "    WHERE u.u_status = 'Approved' AND u.u_type = 'Customer'"
                                            ;
               
          
          String[] rentHeaders = {"User ID", "Customer Name", "Rental ID", "Item ID", "Item Name", "Status"};
        String[] rentCols = {"u_id", "u_name", "r_id", "i_id", "i_name", "i_availability_status"};
         config conf = new config();
            conf.viewRecords(viewMyRentals, rentHeaders, rentCols);
    }
    public static void viewrentalRecord(){
         String votersQuery = "SELECT * FROM Rental";
        String[] votersHeaders = {"Rental ID", "Customer ID", "Item ID", "Rental Date", "Return Date", "Total Amount"};
        String[] votersColumns = {"r_id", "c_id", "i_name", "i_id", "r_date", "r_return", "total_amount"};
        config conf = new config();
        conf.viewRecords(votersQuery, votersHeaders, votersColumns);
    }
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        config con = new config();
        con.connectDB();
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
                sc.nextLine();
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
                  System.out.println("Select User Type: ");
                  System.out.println("1. Customer");
                  System.out.println("2. Admin");
                  System.out.println("3. Staff");
                  System.out.print("Enter Choice: ");
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
                
                String HashedPassword = con.hashPassword(u_pass);
                        
                String sqluser = "INSERT INTO User(u_name, u_contactNumber, u_email, u_type, u_status, u_password) VALUES (?, ?, ?, ?, ?, ?)";
                con.addRecord(sqluser, u_name, u_number, u_email, tp, "Pending", HashedPassword);   
                System.out.println("Registered Successfully.");
                break;
            case 2:
                System.out.println("=== Login Panel ===");
                System.out.print("Enter Email: ");
                u_email = sc.next();
                System.out.print("Enter password: ");
                u_pass = sc.next();
                
                String hashPass = con.hashPassword(u_pass);
                
                while(true){
                    String qry = "SELECT * FROM User WHERE u_email = ? AND u_password = ?";
                    java.util.List<java.util.Map<String, Object>> result = con.fetchRecords(qry, u_email, hashPass);
                    
                       if (result.isEmpty()) {
                           System.out.println("INVALID CREDENTIALS");
                                return;
                        } else {
                         java.util.Map<String, Object> user = result.get(0);
                        String stat = user.get("u_status").toString();
                       String u_type = user.get("u_type").toString();
 
                       if(stat.equals("Pending")){
                          System.out.println("Account is Pending, Wait for Admin Approval!");
                         return;
                       }else{
                      System.out.println("LOGIN SUCCESS!");
                       if(u_type.equals("Admin")){
                        Admin admin = new Admin();
                         admin.Admin();
                           }else if(u_type.equals("Customer")){
                              Customer customer = new Customer();
                              customer.Customer();
                           }else if(u_type.equals("Staff")){
                                Staff staff = new Staff();
                                staff.Staff();
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
 