package cn.itcast.pojo;

import java.io.Serializable;
import java.util.Date;

public class Calendar implements Serializable {
    private Integer date;
    private Integer number;
    private Integer reservations;

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getReservations() {
        return reservations;
    }

    public void setReservations(Integer reservations) {
        this.reservations = reservations;
    }
}
