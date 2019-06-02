package alexengrig.suai.library.controller;

import alexengrig.suai.library.domain.Shelf;
import alexengrig.suai.library.payload.BaseSearchCondition;
import alexengrig.suai.library.repository.ShelfRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/shelves")
@RequiredArgsConstructor
public class ShelfController {
    private final ShelfRepository repository;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Shelf shelf) {
        return Optional.ofNullable(shelf)
                .map(repository::save)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Shelf shelf) {
        return Optional.ofNullable(shelf)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Shelf oldShelf, @RequestBody Shelf shelf) {
        return Optional.ofNullable(oldShelf)
                .map(Shelf::getId)
                .map(id -> {
                    shelf.setId(id);
                    return shelf;
                })
                .map(repository::save)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Shelf shelf) {
        return Optional.ofNullable(shelf)
                .map(Shelf::getId)
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
