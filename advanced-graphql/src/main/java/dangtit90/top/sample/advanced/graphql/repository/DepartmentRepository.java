package dangtit90.top.sample.advanced.graphql.repository;

import dangtit90.top.sample.advanced.graphql.domain.Department;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentRepository extends CrudRepository<Department, Integer>,
        JpaSpecificationExecutor<Department> {

}
