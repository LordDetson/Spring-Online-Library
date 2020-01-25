package by.babanin.booklibrary.dao.repository;

import by.babanin.booklibrary.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findByNameContainingIgnoreCaseOrAuthorFioContainingIgnoreCase(String name, String fio, Pageable pageable);

    List<Book> findByNameContainingIgnoreCaseOrAuthorFioContainingIgnoreCaseOrderByName(String name, String fio);

    @Query("select new Book(b.id, b.name, b.pageCount, b.isbn, b.genre, b.author, b.image, b.publisher, b.publishYear, b.avgRating, b.totalVoteCount, b.totalRating, b.viewCount, b.description) from Book b")
    Page<Book> findAllWithoutContent(Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("update Book b set b.content=:content where b.id=:id")
    void updateContent(@Param("content") byte[] content, @Param("id") Long id);

    @Modifying(clearAutomatically = true)
    @Query("update Book b set b.image=:image where b.id=:id")
    void updateImage(@Param("image") byte[] image, @Param("id") Long id);

    @Query("select new Book(b.id, b.image) from Book b")
    Page<Book> findTopBooks(Pageable pageable);

    @Query("select new Book(b.id, b.name, b.pageCount, b.isbn, b.genre, b.author, b.image, b.publisher, b.publishYear, b.avgRating, b.totalVoteCount, b.totalRating, b.viewCount, b.description) from Book b where b.genre.id=:genreId")
    Page<Book> findByGenre(@Param("genreId") Long genreId, Pageable pageable);

    @Query("select b.content from Book b where b.id=:id")
    byte[] getContent(@Param("id") Long id);

    @Modifying
    @Query("update Book b set b.viewCount=:viewCount where b.id=:id")
    void updateViewCount(@Param("viewCount") long viewCount, @Param("id") Long id);

    @Modifying
    @Query("update Book b set b.totalRating=:totalRating, b.totalVoteCount=:totalVoteCount, b.avgRating=:avgRating where b.id=:id")
    void updateRating(@Param("totalRating") long totalRating, @Param("totalVoteCount") long totalVoteCount, @Param("avgRating") int avgRating, @Param("id") Long id);
}
