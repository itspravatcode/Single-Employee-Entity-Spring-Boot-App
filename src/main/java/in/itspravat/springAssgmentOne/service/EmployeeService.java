package in.itspravat.springAssgmentOne.service;

import in.itspravat.springAssgmentOne.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;


import java.util.List;



public interface EmployeeService {
    List<Employee> getEmployees();

    List<Employee> getEmployeesWithSorting(String field, Sort.Direction direction);

    Employee getEmployee(int id);

    Employee addEmployee(Employee employee);

    Employee updateEmployee(int id, Employee updatedEmployee);

    void deleteEmployee(int id);

    List<Employee> getEmployeesByDepartment(String department);

    Page<Employee> getEmployeesWithPaginationAndSorting(int page, int size, String sortField, String sortDirection);

    List<Employee> searchEmployeesByName(String name);

    List<Employee> getEmployeesWithSalaryGreaterThan(double salary);



}
