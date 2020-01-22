package by.babanin.booklibrary.domain;

import by.babanin.booklibrary.domain.view.AuthorView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "author", catalog = "book_library")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = {"id", "fio"}, includeFieldNames = false)
@Getter @Setter
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
public class Author {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(AuthorView.NoBooks.class)
    private Long id;

    @Basic(optional = false)
    @Column(name = "fio", nullable = false, length = 300)
    @JsonView(AuthorView.NoBooks.class)
    private String fio;

    @Basic(optional = false)
    @Column(name = "birthday", nullable = false)
    @JsonView(AuthorView.NoBooks.class)
    private LocalDate birthday;

    @Basic
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonView(AuthorView.FullAuthor.class)
    private List<Book> books;
}
