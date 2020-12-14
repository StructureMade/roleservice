package de.structuremade.ms.roleservice.util.database.repo;

import de.structuremade.ms.roleservice.util.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
