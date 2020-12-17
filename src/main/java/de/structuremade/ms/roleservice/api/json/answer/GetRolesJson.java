package de.structuremade.ms.roleservice.api.json.answer;

import de.structuremade.ms.roleservice.api.json.answer.arrays.RoleArray;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class GetRolesJson {
    private List<RoleArray> roles;
}
