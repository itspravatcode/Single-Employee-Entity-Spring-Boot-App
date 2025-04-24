package in.itspravat.springAssgmentOne.service.Imple;

import in.itspravat.springAssgmentOne.model.Employee;
import in.itspravat.springAssgmentOne.repository.EmployeeRepo;
import in.itspravat.springAssgmentOne.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class EmployeeServiceImple implements EmployeeService {

    EmployeeRepo repo;

    public EmployeeServiceImple(EmployeeRepo repo) {
        this.repo = repo;
    }

    @Override
    public List<Employee> getEmployees() {
       List<Employee> employees = repo.findAll();
       List<Employee> employeesList = new ArrayList<>();
       for(Employee e: employees){

           if(  e.getIsDeleted() == false){
               employeesList.add(e);
           }

       }
        System.out.println(employeesList);
        return employeesList;
    }

    @Override
    public List<Employee> getEmployeesWithSorting(String field, Sort.Direction direction) {
        if (field == null || field.trim().isEmpty()) {
            throw new IllegalArgumentException("Sort field must not be null or empty");
        }
        Sort sort = Sort.by(direction, field);
        return repo.findAll(sort);
    }


    @Override
    public Employee getEmployee(int id) {
        Employee employee = repo.findById(id).orElse(null);

        if (employee == null || employee.getIsDeleted()) {
            throw new EntityNotFoundException("Employee with ID " + id + " not found or has been deleted.");
        } else {
            return employee;
        }
    }


    @Override
    public Employee addEmployee(Employee employee) {
        employee.setIsDeleted(false);
        employee.setCreatedAt(new Date());
        employee.setUpdatedAt(new Date());

        return repo.save(employee);
    }

    @Override
    public Employee updateEmployee(int id, Employee updatedEmployee)  {
        Optional<Employee> employee = repo.findById(id);

        if(employee.isPresent()){

            Employee existinEmployee = employee.get();

            existinEmployee.setName(updatedEmployee.getName());
            existinEmployee.setEmail(updatedEmployee.getEmail());
            existinEmployee.setDepartment(updatedEmployee.getDepartment());
            existinEmployee.setSalary(updatedEmployee.getSalary());

            existinEmployee.setUpdatedAt(new Date());

           return repo.save(existinEmployee);

        }
        else {
            throw  new EntityNotFoundException(id+"not found");
        }

    }

    @Override
    public void deleteEmployee(int id) {
        Optional<Employee> employee = repo.findById(id);
        if (employee.isPresent()) {
            Employee emp = employee.get();
            emp.setName("deleted_" + id);
            emp.setEmail("deleted_" + id + "@deleted.com");
            emp.setIsDeleted(true);
            repo.save(emp);
        } else {
            throw new EntityNotFoundException("Employee with id " + id + " not found");
        }
    }


    @Override
    public List<Employee> getEmployeesByDepartment(String department) {
        return repo.findByDepartmentIgnoreCaseAndIsDeletedFalse(department);
    }

    @Override
    public Page<Employee> getEmployeesWithPaginationAndSorting(int page, int size, String sortField, String sortDirection) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
        return repo.findAllByIsDeletedFalse(pageable);
    }

    @Override
    public List<Employee> searchEmployeesByName(String name) {
        return repo.findByNameContainingIgnoreCaseAndIsDeletedFalse(name);
    }

    @Override
    public List<Employee> getEmployeesWithSalaryGreaterThan(double salary) {
        return repo.findBySalaryGreaterThanAndIsDeletedFalse(salary);
    }


}
