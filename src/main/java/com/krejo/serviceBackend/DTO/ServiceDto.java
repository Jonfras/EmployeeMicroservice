package com.krejo.serviceBackend.DTO;

import com.krejo.serviceBackend.Data.TypeAndName;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Data
public class ServiceDto {

    public static final List<TypeAndName> fields = new ArrayList<>();

    private String name;
    private int employeeId;
    private String date;
    private String address;


}
