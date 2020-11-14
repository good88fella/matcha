package ru.school21.matcha.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.school21.matcha.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
