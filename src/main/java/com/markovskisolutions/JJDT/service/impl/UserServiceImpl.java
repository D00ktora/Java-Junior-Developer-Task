package com.markovskisolutions.JJDT.service.impl;

import com.markovskisolutions.JJDT.model.DTO.ModifyDTO;
import com.markovskisolutions.JJDT.model.DTO.UserDTO;
import com.markovskisolutions.JJDT.model.DTO.ViewDTO;
import com.markovskisolutions.JJDT.model.UserEntity;
import com.markovskisolutions.JJDT.repository.RoleRepository;
import com.markovskisolutions.JJDT.repository.UserRepository;
import com.markovskisolutions.JJDT.service.UserService;
import jakarta.validation.Valid;
import org.hibernate.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<ViewDTO> getAllUsersOrdered() {
        List<ViewDTO> allUsers = userRepository.findAllByOrderByLastNameAscDateOfBirthAsc()
                .stream()
                .map(this::mapView).toList();
        if (allUsers.isEmpty()) {
            throw new ObjectNotFoundException("user", UserEntity.class);
        }
        return allUsers;
    }

    @Override
    public List<ViewDTO> getAllUsersByFirstName(String firstName) {
        List<ViewDTO> usersByFirsName = userRepository.findAllByFirstName(firstName)
                .stream()
                .map(this::mapView).toList();
        if (usersByFirsName.isEmpty()) {
            throw new ObjectNotFoundException("user", UserEntity.class);
        }
        return usersByFirsName;
    }

    @Override
    public List<ViewDTO> getAllUsersByLastName(String lastName) {
        List<ViewDTO> usersByLastName = userRepository.findAllByLastName(lastName)
                .stream()
                .map(this::mapView).toList();
        if (usersByLastName.isEmpty()) {
            throw new ObjectNotFoundException("user", UserEntity.class);
        }
        return usersByLastName;
    }

    @Override
    public List<ViewDTO> getAllUsersByEmail(String email) {
        List<ViewDTO> usersByEmail = userRepository.findAllByEmail(email)
                .stream()
                .map(this::mapView).toList();
        if (usersByEmail.isEmpty()) {
            throw new ObjectNotFoundException("user", UserEntity.class);
        }
        return usersByEmail;
    }

    @Override
    public List<ViewDTO> getAllUsersByPhoneNumber(String phoneNumber) {
        List<ViewDTO> usersByPhone = userRepository.findAllByPhoneNumber(phoneNumber)
                .stream()
                .map(this::mapView).toList();
        if (usersByPhone.isEmpty()) {
            throw new ObjectNotFoundException("user", UserEntity.class);
        }
        return usersByPhone;
    }

    @Override
    public List<ViewDTO> getAllByDateOfBirth(String dateOfBirth) {
        return userRepository.findAllByDateOfBirth(LocalDate.parse(dateOfBirth))
                .stream().map(this::mapView).toList();
    }

    @Override
    public ViewDTO getUserById(Long id) {
        Optional<UserEntity> byId = userRepository.findById(id);
        if (byId.isEmpty()) {
            throw new ObjectNotFoundException("user", UserEntity.class);
        }
        ViewDTO mapped = mapView(byId.get());
        if (mapped == null) {
            throw new ObjectNotFoundException("user", UserEntity.class);
        }
        return mapped;
    }

    @Override
    public Long createUser(@Valid UserDTO userDTO) {
        UserEntity mapped = this.map(userDTO);
        if (mapped == null) {
            throw new NullPointerException("All fields are required! : firstName, lastName, email, dateOfBirth, phoneNumber");
        }
        mapped.setRoles(List.of(roleRepository.findById(2L).get()));
        String encode = passwordEncoder.encode(userDTO.getPassword());
        mapped.setPassword(encode);
        return userRepository.save(mapped).getId();
    }

    @Override
    public boolean modifyUser(Long id, ModifyDTO modifyDTO) {
        Optional<UserEntity> userById = userRepository.findById(id);
        if (userById.isEmpty()) {
            throw new ObjectNotFoundException("user", UserEntity.class);
        }

        UserEntity userEntity = userById.get();
        userEntity.setEmail(modifyDTO.getEmail())
                .setFirstName(modifyDTO.getFirstName())
                .setLastName(modifyDTO.getLastName())
                .setDateOfBirth(modifyDTO.getDateOfBirth())
                .setPhoneNumber(modifyDTO.getPhoneNumber());
        userRepository.saveAndFlush(userEntity);
        return true;
    }

    @Override
    public boolean deleteUser(Long id) {
        Optional<UserEntity> userById = userRepository.findById(id);
        if (userById.isEmpty()) {
            throw new ObjectNotFoundException("user", UserEntity.class);
        }
        userRepository.delete(userById.get());
        return true;
    }

    @Override
    public List<ViewDTO> getUserWithPagination(int offset, int size) {
        List<ViewDTO> list = userRepository.findAll(PageRequest.of(offset, size)).stream().map(this::mapView).toList();

        return list;
    }

    private UserDTO map(UserEntity userEntity) {
        UserDTO map = modelMapper.map(userEntity, UserDTO.class);
        if (
                map.getEmail() == null ||
                map.getFirstName() == null ||
                map.getLastName() == null ||
                map.getPhoneNumber() == null ||
                map.getDateOfBirth() == null
        ) {
            return null;
        }
        return map;
    }

    private ViewDTO mapView(UserEntity userEntity) {
        return modelMapper.map(userEntity, ViewDTO.class);
    }

    private UserEntity map(UserDTO userDTO) {
        UserEntity mapped = modelMapper.map(userDTO, UserEntity.class);
        if (
                mapped.getEmail() == null ||
                        mapped.getFirstName() == null ||
                        mapped.getLastName() == null ||
                        mapped.getPhoneNumber() == null ||
                        mapped.getDateOfBirth() == null
        ) {
            return null;
        }
        return mapped;
    }
}
