package alexengrig.suai.library.controller;

import alexengrig.suai.library.domain.Book;
import alexengrig.suai.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {
    private final BookRepository repository;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Book book) {
        return Optional.ofNullable(book)
                .map(repository::save)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Book book) {
        return Optional.ofNullable(book)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Book oldBook, @RequestBody Book book) {
        return Optional.ofNullable(oldBook)
                .map(Book::getId)
                .map(id -> {
                    book.setId(id);
                    return book;
                })
                .map(repository::save)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Book book) {
        return Optional.ofNullable(book)
                .map(Book::getId)
                .map(id -> {
                    repository.deleteById(id);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
