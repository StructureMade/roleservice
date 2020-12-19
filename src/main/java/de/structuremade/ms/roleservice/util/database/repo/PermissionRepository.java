package de.structuremade.ms.roleservice.util.database.repo;

import de.structuremade.ms.roleservice.util.database.entity.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PermissionRepository extends JpaRepository<Permissions, String> {
    List<Permissions> findAllByMasterPerms(boolean master);
}
