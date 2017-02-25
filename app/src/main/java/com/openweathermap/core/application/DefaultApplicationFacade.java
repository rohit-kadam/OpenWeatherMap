package com.openweathermap.core.application;

import com.openweathermap.core.interfaces.IClientContainer;

/**
 * Created by Rohit on 25-02-2017.
 */

public class DefaultApplicationFacade implements IApplicationFacade {
    IClientContainer clientContainer;

    public DefaultApplicationFacade(IClientContainer clientContainer) {
        this.clientContainer = clientContainer;
    }
}
