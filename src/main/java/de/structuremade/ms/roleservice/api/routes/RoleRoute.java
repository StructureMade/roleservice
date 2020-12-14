package de.structuremade.ms.roleservice.api.routes;

import de.structuremade.ms.roleservice.api.json.CreateRoleJson;
import de.structuremade.ms.roleservice.api.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("service/role")
public class RoleRoute {

    @Autowired
    RoleService roleService;

    @PostMapping(value = "/create")
    public void createRole(@RequestBody @Valid CreateRoleJson roleJson, HttpServletResponse response, HttpServletRequest request) {
        switch (roleService.create(roleJson, request.getHeader("Authorization").substring(7))) {
            case 0:
                response.setStatus(HttpStatus.CREATED.value());
                break;
            case 1:
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                break;
        }
    }

}
