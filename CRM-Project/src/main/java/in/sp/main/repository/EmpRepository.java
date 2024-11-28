package in.sp.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.sp.main.entity.Employee;
import jakarta.transaction.Transactional;

import java.util.List;


//@Repository
@Transactional
public interface EmpRepository extends JpaRepository<Employee, Integer> {
	Employee findByEmail(String email);
	void deleteByEmail(String email);
}
