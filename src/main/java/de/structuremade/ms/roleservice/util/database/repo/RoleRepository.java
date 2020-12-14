package de.structuremade.ms.roleservice.util.database.repo;

import de.structuremade.ms.roleservice.util.database.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}
