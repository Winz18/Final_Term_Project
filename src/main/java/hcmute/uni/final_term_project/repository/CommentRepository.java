package hcmute.uni.final_term_project.repository;

import hcmute.uni.final_term_project.entity.Comment;
import hcmute.uni.final_term_project.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByDocument(Document document);
    long countByDocument(Document document);

}
