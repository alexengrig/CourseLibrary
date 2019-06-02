package alexengrig.suai.library.controller;

import alexengrig.suai.library.domain.Book;
import alexengrig.suai.library.payload.BaseSearchCondition;
import alexengrig.suai.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService service;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Book book) {
        return Optional.ofNullable(book)
                .map(service::save)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long bookId) {
        return Optional.ofNullable(bookId)
                .map(service::findById)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long bookId, @RequestBody Book book) {
        return Optional.ofNullable(book)
                .map(b -> {
                    b.setId(bookId);
                    return b;
                })
                .map(service::save)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long bookId) {
        return Optional.ofNullable(bookId)
                .map(id -> {
                    service.deleteById(id);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/search")
    public ResponseEntity<?> search(@RequestBody BaseSearchCondition condition) {
        return Optional.of(condition)
                .map(service::findAll)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }
}
