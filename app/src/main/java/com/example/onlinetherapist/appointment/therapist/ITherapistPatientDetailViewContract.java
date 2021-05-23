package com.example.onlinetherapist.appointment.therapist;

import android.content.Context;

public interface ITherapistPatientDetailViewContract {
    interface View{
        void cancelSuccessful();
        void cancelFail();
        void setDialog(String username,String date);
    }

    interface Presenter{
        void cancelAppointment(String username,String date);
        void removeAppointment(String username,String date);
    }
}
