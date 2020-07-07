package com.irisdemo.restm2.workersrv;


import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class WorkerSemaphore {

    private boolean green = false;

    public boolean green() {
        return green;
    }

    public void disableThreads() {
    	green = false;
    }

    public void allowThreads() {
    	green = true;
    }
}
