package de.structuremade.ms.roleservice.api.service;

import de.structuremade.ms.roleservice.api.json.CreateRoleJson;
import de.structuremade.ms.roleservice.api.json.answer.GetAllPermissions;
import de.structuremade.ms.roleservice.api.json.answer.GetRolesJson;
import de.structuremade.ms.roleservice.api.json.answer.arrays.PermissionsArray;
import de.structuremade.ms.roleservice.api.json.answer.arrays.RoleArray;
import de.structuremade.ms.roleservice.util.JWTUtil;
import de.structuremade.ms.roleservice.util.database.entity.Permissions;
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
            for (Role schoolrole : schoolRoles) {
                if (schoolrole.getId().equals(role.getId())) {
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
        List<RoleArray> roles = new ArrayList<>();
        try {
            School school = schoolRepository.getOne(schoolid);
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

    public GetAllPermissions getAllPermissions() {
        try {
            GetAllPermissions getAllPermissions = new GetAllPermissions();
            List<Permissions> permissions = permissionRepository.findAllByMasterPerms(false);
            List<PermissionsArray> permissionsArrays = new ArrayList<>();
            for (Permissions perms : permissions) {
                PermissionsArray permissionsArray = new PermissionsArray(perms);
                permissionsArrays.add(permissionsArray);
            }
            getAllPermissions.setPermissions(permissionsArrays);
            return getAllPermissions;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public int setRole(String userid, String schoolid, List<String> roles) {
        try {
            User user = userRepository.getOne(userid);
            try {
                if (user.getId().length() > 0) {
                    return 0;
                }
            } catch (Exception e) {
                return 0;
            }
            if (!user.getSchools().get(0).getId().equals(schoolid)) return 3;

            List<Role> userRoles = user.getRoles();
            School school = schoolRepository.getOne(schoolid);
            List<Role> schoolRoles = school.getRoles();
            for (String role : roles) {
                for (Role schoolRole : schoolRoles) {
                    Role setRole = roleRepository.getOne(role);
                    if (schoolRole.getId().equals(setRole.getId())) {
                        userRoles.add(setRole);
                    }
                }
            }
            user.setRoles(userRoles);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 2;
        }
    }
}

