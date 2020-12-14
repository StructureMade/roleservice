package de.structuremade.ms.roleservice.util.database.repo;

import de.structuremade.ms.roleservice.util.database.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<School, String> {
}
