// Dữ liệu giả lập
const documents = [
    {
        id: 1,
        title: "Document Title 1",
        uploader: "John Doe",
        thumbnail: "/images/sample1.jpg",
        hashtags: ["#Science", "#Technology"]
    },
    {
        id: 2,
        title: "Document Title 2",
        uploader: "Jane Smith",
        thumbnail: "/images/sample2.jpg",
        hashtags: ["#Math", "#Physics"]
    },
    {
        id: 3,
        title: "Document Title 3",
        uploader: "Michael Brown",
        thumbnail: "/images/sample3.jpg",
        hashtags: ["#Literature"]
    }
];

function searchByHashtag(event) {
    if (event.key === "Enter") {
        const query = event.target.value.trim();
        displayDocuments(query);
    }
}

function searchByPopularHashtag(hashtag) {
    displayDocuments(hashtag);
}

function displayDocuments(query) {
    const documentsGrid = document.getElementById("documents-grid");
    const filteredDocs = documents.filter(doc =>
        doc.hashtags.includes(query)
    );

    documentsGrid.innerHTML = filteredDocs.length
        ? filteredDocs
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
            .join("")
        : `<p>No documents found for ${query}</p>`;
}

function viewDocument(id) {
    alert(`Redirecting to document ID: ${id}`);
    // Thay bằng logic chuyển hướng đến trang xem tài liệu
    window.location.href = `/document/${id}`;
}