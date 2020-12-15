package com.example.onlinetherapist.appointment;
import com.example.onlinetherapist.appointment.TimeRowModel;
public class TimeSlotModel {
    private String date;
    private int slot;
    private int status;
    private int user_id;


    public TimeSlotModel(String date, int slot, int status, int user_id){
        this.date = date;
        this.slot = slot;
        this.status =status;

        this.user_id = user_id;
    }



    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getUser_id() {
        return user_id;
    }

    public TimeRowModel toTimeRowModel()
    {
        return new TimeRowModel("1/12/2020",true,true);
    }
}
