package com.markovskisolutions.JJDT.service;

import com.markovskisolutions.JJDT.model.DTO.ModifyDTO;
import com.markovskisolutions.JJDT.model.DTO.UserDTO;
import com.markovskisolutions.JJDT.model.DTO.ViewDTO;

import java.util.List;

public interface UserService {

    List<ViewDTO> getAllUsersOrdered();
    List<ViewDTO> getAllUsersByFirstName(String firstName);
    List<ViewDTO> getAllUsersByLastName(String lastName);
    List<ViewDTO> getAllUsersByEmail(String email);
    List<ViewDTO> getAllUsersByPhoneNumber(String phoneNumber);
    List<ViewDTO> getAllByDateOfBirth(String dateOfBirth);
    ViewDTO getUserById(Long id);
    Long createUser(UserDTO userDTO);
    boolean modifyUser(Long id, ModifyDTO modifyDTO);
    boolean deleteUser(Long id);

    List<ViewDTO> getUserWithPagination(int offset, int size);
}
