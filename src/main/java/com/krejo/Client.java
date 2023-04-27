package com.krejo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.krejo.serviceBackend.DTO.EmployeeDto;
import com.krejo.serviceBackend.DTO.ServiceDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {

    private static final String EMPLOYEES = "employees";
    private static final String SERVICES = "services";

    private static final String URL = "http://localhost:8080/serviceBackend/";
    private static final String SLASH = "/";

    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String PUT = "PUT";
    private static final String DELETE = "DELETE";


    //Farbcodes:
    public static final String RESET = "\033[0m"; // Text Reset
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN_BRIGHT = "\033[0;92m";   // GREEN_BRIGHT
    public static final String YELLOW_BRIGHT = "\033[0;93m";  // YELLOW
    public static final String BLUE = "\033[0;34m";    // BLUE
    public static final String WHITE_UNDERLINED = "\033[1;97m";
    public static final String WHITE_BACKGROUND_BRIGHT = "\033[0;107m";   // WHITE
    public static final String BLACK_BOLD = "\033[1;30m";  // BLACK

    private static Scanner systemScanner = new Scanner(System.in);

    private static final ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


    public static void main(String[] args) throws JsonProcessingException {

        int clientInput = 0;
        do {
            showMenu();
            //TODO: moch des so dass ned obstürtzt bei asdf ois input
            clientInput = Integer.parseInt(systemScanner.nextLine());

            switch (clientInput) {
                case 1 -> getEmployee();
                case 2 -> getAllEmployees();
                case 3 -> addEmployee();
                case 4 -> getService();
                case 5 -> getAllServices();
                case 6 -> addService();
                case 7 -> editService();
                case 8 -> removeService();
            }
        } while (clientInput != -1);


    }

    private static void getEmployee() {
        int employeeId = askForId(EMPLOYEES);
        String response = sendRequest(EMPLOYEES + SLASH + employeeId, GET, "");
        printResponse(response);
    }

    private static void getAllEmployees() {
        String response = sendRequest(EMPLOYEES, GET, "");
        printResponse(response);
    }

    private static void addEmployee() throws JsonProcessingException {
        // und don mittels objectMapper in an json String umwandelt
        EmployeeDto employeeDto = readEmployeeDto();

        String json = objectMapper.writeValueAsString(employeeDto);
        String response = sendRequest(EMPLOYEES, POST, json);
        printResponse(response);
    }

    private static void getService() {
        int serviceId = askForId(SERVICES);
        String response = sendRequest(SERVICES + SLASH + serviceId, GET, "");
        printResponse(response);
    }

    private static void getAllServices() {
        String response = sendRequest(SERVICES, GET, "");
        printResponse(response);
    }

    private static void addService() throws JsonProcessingException {
        ServiceDto serviceDto = readServiceDto();

        String json = objectMapper.writeValueAsString(serviceDto);
        String response = sendRequest(SERVICES, POST, json);
        printResponse(response);
    }

    private static void editService() throws JsonProcessingException {
        int serviceId = askForId(SERVICES);
        System.out.println("Now updated values: ");
        ServiceDto serviceDto = readServiceDto();

        String json = objectMapper.writeValueAsString(serviceDto);
        String response = sendRequest(SERVICES + SLASH + serviceId, PUT, json);
        printResponse(response);
    }

    private static void removeService() {
        int serviceId = askForId(SERVICES);
        String response = sendRequest(SERVICES + SLASH + serviceId, DELETE, "");
        printResponse(response);

    }


    private static EmployeeDto readEmployeeDto() {
        EmployeeDto employeeDto = new EmployeeDto();

        String condition = "";
        do {
            System.out.println("Enter valid inputs for each field and press enter:");

            System.out.println("name:");
            employeeDto.setName(systemScanner.nextLine());

            System.out.println("longitude:");
            employeeDto.setLongitude(systemScanner.nextLine());

            System.out.println("latitude:");
            employeeDto.setLatitude(systemScanner.nextLine());

            System.out.println("Are these correct?: \n name: " + employeeDto.getName() +
                    " \n longitude: " + employeeDto.getLongitude() +
                    " \n latitude: " + employeeDto.getLatitude() +
                    " \n Enter 1 if they are incorrect!");

            condition = systemScanner.nextLine();

        } while (condition.equals("1"));


        return employeeDto;
    }


    private static ServiceDto readServiceDto() {
        ServiceDto serviceDto = new ServiceDto();

        String condition = "";

        do {
            System.out.println("Enter valid inputs for each field and press enter:");

            System.out.println("name:");
            serviceDto.setName(systemScanner.nextLine());

            System.out.println("date:");
            serviceDto.setDate(systemScanner.nextLine());

            System.out.println("address:");
            serviceDto.setAddress(systemScanner.nextLine());

            System.out.println("employeeId:");
            try {
                serviceDto.setEmployeeId(Integer.parseInt(systemScanner.nextLine()));

                System.out.println("Are these correct?: \n name: " + serviceDto.getName() +
                        " \n date: " + serviceDto.getDate() +
                        " \n address: " + serviceDto.getAddress() +
                        " \n employeeId: " + serviceDto.getEmployeeId() +
                        " \n Enter 1 if they are incorrect!");

                condition = systemScanner.nextLine();

            } catch (NumberFormatException e) {
                condition = "1";
            }

        } while (condition.equals("1"));


        return serviceDto;
    }


    private static void printResponse(String response) {
        System.out.println(WHITE_BACKGROUND_BRIGHT + BLACK_BOLD + response + RESET + "\n");
    }

    private static String sendRequest(String URI, String HTTP_METHOD, String body) {
        HttpURLConnection httpURLConnection = null;
        String response = "";

        try {
            httpURLConnection = getHttpURLConnection(URI, HTTP_METHOD);


            if (HTTP_METHOD.equals(POST) || HTTP_METHOD.equals(PUT)) {
                writeOutput(body, httpURLConnection);
            }


            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                response = getResponse(httpURLConnection);

            } else {
                //Todo: error-handling bei serverinternen Fehlern
                System.out.println(HTTP_METHOD + " Request failed: " + httpURLConnection.getResponseCode() + " " + httpURLConnection.getResponseMessage());
            }

            httpURLConnection.disconnect();

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return response;

    }

    private static void writeOutput(String body, HttpURLConnection httpURLConnection) throws IOException {
        try (OutputStream os = httpURLConnection.getOutputStream()) {
            byte[] input = body.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
    }

    private static HttpURLConnection getHttpURLConnection(String URI, String HTTP_METHOD) throws IOException {
        HttpURLConnection httpURLConnection;

        URL url = new URL(URL + URI);
        httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod(HTTP_METHOD);

        if (HTTP_METHOD.equals(POST) || HTTP_METHOD.equals(PUT)) {

            httpURLConnection.setRequestProperty("Content-Type", "application/json; utf-8");
            httpURLConnection.setRequestProperty("Accept", "application/json");
            httpURLConnection.setDoOutput(true);

        }
        return httpURLConnection;
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

    private static int askForId(String wishedElement) {
        int id = 0;
        boolean validInput = false;

        while (!validInput) {

            System.out.print("Enter a valid id: ");

            try {
                id = Integer.parseInt(systemScanner.nextLine());
                validInput = true;

            } catch (Exception e) {
                System.out.println(RED + "Invalid id!!" + RESET);
            }
        }


        return id;
    }

    private static void showMenu() {
        System.out.println(WHITE_UNDERLINED + "Possible Endpoints:" + RESET);
        System.out.println();

        System.out.println("FOR EMPLOYEES:");
        System.out.println(GREEN_BRIGHT + "1...GET getEmployee");
        System.out.println("2...GET getAllEmployees");
        System.out.println(YELLOW_BRIGHT + "3...POST addEmployee" + RESET);

        System.out.println();

        System.out.println("FOR SERVICES:");
        System.out.println(GREEN_BRIGHT + "4...GET getService");
        System.out.println("5...GET getAllServices");
        System.out.println(YELLOW_BRIGHT + "6...POST addService");
        System.out.println((BLUE + "7...PUT editService"));
        System.out.println(RED + "8...DELETE removeService" + RESET);

        System.out.println();

        System.out.println("Enter the number next to the method you want to use or enter -1 to end the programm:");
    }
}
