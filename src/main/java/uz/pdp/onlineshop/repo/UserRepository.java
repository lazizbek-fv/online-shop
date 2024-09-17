package uz.pdp.onlineshop.repo;

import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.onlineshop.entity.User;
@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
 Optional<User> findByEmail(String email);
}