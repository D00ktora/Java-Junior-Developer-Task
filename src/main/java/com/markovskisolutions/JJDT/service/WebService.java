package com.markovskisolutions.JJDT.service;

import com.markovskisolutions.JJDT.model.DTO.ListOfViewDTO;

import java.io.IOException;

public interface WebService {
    String encodingBasic64(String username, String password);
    String getJWTToken() throws IOException;
    ListOfViewDTO getListOfAllUsers();
}
