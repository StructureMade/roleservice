package de.structuremade.ms.roleservice.api.service;

import de.structuremade.ms.roleservice.api.json.answer.GetRolesJson;
import de.structuremade.ms.roleservice.api.json.answer.arrays.PermissionsArray;
import de.structuremade.ms.roleservice.api.json.answer.arrays.RoleArray;
import de.structuremade.ms.roleservice.util.database.entity.Permissions;
import de.structuremade.ms.roleservice.api.json.CreateRoleJson;
import de.structuremade.ms.roleservice.util.JWTUtil;
import de.structuremade.ms.roleservice.util.database.entity.Role;
import de.structuremade.ms.roleservice.util.database.entity.School;
import de.structuremade.ms.roleservice.util.database.entity.User;
import de.structuremade.ms.roleservice.util.database.repo.PermissionRepository;
import de.structuremade.ms.roleservice.util.database.repo.RoleRepository;
import de.structuremade.ms.roleservice.util.database.repo.SchoolRepository;
import de.structuremade.ms.roleservice.util.database.repo.UserRepository;
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
    UserRepository userRepository;

    @Autowired
    JWTUtil jwtUtil;

    @Transactional
    public int create(CreateRoleJson roleJson, String jwt) {
        try {
            Role role = new Role();
            List<Permissions> permissionList = new ArrayList<>();
            role.setName(roleJson.getName());
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

    @Transactional
    public int updateRole(String roleid, String name, List<PermissionsArray> permissionsUpdate, String scholid) {
        try {
            List<Role> schoolRoles = schoolRepository.getOne(scholid).getRoles();
            Role role = roleRepository.getOne(roleid);
            List<Permissions> perms = role.getPermissions();
            boolean existsPerm = false;
            boolean roleExists = false;
            for (Role schoolrole: schoolRoles) {
                if (schoolrole.getId().equals(role.getId())){
                    roleExists = true;
                }
            }
            if (role.getName().length() == 0 || !roleExists) {
                return 0;
            }
            role.setName(name);
            if (permissionsUpdate.size() > 0) {
                for (PermissionsArray permUpdate : permissionsUpdate) {
                    for (Permissions perm : perms) {
                        if (permUpdate.getId().equals(perm.getId())) {
                            existsPerm = true;
                        }
                    }
                    Permissions permission = permissionRepository.getOne(permUpdate.getId());
                    if (!existsPerm) {
                        perms.add(permission);
                    } else {
                        perms.remove(permission);
                    }
                }
                role.setPermissions(perms);
            }
            roleRepository.save(role);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 2;
        }
    }

    @Transactional
    public Object getAllRoles(String schoolid) {
        GetRolesJson getRolesJson = new GetRolesJson();
        School school = schoolRepository.getOne(schoolid);
        List<RoleArray> roles = new ArrayList<>();
        try {
            for (Role schoolRole : school.getRoles()) {
                RoleArray role = new RoleArray();
                role.setId(schoolRole.getId());
                role.setName(schoolRole.getName());
                List<PermissionsArray> perms = new ArrayList<>();
                for (Permissions permissions : schoolRole.getPermissions()) {
                    PermissionsArray perm = new PermissionsArray();
                    perm.setId(permissions.getId());
                    perm.setName(perm.getName());
                    perms.add(perm);
                }
                role.setPermissions(perms);
                roles.add(role);
            }
            getRolesJson.setRoles(roles);
            return getRolesJson;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

