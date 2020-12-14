package de.structuremade.ms.roleservice.api.service;

import de.structuremade.ms.roleservice.util.database.entity.Permissions;
import de.structuremade.ms.roleservice.api.json.CreateRoleJson;
import de.structuremade.ms.roleservice.util.JWTUtil;
import de.structuremade.ms.roleservice.util.database.entity.Role;
import de.structuremade.ms.roleservice.util.database.entity.School;
import de.structuremade.ms.roleservice.util.database.repo.PermissionRepository;
import de.structuremade.ms.roleservice.util.database.repo.RoleRepository;
import de.structuremade.ms.roleservice.util.database.repo.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    SchoolRepository schoolRepository;

    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    JWTUtil jwtUtil;

    @Transactional
    public int create(CreateRoleJson roleJson, String jwt) {
        try {
            Role role = new Role();
            List<Permissions> permissionList = new ArrayList<>();
            role.setName(roleJson.getName());
            System.out.println(jwt);
            System.out.println(jwtUtil.extractSpecialClaim(jwt, "schoolid"));
            School school = schoolRepository.getOne(jwtUtil.extractSpecialClaim(jwt, "schoolid"));
            System.out.println(school.getName());
            role.setSchool(school);
            for (String permission : roleJson.getPermissions()) {
                Permissions perm = permissionRepository.getOne(permission);
                if (perm != null) {
                    permissionList.add(perm);
                }
            }
            role.setPermissions(permissionList);
            roleRepository.save(role);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }
}
