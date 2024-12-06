// Giả lập dữ liệu từ backend
const universityName = "Harvard University"; // Giá trị này sẽ đến từ backend
const documents = [
    {
        id: 1,
        title: "Document Title 1",
        uploader: "John Doe",
        thumbnail: "/images/sample1.jpg"
    },
    {
        id: 2,
        title: "Document Title 2",
        uploader: "Jane Smith",
        thumbnail: "/images/sample2.jpg"
    },
    {
        id: 3,
        title: "Document Title 3",
        uploader: "Michael Brown",
        thumbnail: "/images/sample3.jpg"
    }
];

// Hiển thị dữ liệu trên giao diện
document.getElementById("university-name").innerText = universityName;

const documentsGrid = document.querySelector(".documents-grid");
documentsGrid.innerHTML = documents
    .map(
        doc => `
    <div class="document-card">
      <img src="${doc.thumbnail}" alt="Document Thumbnail">
      <h5>${doc.title}</h5>
      <p>Uploaded by ${doc.uploader}</p>
      <button onclick="viewDocument(${doc.id})">View Document</button>
    </div>
  `
    )
    .join("");

function viewDocument(id) {
    alert(`Redirecting to document ID: ${id}`);
    // Thay alert bằng logic chuyển hướng đến trang xem tài liệu
    window.location.href = `/document/${id}`;
}