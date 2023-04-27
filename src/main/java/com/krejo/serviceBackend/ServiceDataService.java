package com.krejo.serviceBackend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.krejo.serviceBackend.DTO.ServiceDto;
import com.krejo.serviceBackend.Data.Employee;
import com.krejo.serviceBackend.Data.Service;
import com.krejo.serviceBackend.Resource.EmployeeResource;
import com.krejo.serviceBackend.Resource.ServiceResource;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServiceDataService {

    private static final String getEmployee = "http://localhost:8080/serviceBackend/employees/";


    List<Service> serviceList = new ArrayList<>();

    public void createInitialListEntries() {
        Service s1 = new Service();
        s1.setName("test1");
        s1.setEmployeeId(1);
        s1.setDate("01.01.2001");
        s1.setLongitude("longitute1");
        s1.setLatitude("latitude1");

        Service s2 = new Service();
        s2.setName("test2");
        s2.setEmployeeId(2);
        s2.setDate("02.01.2002");
        s2.setLongitude("longitute2");
        s2.setLatitude("latitude2");


        serviceList.add(s1);
        serviceList.add(s2);

    }

    public List<ServiceResource> getServiceResources() {
        List<ServiceResource> result = new ArrayList<>();

        for (Service service :
                serviceList) {
            ServiceResource serviceResource = convertServiceToServiceResource(service);

            result.add(serviceResource);
        }

        return result;
    }

    private ServiceResource convertServiceToServiceResource(Service service) {
        ServiceResource serviceResource = new ServiceResource(service.getId());
        serviceResource.setName(service.getName());
        serviceResource.setEmployeeId(service.getEmployeeId());
        serviceResource.setDate(service.getDate());
        serviceResource.setLongitude(service.getLongitude());
        serviceResource.setLatitude(service.getLatitude());

        return serviceResource;
    }

    public ServiceResource addService(ServiceDto serviceDto) throws IOException {
        Service service = new Service();
        service.setName(serviceDto.getName());
        service.setDate(serviceDto.getDate());


        EmployeeResource employeeResource = getEmployeeByRESTCall(serviceDto.getEmployeeId());
        service.setEmployeeId(employeeResource.getId());
        service.setLongitude(employeeResource.getLongitude());
        service.setLatitude(employeeResource.getLatitude());

        serviceList.add(service);

        return convertServiceToServiceResource(service);

    }

    private EmployeeResource getEmployeeByRESTCall(int employeeId) throws IOException {

        HttpURLConnection httpURLConnection = null;
        String response = "";

        try {
            URL url = new URL(getEmployee + employeeId);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                response = getResponse(httpURLConnection);

            } else {
                System.out.println("GET Request failed: " + httpURLConnection.getResponseCode() + " " + httpURLConnection.getResponseMessage());
            }

            httpURLConnection.disconnect();

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }


        return EmployeeDataService.convertJsonToEmployeeResource(response);

    }



    private static String getResponse(HttpURLConnection httpURLConnection) throws IOException {

        StringBuilder response = new StringBuilder();
        BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();

        return response.toString();
    }

    public ServiceResource deleteService(int serviceId) throws RuntimeException {
        Service serviceToRemove = getServiceById(serviceId);
        if (serviceToRemove != null) {

            serviceList.remove(serviceToRemove);
            return convertServiceToServiceResource(serviceToRemove);

        } else {
            throw new RuntimeException();
        }
    }

    private Service getServiceById(int serviceId) {
        Optional<Service> optionalService = serviceList.stream()
                .filter(x -> x.getId() == serviceId)
                .findFirst();

        return optionalService.orElse(null);

    }

    public ServiceResource getServiceResource(int serviceId) throws RuntimeException {
        Service service = getServiceById(serviceId);

        if (service != null){
            return convertServiceToServiceResource(service);

        } else {
            throw new RuntimeException();
        }

    }

    public ServiceResource editService(int serviceId, ServiceDto serviceDto) throws IOException {
        Service originalService = getServiceById(serviceId);
        Service editedService = getServiceById(serviceId);

        if (editedService != null) {

            editedService.setName(serviceDto.getName());
            editedService.setDate(serviceDto.getDate());

            EmployeeResource employee = getEmployeeByRESTCall(serviceDto.getEmployeeId());
            editedService.setEmployeeId(serviceDto.getEmployeeId());
            editedService.setLongitude(employee.getLongitude());
            editedService.setLatitude(employee.getLatitude());

            serviceList.set(serviceList.indexOf(originalService), editedService);

            return convertServiceToServiceResource(editedService);
        } else {

            throw new RuntimeException();
        }

    }
}
