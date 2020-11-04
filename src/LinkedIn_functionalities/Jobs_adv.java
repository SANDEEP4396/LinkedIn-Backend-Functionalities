/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LinkedIn_functionalities;

import java.sql.Date;

/**
 *
 * @author sande
 */
public class Jobs_adv {
    
    private String job_id;
    private String company;
    private String description;
    private String creator_id;
    private Date date_created;

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(String creator_id) {
        this.creator_id = creator_id;
    }

    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }

    public Jobs_adv(String job_id, String company, String description, String creator_id, Date date_created) {
        this.job_id = job_id;
        this.company = company;
        this.description = description;
        this.creator_id = creator_id;
        this.date_created = date_created;
    }
    
}
