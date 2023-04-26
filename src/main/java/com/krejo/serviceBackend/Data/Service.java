package com.krejo.serviceBackend.Data;


import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class Service {

    @Setter(AccessLevel.NONE)
    private static int counter;

    @Setter(AccessLevel.NONE)
    private int id;
    private String name;
    private int employeeId;
    private String date;
    private String longitude;
    private String latitude;

    public Service () {
        counter++;
        this.id = counter;
    }
}
