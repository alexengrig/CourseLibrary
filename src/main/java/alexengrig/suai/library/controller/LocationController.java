package alexengrig.suai.library.controller;

import alexengrig.suai.library.domain.Location;
import alexengrig.suai.library.payload.BaseSearchCondition;
import alexengrig.suai.library.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/locations")
@RequiredArgsConstructor
public class LocationController {
    private final LocationRepository repository;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Location location) {
        return Optional.ofNullable(location)
                .map(repository::save)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Location location) {
        return Optional.ofNullable(location)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Location oldLocation, @RequestBody Location location) {
        return Optional.ofNullable(oldLocation)
                .map(Location::getId)
                .map(id -> {
                    location.setId(id);
                    return location;
                })
                .map(repository::save)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Location location) {
        return Optional.ofNullable(location)
                .map(Location::getId)
                .map(id -> {
                    repository.deleteById(id);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/search")
    public ResponseEntity<?> search(@RequestBody BaseSearchCondition condition) {
        return Optional.of(condition)
                .map(cond -> repository.findAll(PageRequest.of(cond.getPage(), cond.getSize())))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }
}
