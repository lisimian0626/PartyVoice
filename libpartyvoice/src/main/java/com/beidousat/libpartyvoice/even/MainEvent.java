package com.beidousat.libpartyvoice.even;

public class MainEvent {

    public int id;
    public Object data;


    public static MainEvent getEvent(int id) {
        return new MainEvent(id);
    }

    public static MainEvent getEvent(int id, Object data) {
        return new MainEvent(id, data);
    }

    protected MainEvent() {
    }

    protected MainEvent(int id) {
        this.id = id;
    }

    protected MainEvent(int id, Object data) {
        this.id = id;
        this.data = data;
    }

}
