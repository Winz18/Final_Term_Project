package hcmute.uni.final_term_project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "Document")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long docId;

    @Column(nullable = false)
    private String fileType;

    @Column(nullable = false)
    private String name;

    private Long size;
    private String path;

    @Column(name = "date_uploaded", nullable = false)
    private LocalDateTime dateUploaded;

    private int likes;
    private int downloads;
    private boolean isVIP;
    private String background;
    private String university;
    private int views;

    @Column(name = "cate_tags")
    private String cateTags;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;
}

