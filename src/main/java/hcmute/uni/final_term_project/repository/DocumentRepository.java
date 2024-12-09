package hcmute.uni.final_term_project.repository;

import hcmute.uni.final_term_project.entity.Document;
import hcmute.uni.final_term_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByUniversity(String university);
    List<Document> findByCateTagsContaining(String tag); // Tìm tài liệu theo hashtag
    List<Document> findByOwner(User owner); // Lấy tài liệu của user
    List<Document> findByIsVIP(boolean isVIP); // Lấy tài liệu chỉ dành cho VIP
    List<Document> findByNameContaining(String keyword); // Tìm kiếm tài liệu theo tên
    List<Document> findByOrderByDateUploadedDesc(); // Tài liệu mới nhất
    List<Document> findByOrderByViewsDesc(); // Tài liệu nhiều lượt xem nhất
  
    List<Document> findByOrderByDownloadsDesc(); // Tài liệu được tải xuống nhiều nhất
    @Query("SELECT d.cateTags FROM Document d")
    List<String> findAllTags();
    @Query("SELECT COUNT(d) FROM Document d WHERE DATE(d.dateUploaded) = CURRENT_DATE")
    long countDocumentsUploadedToday();
  
    List<Document> findTop3ByOrderByDownloadsDesc(); // Tài liệu được tải xuống nhiều nhất
    List<Document> findTop3ByOrderByViewsDesc();

    @Query("SELECT d.cateTags FROM Document d GROUP BY d.cateTags ORDER BY COUNT(d) DESC")
    List<String> findPopularTags();

    @Query("SELECT d.university FROM Document d GROUP BY d.university ORDER BY COUNT(d) DESC")
    List<String> findPopularUniversities();
}
