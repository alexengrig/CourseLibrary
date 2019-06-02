package alexengrig.suai.library.controller;

import alexengrig.suai.library.domain.Shelving;
import alexengrig.suai.library.payload.BaseSearchCondition;
import alexengrig.suai.library.repository.ShelvingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/shelving")
@RequiredArgsConstructor
public class ShelvingController {
    private final ShelvingRepository repository;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Shelving shelving) {
        return Optional.ofNullable(shelving)
                .map(repository::save)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Shelving shelving) {
        return Optional.ofNullable(shelving)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Shelving oldShelving, @RequestBody Shelving shelving) {
        return Optional.ofNullable(oldShelving)
                .map(Shelving::getId)
                .map(id -> {
                    shelving.setId(id);
                    return shelving;
                })
                .map(repository::save)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Shelving shelving) {
        return Optional.ofNullable(shelving)
                .map(Shelving::getId)
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
