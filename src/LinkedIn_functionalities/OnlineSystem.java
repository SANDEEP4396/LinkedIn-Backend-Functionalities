/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LinkedIn_functionalities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 *
 * @author sandeep sagar muralidhar- 1893894
 */
public class OnlineSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner input = new Scanner (System.in);
        
        System.out.println("New user ?? \n 1.Sign Up");
        System.out.println("Existing User ? \n 2.Login to your Account");
        
        String userInput = input.nextLine();
        if(userInput.equals("1")){
           SignUp();
            System.out.println("Do you want to login? Yes or No?");
            String userOption = input.nextLine();
            if(userOption.toLowerCase().equals("yes")){
                LogIn();
            }
        }else if(userInput.equals("2")){
            LogIn();          
        }else{
            System.err.println("Please enter a valid input. Select either 1 or 2");
        }
    }
    
    public static void LogIn(){
        Scanner input = new Scanner (System.in);
        Connection con =null;
        Statement st = null;
        ResultSet rs =null;
        boolean loggedIn =false;
        String empId="";
        String empPaswd="";
        String empName="";
        String email="";
        String type="";
        String company="" ;
        final String url = 
                "jdbc:mysql://mis-sql.uhcl.edu/muralidhars5496?useSSL=false";
        final String db_id = "muralidhars5496";
        final String db_password = "1893894";
        try{
            con = DriverManager.getConnection(url,db_id,db_password);
            st =con.createStatement();

            //Ask user to enter ID password type
            System.out.println("Please enter the Employee id");
            String id =input.nextLine();
            System.out.println("Please enter the password");
            String password = input.nextLine();
            String sql = "SELECT * "
                    + "FROM Profile WHERE emp_id='"+id+"'";
            rs = st.executeQuery(sql);
            while(rs.next()){
             empId = rs.getString(1);
             empPaswd = rs.getString(2);
             empName = rs.getString(6);
             email = rs.getString(5);
             type = rs.getString(3);
             company =rs.getString(4);
            if(empId.equalsIgnoreCase(id)&& empPaswd.equalsIgnoreCase(password)){
                 loggedIn=true;
            }
            }
             if(loggedIn==true){
                 Profile profileDetails = new Profile(empId, empPaswd, empName, email
                ,company,type);
                 profileDetails.displayAccountPage();
                 profileDetails.seeConnections();
                 profileDetails.sendConnectionRequest();
                 
                 //Only if the type is Recruiter
                 if(type.equalsIgnoreCase("recruiter")){
                 profileDetails.createJobOpening();
             }
                 profileDetails.shareJob();
             }
             if(loggedIn==false) throw new Exception("Please provide valid login details");
            }catch(Exception ex){
            ex.printStackTrace();
        }
        finally{
            try{
                con.close();
                st.close();
                rs.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
         
    }
    
    public static void SignUp(){
        Scanner input = new Scanner (System.in);
        Connection con =null;
        Statement st = null;
        ResultSet rs = null;
        final String url = 
                "jdbc:mysql://mis-sql.uhcl.edu/muralidhars5496?useSSL=false";
        final String db_id = "muralidhars5496";
        final String db_password = "1893894";
        
        try{
            con = DriverManager.getConnection(url,db_id,db_password);
            st =con.createStatement();
            //Ask user to enter ID password type
            System.out.println("Please enter the Employee id");
            String id =input.nextLine();
            System.out.println("Please enter the name of the employee");
            String emp_name = input.nextLine();
            System.out.println("Please enter the password");
            String password = input.nextLine();
            System.out.println("Is the employee recruiter or regular employee?");
            String type = input.nextLine();
            System.out.println("Please enter the Company name");
            String comp = input.nextLine();
            System.out.println("Please enter the email of the employee");
            String email = input.nextLine();
            
            if(id.equals(password))
                throw new Exception("ID and password cannot be the same");
             if(!(Pattern.matches("^[a-zA-Z0-9#?!*]{3,10}$", id)))
                throw new Exception("The user id must contain at least one "
                        + "letter, one digit and one special"
                        + " character from {#, ? !, *}");
             if(!(type.toLowerCase().equals("regular")|(type.toLowerCase().equals("recruiter"))))
                     throw new Exception("Type has to be regular or recruiter");
            int r = st.executeUpdate("insert into profile values('"+id+"','"+password
            +"','"+type+"','"+comp+"','"+email+"','"+emp_name+"')");
            System.out.println("Account Created");
        }catch(Exception ex){
            ex.printStackTrace();
        }
        finally{
            try{
                con.close();
                st.close();
               // rs.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
    }   
    
}

