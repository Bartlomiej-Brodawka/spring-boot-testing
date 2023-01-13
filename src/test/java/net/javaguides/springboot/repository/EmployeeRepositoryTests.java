package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Employee;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    public void setup() {
        employee = Employee.builder()
                .firstName("Tadeusz")
                .lastName("Srubka")
                .email("tadeusz@gmail.com")
                .build();
    }

    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee(){
        //when
        Employee savedEmployee = employeeRepository.save(employee);

        //then
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    @DisplayName("JUnit test for get all employees operation")
    @Test
    public void givenEmployeesList_whenFindAll_thenEmployeesList(){
        //given
        Employee employee1 = Employee.builder()
                .firstName("John")
                .lastName("Cena")
                .email("cena@gmail,com")
                .build();

        employeeRepository.save(employee);
        employeeRepository.save(employee1);

        //when
        List<Employee> employeeList = employeeRepository.findAll();

        //then
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    @DisplayName("JUnit test for get employee by id operation")
    @Test
    public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject(){
        //given
        employeeRepository.save(employee);

        //when
        Employee employeeDB = employeeRepository.findById(employee.getId()).get();

        //then
        assertThat(employeeDB).isNotNull();
    }

    @DisplayName("JUnit test for get employee by email operation")
    @Test
    public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject(){
        //given
        employeeRepository.save(employee);

        //when
        Employee employeeDB = employeeRepository.findByEmail(employee.getEmail()).get();

        //then
        assertThat(employeeDB).isNotNull();
    }

    @DisplayName("JUnit test for update employee operation")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee(){
        //given
        employeeRepository.save(employee);

        //when
        Employee savedEmployee = employeeRepository.findById(employee.getId()).get();
        savedEmployee.setEmail("marek@gmail.com");
        savedEmployee.setFirstName("Marek");
        Employee updatedEmployee =  employeeRepository.save(savedEmployee);

        //then
        assertThat(updatedEmployee.getEmail()).isEqualTo("marek@gmail.com");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("Marek");
    }

    @DisplayName("JUnit test for delete employee operation")
    @Test
    public void givenEmployeeObject_whenDelete_thenRemoveEmployee(){
        //given
        employeeRepository.save(employee);

        //when
        employeeRepository.deleteById(employee.getId());
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());

        //then
        assertThat(employeeOptional).isEmpty();
    }

    @DisplayName("JUnit test for custom query using JPQL with index")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQL_thenReturnEmployeeObject(){
        //given
        employeeRepository.save(employee);
        String firstName = "Tadeusz";
        String lastName = "Srubka";

        //when
        Employee savedEmployee = employeeRepository.findByJPQL(firstName, lastName);

        //then
        assertThat(savedEmployee).isNotNull();
    }

    @DisplayName("JUnit test for custom query using JPQL with Named params")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQLNamedParams_thenReturnEmployeeObject(){
        //given
        employeeRepository.save(employee);
        String firstName = "Tadeusz";
        String lastName = "Srubka";

        //when
        Employee savedEmployee = employeeRepository.findByJPQLNamedParams(firstName, lastName);

        //then
        assertThat(savedEmployee).isNotNull();
    }

    @DisplayName("JUnit test for custom query using native SQL with index")
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQL_thenReturnEmployeeObject(){
        //given
        employeeRepository.save(employee);
        String firstName = "Tadeusz";
        String lastName = "Srubka";

        //when
        Employee savedEmployee = employeeRepository.findByNativeSQL(employee.getFirstName(), employee.getLastName());

        //then
        assertThat(savedEmployee).isNotNull();
    }

    @DisplayName("JUnit test for custom query using native SQL with named params")
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQLNamedParams_thenReturnEmployeeObject(){
        //given
        employeeRepository.save(employee);
        String firstName = "Tadeusz";
        String lastName = "Srubka";

        //when
        Employee savedEmployee = employeeRepository.findByNativeSQLNamed(employee.getFirstName(), employee.getLastName());

        //then
        assertThat(savedEmployee).isNotNull();
    }

}
