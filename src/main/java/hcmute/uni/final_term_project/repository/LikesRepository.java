package hcmute.uni.final_term_project.repository;

import hcmute.uni.final_term_project.entity.Likes;
import hcmute.uni.final_term_project.entity.Document;
import hcmute.uni.final_term_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {
    List<Likes> findByUser(User user);
    List<Likes> findByDocument(Document document);

    @Query("SELECT COUNT(l) FROM Likes l WHERE l.document = ?1")
    int countByDocument(Document document); // Số lượng like của một tài liệu

    boolean existsByUserAndDocument(User user, Document document); // Kiểm tra xem user đã like tài liệu chưa

}
