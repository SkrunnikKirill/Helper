package com.example.alex.helppeopletogether.SupportClasses;

import com.example.alex.helppeopletogether.registration.Login;
import com.example.alex.helppeopletogether.registration.Registration;

/**
 * Created by PM on 29.06.2016.
 */
public class GetUserId {
    public String getUserId(String idUser) {
        Login login = new Login();
        Registration registration = new Registration();
        if (login.userId != null) {
            idUser = String.valueOf(login.userId);
        } else if (registration.responseFromServiseRegistrationId != null) {
            idUser = String.valueOf(registration.responseFromServiseRegistrationId);
        }

        return idUser;
    }


}
