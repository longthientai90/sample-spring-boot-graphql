package dangtit90.top.sample.advanced.graphql.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentInput {
    private String name;
    private Integer organizationId;
}
