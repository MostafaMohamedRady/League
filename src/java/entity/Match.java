/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Bakar M.M.R
 */
public class Match {
    
    private Date calendar;
    
    String home ;
    
    String guest;

    Match(Date time, String clubName, String clubName0) {
        this.calendar=time;
        this.guest=clubName;
        this.home=clubName0;
                
    }

    public Date getCalendar() {
        return calendar;
    }

    public void setCalendar(Date calendar) {
        this.calendar = calendar;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getGuest() {
        return guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    @Override
    public String toString() {
        String name="";
        name+=" "+home+"  "+guest;
        return name;
    }
    
    
    
}
