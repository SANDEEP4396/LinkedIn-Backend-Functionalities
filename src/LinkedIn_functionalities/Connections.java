/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LinkedIn_functionalities;
import java.util.Date;
/**
 *
 * @author sande
 */
public class Connections {
    
    private String sender_id;
    private String receiver_id;
    private String status;
    private String message;
    private Date dateAndTime;

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(String receiver_id) {
        this.receiver_id = receiver_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(Date dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public Connections(String sender_id, String receiver_id, String status, String message, Date dateAndTime) {
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.status = status;
        this.message = message;
        this.dateAndTime = dateAndTime;
    }   
   
}
