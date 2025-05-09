package in.itspravat.springAssgmentOne.repository;

import in.itspravat.springAssgmentOne.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Integer> {
     List<Employee> findByDepartmentIgnoreCaseAndIsDeletedFalse(String department);
     Page<Employee> findAllByIsDeletedFalse(Pageable pageable);
     List<Employee> findByNameContainingIgnoreCaseAndIsDeletedFalse(String name);
     List<Employee> findBySalaryGreaterThanAndIsDeletedFalse(double salary);

}
