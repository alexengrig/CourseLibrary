package alexengrig.suai.library.service;

import alexengrig.suai.library.domain.Book;
import alexengrig.suai.library.payload.BaseSearchCondition;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface BookService {
    Book save(Book book);

    Optional<Book> findById(Long bookId);

    void deleteById(Long bookId);

    Page<Book> findAll(BaseSearchCondition condition);
}
