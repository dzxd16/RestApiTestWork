package de.telran.restapitestwork.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import de.telran.restapitestwork.model.entity.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByPhone(String phone);

    boolean existsByPhone(String phone);

    boolean existsById(Long id);

}
