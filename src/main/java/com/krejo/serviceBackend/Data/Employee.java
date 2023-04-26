package com.krejo.serviceBackend.Data;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class Employee {

    @Setter(AccessLevel.NONE)
    private static int counter;
    @Setter(AccessLevel.NONE)

    private int id;
    private String name;
    private String longitude;
    private String latitude;

    public Employee (){
        counter++;
        this.id = counter;
    }
}
