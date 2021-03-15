package com.hcl.finalproject;

public class DataLock {
    private static DataLock INSTANCE = null;
    private Thread myObj = null;
    private int dataReturn = 0;

    private DataLock(){
    }

    public static DataLock getInstance(){
        if (INSTANCE == null)
            INSTANCE = new DataLock();
        return INSTANCE;
    }

    public void setObj(Thread myObj) {
        this.myObj = myObj;
    }

    public void getJSONLock() throws InterruptedException {
        synchronized (this.myObj) {
            if (dataReturn == 0)
                this.myObj.wait();
            else {
                dataReturn = 0;
            }
        }
    }

    public void releaseJSONLock() throws InterruptedException {
        synchronized (this.myObj) {
            dataReturn = 1;
            this.myObj.notifyAll();
        }
    }
}
