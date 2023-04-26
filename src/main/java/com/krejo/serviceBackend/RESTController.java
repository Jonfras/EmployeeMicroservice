package com.krejo.serviceBackend;

import com.krejo.serviceBackend.DTO.EmployeeDto;
import com.krejo.serviceBackend.DTO.ServiceDto;
import com.krejo.serviceBackend.Resource.EmployeeResource;
import com.krejo.serviceBackend.Resource.ServiceResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/serviceBackend")
public class RESTController {

    @Autowired
    private EmployeeDataService employeeDataService;
    @Autowired
    private ServiceDataService serviceDataService;


    @RequestMapping(value = "/employees", method = RequestMethod.GET)
    public List<EmployeeResource> getAllEmployees(){
        List<EmployeeResource> employeeResourceList = employeeDataService.getEmployeeResources();

        return employeeResourceList;
    }

    @RequestMapping(value = "/employees", method = RequestMethod.POST)
    public EmployeeResource addEmployee(@RequestBody EmployeeDto employeeDto) {
        return employeeDataService.addEmployee(employeeDto);
    }

    @GetMapping("/services")
    public List<ServiceResource> getAllServices() {
        List<ServiceResource> serviceResourceList = serviceDataService.getServiceResources();

        return serviceResourceList;
    }

    //Todo: entsprechenden Postman POST Request mocha
    @PostMapping("/services")
    public ServiceResource addService(@RequestBody ServiceDto serviceDto) {
        return serviceDataService.addService(serviceDto);
    }

}
