package MAIN;

import Config.config;
import java.util.Scanner;

public class MAIN {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        config con = new config();

         String resp;
         do{
        System.out.println("======WELCOME TO MY SYSTEM======");
        System.out.println("-------------------------------");
        System.out.println("-----COSTUME RENTAL SYSTEM-----");
        System.out.println("1. Add Customer ");
        System.out.println("2. Add Costume Item ");
        System.out.println("3. Rental Record ");

        System.out.print("Enter Choice: ");
        int choice = sc.nextInt();
        sc.nextLine(); 

        switch (choice) {
            case 1:
                System.out.println("----Input Customer Details----");

                System.out.print("Enter Customer Name: ");
                String c_name = sc.nextLine();

                System.out.print("Enter Contact Number: ");
                String c_number = sc.nextLine();

                System.out.print("Enter Email: ");
                String c_email = sc.nextLine();

                String sqlcustomer = "INSERT INTO Customer(c_name, c_contactNumber, c_email) VALUES (?, ?, ?)";
                con.addRecord(sqlcustomer, c_name, c_number, c_email);
                break;

            case 2:
                System.out.println("----Dress Available----");
                System.out.println("---------Modern Outfits----------");
                System.out.println("*Hip-Hop Streetwear*");
                System.out.println("*Jazz Dance Sequin Outfit*");
                System.out.println("*Modern Ballet Leotard with Skirt*");
                System.out.println("*Contemporary Lyrical Flow Dress*");
                System.out.println("*K-pop Inspired Dance Outfit*");
                System.out.println("---------------------------------");
                System.out.println("---------Cultural Outfits---------");
                System.out.println("*Barong Tagalog with Black Pants*");
                System.out.println("*Maria Clara Dress (Philippines)*");
                System.out.println("*Tinikling Bamboo Dance Outfit*");
                System.out.println("----------------------------------");
                System.out.println("------Festival/Cheer Outfits------");
                System.out.println("*Cheerleader Uniform*");
                System.out.println("*Mardi Gras Carnival Costume*");
                System.out.println("*Festival Street Dance Outfit*");
                System.out.println("*Neon Glow Dance Costume*");
                System.out.println("*Disco 80s Sequin Costume*");
                System.out.println("----------------------------------");

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
                  String availability_status = "";
                  boolean valid_status = false;

     while (!valid_status) {
        System.out.print("Enter Availability Status (available/rented): ");
        availability_status = sc.nextLine().trim().toLowerCase();

        if (availability_status.equals("available") || availability_status.equals("rented")) {
            valid_status = true;
        } else {
            System.out.println("Invalid status. Please enter 'available' or 'rented'.");
        }
    }
                System.out.println("\n---You Chose---");
                System.out.println("Item Name: " + i_name);
                System.out.println("Item Size: " + size);
                System.out.println("Price: ₱" + price);
                System.out.println("Status: "+availability_status);
                
                String sqlitem = "INSERT INTO Item(i_name, i_size, i_rental_Price, i_availability_status) VALUES (?, ?, ?, ?)";
                con.addRecord(sqlitem, i_name, size, price, availability_status);
                break;

            case 3:
                System.out.println("----Rental Record----");
                
                System.out.println("Enter Customer ID: ");
                String c_id = sc.next();
                
                String sqlc_check = "*SELECT COUNT(*) FROM Customer WHERE c_id = ?";
                int c_count = con.getCount(sqlc_check, c_id);
                
                if(c_count == 0){
                    System.out.println("Customer ID not found.");
                    break;
                }
                
                System.out.println("Enter Item ID: ");
                String i_id = sc.next();
                
                String sqli_check = "SELECT COUNT(*) FROM Item WHERE i_id = ?";
                int i_count = con.getCount(sqli_check, i_id);
                
                if(i_count == 0){
                    System.out.println("Item ID not found.");
                    break;
                }
                
                String status = con.getSingleValue("SELECT i_availability_status FROM Item WHERE i_id = ?", i_id);
                 if(status.equalsIgnoreCase("rented")) {
                    System.out.println("Item is already rented.");
                    break;
                 }

                if(status.equalsIgnoreCase("rented")){
                    System.out.println("Item is Already Rented.");
                    break;
                }
                
                
                System.out.println("Enter Date: ");
                String date =sc.next();
                
                System.out.println("Enter Return Date: ");
                String r_date = sc.next();
                
                String sqlUpdate = "UPDATE Item SET i_availability_status = 'rented' WHERE i_id = ?";
    con.updateRecord(sqlUpdate, i_id);

    System.out.println("✅ Rental recorded for Customer " + c_id + " and Item " + i_id);
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
