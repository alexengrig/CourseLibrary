package alexengrig.suai.library.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String note;

    @OneToOne(fetch = FetchType.EAGER)
    private Shelf shelf;

    @OneToOne(fetch = FetchType.EAGER)
    private Shelving shelving;
}
