package de.structuremade.ms.roleservice.api.json.answer.arrays;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoleArray {
    private String id;
    private String name;
    private List<PermissionsArray> permissions;
}
