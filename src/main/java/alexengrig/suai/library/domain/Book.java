package alexengrig.suai.library.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.Year;
import java.util.Set;

@Data
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String annotation;
    private Year year;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Author> authors;

    @OneToOne(fetch = FetchType.EAGER)
    private Publisher publisher;
}
