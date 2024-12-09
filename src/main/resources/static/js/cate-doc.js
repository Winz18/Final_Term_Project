// Dữ liệu giả lập
const documents = [

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
          <img src="'/uploads/' +${doc.background}" alt="Document Thumbnail">
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