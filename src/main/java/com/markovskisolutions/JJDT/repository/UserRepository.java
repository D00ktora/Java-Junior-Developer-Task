package com.markovskisolutions.JJDT.repository;

import com.markovskisolutions.JJDT.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findAllByOrderByLastNameAscDateOfBirthAsc();
    List<UserEntity> findAllByFirstName(String firstName);
    List<UserEntity> findAllByLastName(String lastName);
    List<UserEntity> findAllByDateOfBirth(LocalDate dateOfBirth);
    List<UserEntity> findAllByEmail(String email);
    List<UserEntity> findAllByPhoneNumber(String phoneNumber);
    Optional<UserEntity> findByEmail(String value);
}
