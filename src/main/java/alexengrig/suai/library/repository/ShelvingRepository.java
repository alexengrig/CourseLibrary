package alexengrig.suai.library.repository;

import alexengrig.suai.library.domain.Shelving;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShelvingRepository extends JpaRepository<Shelving, Long> {
}
