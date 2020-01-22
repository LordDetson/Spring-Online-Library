package by.babanin.booklibrary.domain;

import by.babanin.booklibrary.domain.view.BookView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;

@Entity
@Table(name = "book", catalog = "book_library")
@EqualsAndHashCode(of = "id")
@ToString(of = {"id"})
@Getter @Setter
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
public class Book {
    public Book() {
    }

    public Book(Long id, String name, Integer pageCount, String isbn, Genre genre, Author author, byte[] image, Publisher publisher, Integer publishYear, String description) {
        this.id = id;
        this.name = name;
        this.pageCount = pageCount;
        this.isbn = isbn;
        this.genre = genre;
        this.author = author;
        this.image = image;
        this.publisher = publisher;
        this.publishYear = publishYear;
        this.description = description;
    }

    public Book(Long id, byte[] image) {
        this.id = id;
        this.image = image;
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(BookView.Id.class)
    private Long id;

    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 45)
    @JsonView(BookView.Name.class)
    private String name;

    @Lob
    @Column(name = "content", updatable = false)
    private byte[] content;

    @Basic(optional = false)
    @Column(name = "page_count", nullable = false)
    private Integer pageCount;

    @Basic(optional = false)
    @Column(name = "isbn", nullable = false, unique = true, length = 100)
    private String isbn;

    @ManyToOne
    @JoinColumn(name = "genre_id", referencedColumnName = "id", nullable = false)
    private Genre genre;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = false)
    private Author author;

    @Basic(optional = false)
    @Column(name = "publish_year", nullable = false)
    private Integer publishYear;

    @ManyToOne
    @JoinColumn(name = "publisher_id", referencedColumnName = "id")
    private Publisher publisher;

    @Lob
    @Column(name = "image", updatable = false)
    private byte[] image;

    @Basic
    @Column(name = "avg_rating", columnDefinition = "integer default 0")
    private Integer avgRating;

    @Basic
    @Column(name = "total_vote_count", columnDefinition = "bigint default 0")
    private Long totalVoteCount;

    @Basic
    @Column(name = "total_rating", columnDefinition = "bigint default 0")
    private Long totalRating;

    @Basic(optional = false)
    @Column(name = "view_count", columnDefinition = "bigint default 0")
    private Long viewCount;

    @Basic
    @Column(name = "descr", length = 500)
    private String description;

}
