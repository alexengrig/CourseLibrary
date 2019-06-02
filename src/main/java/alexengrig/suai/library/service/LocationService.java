package alexengrig.suai.library.service;

import alexengrig.suai.library.domain.Location;
import alexengrig.suai.library.payload.BaseSearchCondition;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface LocationService {
    Location save(Location location);

    Optional<Location> findById(Long locationId);

    void deleteById(Long locationId);

    Page<Location> findAll(BaseSearchCondition condition);
}
