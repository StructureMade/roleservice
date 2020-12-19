package de.structuremade.ms.roleservice.api.json.answer;

import de.structuremade.ms.roleservice.api.json.answer.arrays.PermissionsArray;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetAllPermissions {
    private List<PermissionsArray> permissions;
}
