package hcmute.uni.final_term_project.repository;

import hcmute.uni.final_term_project.entity.Download;
import hcmute.uni.final_term_project.entity.User;
import hcmute.uni.final_term_project.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DownloadRepository extends JpaRepository<Download, Long> {
    List<Download> findByUser(User user);
    List<Download> findByDocument(Document document);

    long countByDoc(Document document); // Số lượt tải của một tài liệu
    long countByUser(User user); // Số lượt tải của một user

}
