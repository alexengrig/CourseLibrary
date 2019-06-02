package alexengrig.suai.library.service;

import alexengrig.suai.library.domain.Book;
import alexengrig.suai.library.payload.BaseSearchCondition;
import alexengrig.suai.library.repository.AuthorRepository;
import alexengrig.suai.library.repository.BookRepository;
import alexengrig.suai.library.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SimpleBookService implements BookService {
    private final BookRepository repository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;
    private final LocationService locationService;

    @Override
    public Book save(Book book) {
        Optional.of(book)
                .map(Book::getAuthors)
                .map(authorRepository::saveAll)
                .map(HashSet::new)
                .ifPresent(book::setAuthors);
        Optional.of(book)
                .map(Book::getPublisher)
                .map(publisherRepository::save)
                .ifPresent(book::setPublisher);
        Optional.of(book)
                .map(Book::getLocation)
                .map(locationService::save)
                .ifPresent(book::setLocation);
        return repository.save(book);
    }

    @Override
    public Optional<Book> findById(Long bookId) {
        return repository.findById(bookId);
    }

    @Override
    public void deleteById(Long bookId) {
        repository.deleteById(bookId);
    }

    @Override
    public Page<Book> findAll(BaseSearchCondition condition) {
        return repository.findAll(PageRequest.of(condition.getPage(), condition.getSize()));
    }
}
