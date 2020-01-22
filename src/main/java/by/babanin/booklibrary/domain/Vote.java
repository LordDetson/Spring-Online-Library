package by.babanin.booklibrary.domain;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;

@Entity
@Table(name = "vote", catalog = "book_library")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = {"id", "value"}, includeFieldNames = false)
@Getter @Setter
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
public class Vote {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "value", columnDefinition = "integer default 0")
    private Integer value;

    @Basic(optional = false)
    @Column(name = "book_id", nullable = false)
    private Long bookId;

    @Basic(optional = false)
    @Column(name = "username", nullable = false, length = 100)
    private String username;
}
