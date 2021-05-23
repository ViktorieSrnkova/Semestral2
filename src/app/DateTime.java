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

    public DateTime(LocalDate date, LocalTime time) {
        this.date = date;
        this.time = time;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
    
    @Override
    public String toString() {
        return String.format("%16s  %10s",date,time);
    }
}
