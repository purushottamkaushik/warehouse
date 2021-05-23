package com.warehouse.repo;

import com.warehouse.model.WhUserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WhUserTypeRepository extends JpaRepository<WhUserType, Integer> {

    // For register checkCode
    @Query("SELECT count(userCode) FROM WhUserType WHERE userCode=:code")
    Integer isCodeExists(String code);

    // For edit checkCode
    @Query("SELECT count(userCode) FROM WhUserType WHERE userCode=:code and id<>:id")
    Integer isCodeExists(String code, Integer id);

    // For register checkEmail
    @Query("SELECT count(userEmail) FROM WhUserType WHERE userEmail=:email")
    Integer isEmailExists(String email);

    // For edit checkEmail
    @Query("SELECT count(userEmail) FROM WhUserType WHERE userEmail=:email and id<>:id")
    Integer isEmailExists(String email, Integer id);

    // For register UserId
    @Query("SELECT count(userIdNum) FROM WhUserType WHERE userIdNum=:idNumber")
    Integer isIdNumberExists(String idNumber);

    // For edit check UserIdNum
    @Query("SELECT count(userIdNum) FROM WhUserType WHERE userIdNum=:idNumber and id<>:id")
    Integer isIdNumberExists(String idNumber, Integer id);

    // FOr chart generation
    @Query("SELECT userType,count(userType) FROM WhUserType GROUP BY userType")
    List<Object[]> getCountUserType();


}
