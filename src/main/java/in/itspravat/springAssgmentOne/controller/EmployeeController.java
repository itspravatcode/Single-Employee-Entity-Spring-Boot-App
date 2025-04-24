package in.itspravat.springAssgmentOne.controller;

import in.itspravat.springAssgmentOne.model.Employee;
import in.itspravat.springAssgmentOne.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {


    EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFound(EntityNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }


    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getEmployees(){
        return new ResponseEntity<>(service.getEmployees(), HttpStatus.OK);
    }

    @GetMapping("/employees/sort/{field}")
    public List<Employee> getSortedEmployees(
            @PathVariable String field,
            @RequestParam(defaultValue = "asc") String dir) {

        Sort.Direction direction = dir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return service.getEmployeesWithSorting(field, direction);
    }


    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable int id){
        return new ResponseEntity<>(service.getEmployee(id), HttpStatus.OK);
    }

    @GetMapping("/employees/paginated")
    public ResponseEntity<Page<Employee>> getEmployeesPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Page<Employee> employeesPage = service.getEmployeesWithPaginationAndSorting(page, size, sortField, sortDir);
        return new ResponseEntity<>(employeesPage, HttpStatus.OK);
    }


    @PostMapping("/employee")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee){
        return new ResponseEntity<>(service.addEmployee(employee), HttpStatus.CREATED);
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable int id,@RequestBody Employee employee){
        return new ResponseEntity<>(service.updateEmployee(id,employee),HttpStatus.OK);
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int id) {
        service.deleteEmployee(id);
        return new ResponseEntity<>("Employee deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/employee")
    public ResponseEntity<List<Employee>> getEmployeesByDepartment(@RequestParam String department){
        return new ResponseEntity<>( service.getEmployeesByDepartment(department), HttpStatus.OK);
    }

    @GetMapping("/employees/search")
    public ResponseEntity<List<Employee>> searchEmployeesByName(@RequestParam String name) {
        return new ResponseEntity<>(service.searchEmployeesByName(name), HttpStatus.OK);
    }

    @GetMapping("/employees/salary")
    public ResponseEntity<List<Employee>> getEmployeesWithSalaryGreaterThan(@RequestParam double amount) {
        return new ResponseEntity<>(service.getEmployeesWithSalaryGreaterThan(amount), HttpStatus.OK);
    }



}
