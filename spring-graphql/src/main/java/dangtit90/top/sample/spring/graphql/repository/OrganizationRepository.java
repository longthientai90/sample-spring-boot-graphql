package dangtit90.top.sample.spring.graphql.repository;

import dangtit90.top.sample.spring.graphql.domain.Organization;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface OrganizationRepository extends CrudRepository<Organization, Integer>,
        JpaSpecificationExecutor<Organization> {
}
