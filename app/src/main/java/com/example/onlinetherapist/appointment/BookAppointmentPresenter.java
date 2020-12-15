package com.example.onlinetherapist.appointment;
import com.example.onlinetherapist.appointment.TimeRowModel;
public class BookAppointmentPresenter implements IBookAppointmentPresenter{
    IBookAppointmentView iBookAppointmentView;
    public BookAppointmentPresenter(IBookAppointmentView iBookAppointmentView)
    {
        this.iBookAppointmentView = iBookAppointmentView;
    }
    public void initTimeTable(TimeRowModel timeRowModel[])
    {
        iBookAppointmentView.initAvailableTimeTable();
    }
    public void bookAppointment(String date,int slot)
    {
        iBookAppointmentView.bookAppointment(date,slot);
    }

}
