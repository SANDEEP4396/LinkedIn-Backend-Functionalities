/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LinkedIn_functionalities;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author sande
 */
public class Profile {

    private String emp_id;
    private String emp_passwd;
    private String emp_name;
    private String email_id;
    private String emp_company;
    private String emp_type;
    Scanner input = new Scanner (System.in);
     Connection con =null;
        Statement st = null;
        ResultSet rs = null;
        ResultSet resTwo=null;
        String[] jobsPosted= new String[8];
        String[] connectedPeople= new String[10];
         int i=0;
        final String url = 
                "jdbc:mysql://mis-sql.uhcl.edu/muralidhars5496?useSSL=false";
        final String db_id = "muralidhars5496";
        final String db_password = "1893894";
    public Profile(String empId,String empPasswd,String empName,String email,String company,String type) {
        emp_id = empId;
        emp_passwd =empPasswd;
        emp_name=empName;
        email_id=email;
        emp_company=company;
        emp_type=type;
    }

    public String getEmp_company() {
        return emp_company;
    }

    public void setEmp_company(String emp_company) {
        this.emp_company = emp_company;
    }

    public String getEmp_type() {
        return emp_type;
    }

    public void setEmp_type(String emp_type) {
        this.emp_type = emp_type;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getEmp_passwd() {
        return emp_passwd;
    }

    public void setEmp_passwd(String emp_passwd) {
        this.emp_passwd = emp_passwd;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public String getEmail() {
        return email_id;
    }

    public void setEmail(String email) {
        this.email_id = email;
    }
    
    public void displayAccountPage(){
        
        String[] recommeded=new String[8];
        String[] mutualConnection=new String[8];       
      
        try{
            con = DriverManager.getConnection(url,db_id,db_password);
            st =con.createStatement();
            String sql = "SELECT * "
                    + "FROM Profile WHERE emp_id='"+emp_id+"'";
            rs = st.executeQuery(sql);
            while(rs.next()){
                
                emp_company=rs.getString("company");
                email_id =rs.getString("email");
                emp_name=rs.getString("emp_name");
                emp_type =rs.getString("type");
                System.out.println("Welcome.. "+emp_name);
                System.out.println("Company: "+emp_company);
                System.out.println("Email: "+email_id);
                System.out.println("Type: "+emp_type);
            }
         
            int i=0;
            int k=0;
            sql = "SELECT * "
                    + "FROM connection WHERE emp_id='"+emp_id+"'";
            rs = st.executeQuery(sql);
           
            while(rs.next()){
                if(rs.getString("status").equalsIgnoreCase("new")||rs.getString("status").equalsIgnoreCase("ignored") ){
                    System.out.println("\n ****You have new connection requests from: ****");
                   Connections connection = new Connections(rs.getString("sender_id"), rs.getString("emp_id"), rs.getString("status"), rs.getString("message"),
                           rs.getDate("dateandtime"));
                    System.out.println(connection.getSender_id());
                    System.out.println("\n****Would you like to accept this connection request??\n Yes or No****");
                    String response = input.nextLine();
                    String new_status;
                    if(response.equalsIgnoreCase("yes")){
                        new_status="approved";
                    }else if(response.equalsIgnoreCase("no")){
                        new_status="denied";
                    }else{
                       new_status="ignored";
                    }
                    String sql2 ="Update connection SET status='"+new_status+"' where emp_id='"+emp_id+"'"
                            + "AND sender_id='"+connection.getSender_id()+"'";
                   Statement stTwo = con.createStatement() ;
                   stTwo.execute(sql2);  
                }
            }
            System.out.println("\n****You are connected with:****");
             sql = "SELECT * "
                    + "FROM connection WHERE emp_id='"+emp_id+"'";
            rs = st.executeQuery(sql);
            while(rs.next()){  
                if(rs.getString("status").equalsIgnoreCase("approved")){
                System.out.println(rs.getString("sender_id"));
                 connectedPeople[i] = rs.getString("sender_id");
                 i++;
                }
            }
            sql = "SELECT * "
                    + "FROM connection WHERE sender_id='"+emp_id+"'";
            rs = st.executeQuery(sql);
            while(rs.next()){  
                if(rs.getString("status").equalsIgnoreCase("approved")){
                System.out.println(rs.getString("emp_id"));
                 connectedPeople[i] = rs.getString("emp_id");
                 i++;
                }
            }
            int m=0;
            for(int j=0;j<connectedPeople.length;j++){
                sql = "SELECT * "
                    + "FROM connection WHERE emp_id='"+connectedPeople[j]+"'";
                rs = st.executeQuery(sql);
               while(rs.next()) {
                   mutualConnection[m]=rs.getString("emp_id");
                   recommeded[m] = rs.getString("sender_id");
                    m++;
               }                 
            }
            for(int s =0;s<connectedPeople.length;s++){
                for(int j=0;j<recommeded.length;j++){
                    if(connectedPeople[s]==null){
                        break;
                    }
                    if(connectedPeople[s].equals(recommeded[j])){
                        recommeded[j]="";
                    }
                }
            }
            
            for(int a=0;a<m;a++){
                 sql = "SELECT * "
                    + "FROM profile WHERE emp_id='"+recommeded[a]
                         +"'";
                rs = st.executeQuery(sql);
               while(rs.next()) {
                    if((rs.getString("company").equals(getEmp_company()))&&(!rs.getString("emp_id").equals(getEmp_id()))){
                  System.out.println("You can connect with: "+rs.getString("emp_name")+" Working in: "+rs.getString("company")+" "
                          + "Mutual connection:"+mutualConnection[a]);  
               } 
               }
               }
            System.out.println("\n****Jobs available****");
            for(int j=0;j<connectedPeople.length;j++){
                sql = "SELECT * "
                    + "FROM job_add WHERE creator_id='"+connectedPeople[j]+"' ORDER BY date DESC LIMIT 3";
                rs = st.executeQuery(sql);
                k=0;
               while(rs.next()) {
                   if(k<3){
                   jobsPosted[k]=rs.getString("description");
                   System.out.println(rs.getString("description")+" job has been posted by "+
                   connectedPeople[j]+" on: "+rs.getDate("date"));
                   k++;
                   }
               }
            }  
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
    
    public void seeConnections(){
       
        try {
            con = DriverManager.getConnection(url,db_id,db_password);
            st =con.createStatement();
        
            System.out.println("\n Please enter the ID of the person whose profile you would like to view!\n");
            String profile_id =input.nextLine();
           String sql = "SELECT * FROM profile WHERE emp_id='"+profile_id+"'";
            rs= st.executeQuery(sql);
            if(rs.next()){
                System.out.println("Name: "+rs.getString("emp_name"));
                System.out.println("Company: "+rs.getString("company"));
                System.out.println("Email ID: "+rs.getString("email"));
            }
            sql = "SELECT * FROM connection WHERE emp_id='"+profile_id+"' OR sender_id='"+profile_id+"'";
            rs = st.executeQuery(sql);
            if(rs.next())
             System.out.println("This person is connected with:");
            while(rs.next()){
                if(!((rs.getString("emp_id").equalsIgnoreCase(emp_id)) ||(rs.getString("emp_id").equalsIgnoreCase(emp_id)))){
                if(rs.getString("emp_id").equalsIgnoreCase(profile_id)){
                    System.out.println(rs.getString("sender_id"));
                }else{
                    System.out.println(rs.getString("emp_id"));
                }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally{
            try{
                con.close();
                st.close();
               // rs.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
    }
    
    public void sendConnectionRequest(){
        String status = "new";
        String message;
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        try {
            con = DriverManager.getConnection(url,db_id,db_password);
            st =con.createStatement();
            
            System.out.println("Would you like to make a new connection? Yes or No");
            if(input.nextLine().equalsIgnoreCase("yes")){
            System.out.println("With whom would you like to connect");
            String new_connection = input.nextLine();
            String sql = "SELECT * FROM connection WHERE emp_id='"+new_connection+"'";
            rs = st.executeQuery(sql);
            while(rs.next()){
                String existing_con = rs.getString("sender_id");
                if(emp_id.equalsIgnoreCase(existing_con)){
                    throw new Exception("Connection already exist");
                }
            }
            sql = "SELECT * FROM connection WHERE sender_id='"+new_connection+"'";
            rs = st.executeQuery(sql);
            while(rs.next()){
                String existing_con = rs.getString("emp_id");
                if(emp_id.equalsIgnoreCase(existing_con)){
                    throw new Exception("There might be an existing request or connection already exist");
                }
            }
            System.out.println("Send a personalized invite with a message");
            message = input.nextLine();
             sql = "SELECT company FROM profile WHERE emp_id='"+new_connection+"'";
            rs = st.executeQuery(sql);
            if(rs.next()){
            if(rs.getString("company").equalsIgnoreCase(emp_company)){     
              int r = st.executeUpdate("insert into connection values('"+emp_id+"','"+new_connection
            +"','"+status+"','"+message+"','"+formatter.format(date)+"')");  
                System.out.println("Connection request sent!");
            }else{
                throw new Exception("The person whom you selected is from another company"
                        + "\nPlease select the person from the same company");
            }
            }
            }
        } catch (Exception ex) {
        ex.printStackTrace();
        }finally{
            try{
                con.close();
                st.close();
               // rs.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        } 
    }
    public void createJobOpening(){
        //SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String job_id= null;
        String description=null;
        String company = null;
        String creator_id = null;
        
        Random rand = new Random(); 
        try {
             con = DriverManager.getConnection(url,db_id,db_password);
             st =con.createStatement();
             System.out.println("Do you want to post a new job opening? YES or NO");
             String res = input.nextLine();
             if(res.equalsIgnoreCase("yes")){
                  int rand_job_id = rand.nextInt(1000);  
                  System.out.println("Provide the job description");
                 job_id = (String.valueOf(rand_job_id));
                 description =input.nextLine();
                 company = this.emp_company;
                 creator_id = this.emp_id;
                 Jobs_adv jobs=new Jobs_adv(job_id, company, description, creator_id, date);
              int create_job = st.executeUpdate("insert into job_add values('"+jobs.getJob_id()+"','"+jobs.getCompany()
            +"','"+jobs.getDescription()+"','"+jobs.getCreator_id()+"','"+jobs.getDate_created()+"')"); 
              
             int update_job_con = st.executeUpdate("insert into job_shares values('"+emp_id+"','"+jobs.getJob_id()+"')"); 
                System.out.println("Job has been created successfully");
             }
        } catch (Exception e) {
            e.printStackTrace();
        }
        }
    public void shareJob(){
        
        try {
            con = DriverManager.getConnection(url,db_id,db_password);
             st =con.createStatement();
             
               for(int j=0;j<connectedPeople.length;j++){
               String sql = "SELECT * "
                    + "FROM job_shares WHERE profile_id='"+connectedPeople[j]+"'";
                rs = st.executeQuery(sql);
                    while(rs.next()) {
                   System.out.println("Job ID: "+rs.getString("job_id")+" has been posted by "+
                   connectedPeople[j]); 
               }
            }  
        } catch (Exception e) {
        }
    }
    
}
