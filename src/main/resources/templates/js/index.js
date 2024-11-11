// script.js
document.addEventListener("DOMContentLoaded", () => {
    // Top 5 tài liệu được xem nhiều nhất
    const topDocuments = [
        { title: "Toán học cơ bản", description: "Tài liệu về toán học cơ bản cho học sinh.", views: 1500, thumbnail: "thumbnail1.jpg" },
        { title: "Lịch sử Việt Nam", description: "Tìm hiểu lịch sử Việt Nam qua các thời kỳ.", views: 1200, thumbnail: "thumbnail2.jpg" },
        { title: "Tiếng Anh giao tiếp", description: "Tài liệu giúp nâng cao kỹ năng giao tiếp tiếng Anh.", views: 1100, thumbnail: "thumbnail3.jpg" },
        { title: "Văn học cổ điển", description: "Phân tích các tác phẩm văn học cổ điển nổi tiếng.", views: 1050, thumbnail: "thumbnail4.jpg" },
        { title: "Lập trình Python", description: "Giới thiệu về lập trình cơ bản với Python.", views: 1000, thumbnail: "thumbnail5.jpg" }
    ];

    const topDocumentsContainer = document.getElementById("topDocuments");
    topDocuments.forEach(doc => {
        const docCard = document.createElement("div");
        docCard.className = "col-md-4 mb-4";
        docCard.innerHTML = `
            <div class="card h-100">
                <img src="${doc.thumbnail}" class="card-img-top" alt="${doc.title}">
                <div class="card-body">
                    <h5 class="card-title">${doc.title}</h5>
                    <p class="card-text">${doc.description}</p>
                    <p class="text-muted">Lượt xem: ${doc.views}</p>
                    <a href="#" class="btn btn-primary">Xem chi tiết</a>
                </div>
            </div>
        `;
        topDocumentsContainer.appendChild(docCard);
    });

    // Số lượng người dùng trong năm (Ví dụ tĩnh)
    const userCount = 12500;  // Dữ liệu này có thể lấy từ API hoặc cơ sở dữ liệu
    document.getElementById("userCount").textContent = `Có ${userCount} người dùng đã truy cập website trong năm.`;
});
