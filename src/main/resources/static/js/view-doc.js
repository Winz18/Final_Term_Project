function likeDocument() {
    alert('You liked this document!');
    // Gửi yêu cầu lên backend để tăng lượt thích
}

function downloadDocument() {
    alert('Document downloaded!');
    // Gửi yêu cầu download tài liệu
}

function postComment() {
    const commentBox = document.querySelector('.comment-box textarea');
    const comment = commentBox.value.trim();
    if (comment) {
        // Thêm bình luận vào danh sách (giả lập)
        const commentList = document.querySelector('.comment-list');
        const newComment = document.createElement('li');
        newComment.innerHTML = `
        <p class="author">You</p>
        <p>${comment}</p>
      `;
        commentList.prepend(newComment);
        commentBox.value = '';
        alert('Comment posted!');
    } else {
        alert('Please write a comment before posting.');
    }
}