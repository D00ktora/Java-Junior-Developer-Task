package com.markovskisolutions.JJDT.service.impl;

import com.google.gson.*;
import com.markovskisolutions.JJDT.model.DTO.ListOfViewDTO;
import com.markovskisolutions.JJDT.model.DTO.ViewDTO;
import com.markovskisolutions.JJDT.service.WebService;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class WebServiceImpl implements WebService {
    public String encodingBasic64(String username, String password) {
        String usernamePassword = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(usernamePassword.getBytes());
    }

    @Override
    public String getJWTToken() {
        String encode = encodingBasic64("x.x.x.xxxx@gmail.com", "testPassword");
        BufferedReader httpResponseReader = null;
        try {
            URL serverUrl = new URL("http://localhost:8080/api/token");
            HttpURLConnection urlConnection = (HttpURLConnection) serverUrl.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.addRequestProperty("Authorization", encode);
            httpResponseReader =
                    new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String lineRead = httpResponseReader.readLine();
            return lineRead;

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {

            if (httpResponseReader != null) {
                try {
                    httpResponseReader.close();
                } catch (IOException ioe) {
                    // Close quietly
                }
            }
        }
        return null;
    }

    @Override
    public ListOfViewDTO getListOfAllUsers() {
        BufferedReader httpResponseReader = null;
        String JWTToken = getJWTToken();
        Gson gson = new GsonBuilder().setPrettyPrinting()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        try {
            URL serverUrl = new URL("http://localhost:8080/");
            HttpURLConnection urlConnection = (HttpURLConnection) serverUrl.openConnection();

            urlConnection.setRequestMethod("GET");

            urlConnection.addRequestProperty("Authorization", "Bearer " + JWTToken);

            httpResponseReader =
                    new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String lineRead = httpResponseReader.readLine();

            return fromStringJsonToListOfViewDTO(lineRead);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {

            if (httpResponseReader != null) {
                try {
                    httpResponseReader.close();
                } catch (IOException ioe) {
                    // Close quietly
                }
            }
        }
        return null;
    }

    private static ListOfViewDTO fromStringJsonToListOfViewDTO(String lineRead) {
        ListOfViewDTO listOfViewDTO = new ListOfViewDTO();
        List<ViewDTO> vIewDTOList = new ArrayList<>();
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = (JsonArray) jsonParser.parse(lineRead);
        for (JsonElement jsonElement : jsonArray) {
            vIewDTOList.add(fromJsonElementToViewDTO(jsonElement));
        }
        listOfViewDTO.setViewDTO(vIewDTOList);
        return listOfViewDTO;
    }

    private static ViewDTO fromJsonElementToViewDTO(JsonElement jsonElement) {
        JsonObject asJsonObject = jsonElement.getAsJsonObject();
        String id = asJsonObject.get("id").toString().replace("\"", "");
        String firstName = asJsonObject.get("firstName").toString().replace("\"", "");
        String lastName = asJsonObject.get("lastName").toString().replace("\"", "");
        String dateOfBirth = asJsonObject.get("dateOfBirth").toString().replace("\"", "");
        String email = asJsonObject.get("email").toString().replace("\"", "");
        String phoneNumber = asJsonObject.get("phoneNumber").toString().replace("\"", "");
        ViewDTO vIewDTO = new ViewDTO()
                .setEmail(email)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setPhoneNumber(phoneNumber)
                .setId(Long.parseLong(id))
                .setDateOfBirth(LocalDate.parse(dateOfBirth));
        return vIewDTO;
    }

}
