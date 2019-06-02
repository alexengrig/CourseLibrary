package alexengrig.suai.library.controller;

import alexengrig.suai.library.domain.Author;
import alexengrig.suai.library.payload.BaseSearchCondition;
import alexengrig.suai.library.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/authors")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorRepository repository;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Author author) {
        return Optional.ofNullable(author)
                .map(repository::save)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Author author) {
        return Optional.ofNullable(author)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Author oldAuthor, @RequestBody Author author) {
        return Optional.ofNullable(oldAuthor)
                .map(Author::getId)
                .map(id -> {
                    author.setId(id);
                    return author;
                })
                .map(repository::save)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Author author) {
        return Optional.ofNullable(author)
                .map(Author::getId)
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
