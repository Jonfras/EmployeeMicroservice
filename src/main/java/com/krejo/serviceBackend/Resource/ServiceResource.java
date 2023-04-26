package com.krejo.serviceBackend.Resource;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ServiceResource {

    @Setter(AccessLevel.NONE)

    private int id;
    private String name;
    private int employeeId;
    private String date;
    private String longitude;
    private String latitude;

    public ServiceResource (int id) {
        this.id = id;
    }
}

