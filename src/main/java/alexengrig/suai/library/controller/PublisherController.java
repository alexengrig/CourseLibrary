package alexengrig.suai.library.controller;

import alexengrig.suai.library.domain.Publisher;
import alexengrig.suai.library.payload.BaseSearchCondition;
import alexengrig.suai.library.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/publishers")
@RequiredArgsConstructor
public class PublisherController {
    private final PublisherRepository repository;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Publisher publisher) {
        return Optional.ofNullable(publisher)
                .map(repository::save)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Publisher publisher) {
        return Optional.ofNullable(publisher)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Publisher oldPublisher, @RequestBody Publisher publisher) {
        return Optional.ofNullable(oldPublisher)
                .map(Publisher::getId)
                .map(id -> {
                    publisher.setId(id);
                    return publisher;
                })
                .map(repository::save)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Publisher publisher) {
        return Optional.ofNullable(publisher)
                .map(Publisher::getId)
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
