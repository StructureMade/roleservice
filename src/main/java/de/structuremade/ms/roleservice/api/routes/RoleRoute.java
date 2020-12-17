package de.structuremade.ms.roleservice.api.routes;

import de.structuremade.ms.roleservice.api.json.CreateRoleJson;
import de.structuremade.ms.roleservice.api.json.answer.GetRolesJson;
import de.structuremade.ms.roleservice.api.service.RoleService;
import de.structuremade.ms.roleservice.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("service/role")
public class RoleRoute {

    @Autowired
    RoleService roleService;

    @Autowired
    JWTUtil jwtUtil;

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

    @GetMapping("/getall")
    public Object getAllRoles(HttpServletResponse response, HttpServletRequest request){
        Object rolesJson = roleService.getAllRoles(jwtUtil.extractSpecialClaim(request.getHeader("Authorization").substring(7), "schoolid"));
        if (rolesJson == null){
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return rolesJson;
        }
        else {
            response.setStatus(HttpStatus.OK.value());
            return rolesJson;
        }
    }
}
