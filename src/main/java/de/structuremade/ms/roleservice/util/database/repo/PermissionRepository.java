package de.structuremade.ms.roleservice.util.database.repo;

import de.structuremade.ms.roleservice.util.database.entity.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permissions, String> {
}
