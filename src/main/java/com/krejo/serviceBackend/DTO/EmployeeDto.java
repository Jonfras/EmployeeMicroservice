package com.krejo.serviceBackend.DTO;

import com.krejo.serviceBackend.Data.TypeAndName;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Data
public class EmployeeDto {

    public static final List<TypeAndName> fields = new ArrayList<>();

    private String name;
    private String longitude;
    private String latitude;

//    public EmployeeDto(){
//        fields.add(new TypeAndName("String", "name"));
//        fields.add(new TypeAndName("String", "longitude"));
//        fields.add(new TypeAndName("String", "latitude"));
//    }
}

