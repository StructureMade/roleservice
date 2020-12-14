package de.structuremade.ms.roleservice.api.json;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;

@Getter
@Setter
public class CreateRoleJson {
    @NotBlank(message = "Rolename is required")
    private String name;
    private ArrayList<String> permissions;
}
