package alexengrig.suai.library.service;

import alexengrig.suai.library.domain.Location;
import alexengrig.suai.library.payload.BaseSearchCondition;
import alexengrig.suai.library.repository.LibraryRepository;
import alexengrig.suai.library.repository.LocationRepository;
import alexengrig.suai.library.repository.ShelfRepository;
import alexengrig.suai.library.repository.ShelvingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SimpleLocationService implements LocationService {
    private final LocationRepository repository;
    private final ShelfRepository shelfRepository;
    private final ShelvingRepository shelvingRepository;
    private final LibraryRepository libraryRepository;

    @Override
    public Location save(Location location) {
        Optional.of(location)
                .map(Location::getShelf)
                .map(shelfRepository::save)
                .ifPresent(location::setShelf);
        Optional.of(location)
                .map(Location::getShelving)
                .map(shelvingRepository::save)
                .ifPresent(location::setShelving);
        Optional.of(location)
                .map(Location::getLibrary)
                .map(libraryRepository::save)
                .ifPresent(location::setLibrary);
        return repository.save(location);
    }

    @Override
    public Optional<Location> findById(Long locationId) {
        return repository.findById(locationId);
    }

    @Override
    public void deleteById(Long locationId) {
        repository.deleteById(locationId);
    }

    @Override
    public Page<Location> findAll(BaseSearchCondition condition) {
        return repository.findAll(PageRequest.of(condition.getPage(), condition.getSize()));
    }
}
