package com.krejo.serviceBackend.Resource;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class EmployeeResource {

    @Setter(AccessLevel.NONE)
    private int id;
    private String name;
    private String longitude;
    private String latitude;

    public EmployeeResource (int id){
        this.id = id;
    }
}
