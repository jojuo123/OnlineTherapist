package com.example.onlinetherapist.appointment;
import com.example.onlinetherapist.appointment.TimeRowModel;
import com.google.firebase.database.PropertyName;

public class TimeSlotModel {
    private String Date;
    private long Slot;
    private long Status;
    private String User_ID;


    public TimeSlotModel(String Date, long Slot, long Status, String User_ID){
        this.Date = Date;
        this.Slot = Slot;
        this.Status =Status;

        this.User_ID = User_ID;
    }


    @PropertyName("Status")
    public long getStatus() {
        return this.Status;
    }
    @PropertyName("Status")
    public void setStatus(long Status) {
        this.Status = Status;
    }
    @PropertyName("Slot")
    public long getSlot() {
        return Slot;
    }
    @PropertyName("Slot")
    public void setSlot(long Slot) {
        this.Slot = Slot;
    }
    @PropertyName("Date")
    public String getDate() {
        return Date;
    }
    @PropertyName("Date")
    public void setDate(String Date) {
        this.Date = Date;
    }
    @PropertyName("User_ID")
    public String getUser_ID() {
        return User_ID;
    }
    @PropertyName("User_ID")
    public void setUser_ID(String User_ID) {
        this.User_ID = User_ID;
    }


}
