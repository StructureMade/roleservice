package de.structuremade.ms.roleservice.api.json.answer.arrays;

import de.structuremade.ms.roleservice.util.database.entity.Permissions;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PermissionsArray {

    public PermissionsArray(Permissions permissionsArray) {
        this.id = permissionsArray.getId();
        this.name = permissionsArray.getName();
    }

    private String id;
    private String name;
}
