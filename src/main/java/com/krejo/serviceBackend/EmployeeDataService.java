package com.krejo.serviceBackend;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.krejo.serviceBackend.DTO.EmployeeDto;
import com.krejo.serviceBackend.Data.Employee;
import com.krejo.serviceBackend.Resource.EmployeeResource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeDataService {

    private List<Employee> employeeList = new ArrayList<>();



    protected void createInitialListEntries(){
        Employee employee1 = new Employee();
        employee1.setName("test1");
        employee1.setLongitude("longitute1");
        employee1.setLatitude("latitude1");

        Employee employee2 = new Employee();
        employee2.setName("test2");
        employee2.setLongitude("longitute2");
        employee2.setLatitude("latitude2");

        employeeList.add(employee1);
        employeeList.add(employee2);

    }

    public List<EmployeeResource> getEmployeeResources() {
        List<EmployeeResource> result = new ArrayList<>();

        for (Employee employee :
                employeeList) {
            EmployeeResource employeeResource = convertEmployeeToEmployeeResource(employee);
            result.add(employeeResource);

        }
        return result;
    }

    public EmployeeResource addEmployee(EmployeeDto employeeDto) {

        Employee employee = new Employee();
        employee.setName(employeeDto.getName());
        employee.setLongitude(employeeDto.getLongitude());
        employee.setLatitude(employeeDto.getLatitude());

        employeeList.add(employee);

        return convertEmployeeToEmployeeResource(employee);
    }

    private static EmployeeResource convertEmployeeToEmployeeResource(Employee employee) {
        EmployeeResource employeeResource = new EmployeeResource(employee.getId());

        employeeResource.setName(employee.getName());
        employeeResource.setLongitude(employee.getLongitude());
        employeeResource.setLatitude(employee.getLatitude());

        return employeeResource;
    }

    public static EmployeeResource convertJsonToEmployeeResource(String response) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return objectMapper.readValue(response, EmployeeResource.class);
    }



    public EmployeeResource getEmployeeResource(int id) throws RuntimeException {
        Employee employee = getEmployeeById(id);

        if (employee != null){
            return convertEmployeeToEmployeeResource(employee);
        } else {
            throw new RuntimeException();
        }

    }

    public Employee getEmployeeById(int id) {

        Optional<Employee> optionalEmployee = employeeList.stream()
                .filter(x -> x.getId() == id)
                .findFirst();

        return optionalEmployee.orElse(null);

    }
}
