package dangtit90.top.sample.spring.graphql.resolver;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import dangtit90.top.sample.spring.graphql.domain.Department;
import dangtit90.top.sample.spring.graphql.domain.Employee;
import dangtit90.top.sample.spring.graphql.domain.EmployeeInput;
import dangtit90.top.sample.spring.graphql.domain.Organization;
import dangtit90.top.sample.spring.graphql.filter.EmployeeFilter;
import dangtit90.top.sample.spring.graphql.filter.FilterField;
import dangtit90.top.sample.spring.graphql.repository.DepartmentRepository;
import dangtit90.top.sample.spring.graphql.repository.EmployeeRepository;
import dangtit90.top.sample.spring.graphql.repository.OrganizationRepository;

@Controller
public class EmployeeController {

    DepartmentRepository departmentRepository;
    EmployeeRepository employeeRepository;
    OrganizationRepository organizationRepository;

    EmployeeController(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository, OrganizationRepository organizationRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
        this.organizationRepository = organizationRepository;
    }

    @QueryMapping
    public Iterable<Employee> employees() {
        return employeeRepository.findAll();
    }

    @QueryMapping
    public Employee employee(@Argument Integer id) {
        return employeeRepository.findById(id).orElseThrow();
    }

    @MutationMapping
    public Employee newEmployee(@Argument EmployeeInput employee) {
        Department department = departmentRepository.findById(employee.getDepartmentId()).get();
        Organization organization = organizationRepository.findById(employee.getOrganizationId()).get();
        return employeeRepository.save(new Employee(null, employee.getFirstName(), employee.getLastName(),
                employee.getPosition(), employee.getAge(), employee.getSalary(),
                department, organization));
    }

    @QueryMapping
    public Iterable<Employee> employeesWithFilter(@Argument EmployeeFilter filter) {
        Specification<Employee> spec = null;
        if (filter.getSalary() != null)
            spec = bySalary(filter.getSalary());
        if (filter.getAge() != null)
            spec = (spec == null ? byAge(filter.getAge()) : spec.and(byAge(filter.getAge())));
        if (filter.getPosition() != null)
            spec = (spec == null ? byPosition(filter.getPosition()) :
                    spec.and(byPosition(filter.getPosition())));
        if (spec != null)
            return employeeRepository.findAll(spec);
        else
            return employeeRepository.findAll();
    }

    private Specification<Employee> bySalary(FilterField filterField) {
        return (root, query, builder) -> filterField.generateCriteria(builder, root.get("salary"));
    }

    private Specification<Employee> byAge(FilterField filterField) {
        return (root, query, builder) -> filterField.generateCriteria(builder, root.get("age"));
    }

    private Specification<Employee> byPosition(FilterField filterField) {
        return (root, query, builder) -> filterField.generateCriteria(builder, root.get("position"));
    }
}
