package alexengrig.suai.library.controller;

import alexengrig.suai.library.domain.Library;
import alexengrig.suai.library.payload.BaseSearchCondition;
import alexengrig.suai.library.repository.LibraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/libraries")
@RequiredArgsConstructor
public class LibraryController {
    private final LibraryRepository repository;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Library library) {
        return Optional.ofNullable(library)
                .map(repository::save)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Library library) {
        return Optional.ofNullable(library)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Library oldLibrary, @RequestBody Library library) {
        return Optional.ofNullable(oldLibrary)
                .map(Library::getId)
                .map(id -> {
                    library.setId(id);
                    return library;
                })
                .map(repository::save)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Library library) {
        return Optional.ofNullable(library)
                .map(Library::getId)
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
