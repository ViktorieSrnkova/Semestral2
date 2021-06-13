/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author VikyVixxxen
 */
class DateTime {
    
    private LocalDate date;
    private LocalTime time;
    
/**constructor
 * 
 * @param date
 * @param time 
 */
    public DateTime(LocalDate date, LocalTime time) {
        this.date = date;
        this.time = time;
    }
/**Returns date
 * 
 * @return LocalDate - date
 */
    public LocalDate getDate() {
        return date;
    }
/**Returns time
 * 
 * @return LocalTime - time
 */
    public LocalTime getTime() {
        return time;
    }
    /**Formats string date output
     * 
     * @return String - formated date and time
     */
    @Override
    public String toString() {
        return String.format("%16s  %10s",date,time);
    }
    
}
