package dangtit90.top.sample.spring.graphql.repository;

import dangtit90.top.sample.spring.graphql.domain.Employee;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Integer>,
        JpaSpecificationExecutor<Employee> {
}
