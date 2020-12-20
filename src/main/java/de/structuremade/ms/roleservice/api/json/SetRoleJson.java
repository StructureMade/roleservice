package de.structuremade.ms.roleservice.api.json;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SetRoleJson {
    public String id;
    public List<String> roles;
}
