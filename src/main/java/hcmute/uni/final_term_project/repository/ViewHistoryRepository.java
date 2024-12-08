package hcmute.uni.final_term_project.repository;

import hcmute.uni.final_term_project.entity.Document;
import hcmute.uni.final_term_project.entity.User;
import hcmute.uni.final_term_project.entity.ViewHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

public interface ViewHistoryRepository extends JpaRepository<ViewHistory, Long> {
    @Query("SELECT vh FROM ViewHistory vh " +
            "WHERE vh.user = :user " +
            "AND vh.viewedAt IN (SELECT MAX(vhSub.viewedAt) FROM ViewHistory vhSub WHERE vhSub.user = :user GROUP BY vhSub.document) " +
            "ORDER BY vh.viewedAt DESC")
    List<ViewHistory> findDistinctDocumentsByUserOrderByViewedAtDesc(@Param("user") User user);

    Optional<ViewHistory> findByUserAndDocument(User currentUser, Document doc);
}