package de.structuremade.ms.roleservice.api.json;

import de.structuremade.ms.roleservice.api.json.answer.arrays.PermissionsArray;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateRoleJson {
    private String id;
    private String name;
    private List<PermissionsArray> permissions;
}
