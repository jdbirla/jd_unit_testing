package com.jd.springboot.repository;

import com.jd.springboot.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by jd birla on 23-11-2022 at 09:49
 */

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    //JPQL query with index parameters
    @Query("select e from Employee e where e.firstName =?1 and  e.lastName=?2")
    Employee findByFirstLastName(String firstName , String lastName);

    //JPQL query with named parameters
    @Query("select e from Employee e where e.firstName =:firstName and  e.lastName=:lastName")
    Employee findByNamedParams(@Param("firstName") String firstName , @Param("lastName") String lastName);

    //Native sql query with index parameters
    @Query(value = "select * from employees e where e.fisrt_name =?1 and  e.last_name=?2" , nativeQuery = true)
    Employee findByNativeSql(String firstName , String lastName);

    //Native sql query with named parameters
    @Query(value = "select * from employees e where e.fisrt_name =:firstName and  e.last_name=:lastName" , nativeQuery = true)
    Employee findByNativeSqlNamedParams(@Param("firstName") String firstName , @Param("lastName") String lastName);
}
