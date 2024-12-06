let tags = [];

function addTag(event) {
    if (event.key === 'Enter') {
        event.preventDefault();
        const tagInput = document.getElementById('tag-input');
        const tagValue = tagInput.value.trim();
        if (tagValue && !tags.includes(tagValue)) {
            tags.push(tagValue);
            renderTags();
            tagInput.value = '';
        }
    }
}

function removeTag(tag) {
    tags = tags.filter(t => t !== tag);
    renderTags();
}

function renderTags() {
    const tagsInput = document.getElementById('tags-input');
    tagsInput.innerHTML = `
      ${tags.map(tag => `
        <div class="tag">
          ${tag} <i class="fas fa-times" onclick="removeTag('${tag}')"></i>
        </div>
      `).join('')}
      <input type="text" id="tag-input" placeholder="Add a tag" onkeypress="addTag(event)">
    `;
}

function uploadDocument(event) {
    event.preventDefault();
    alert(`Document uploaded with tags: ${tags.join(', ')}`);
    // Xử lý gửi thông tin về backend
}

document.addEventListener('DOMContentLoaded', () => {
    const isVipMember = false; // Thay giá trị từ backend
    const earningsCheckbox = document.getElementById('earnings-mode');
    const vipWarning = document.getElementById('vip-warning');

    if (isVipMember) {
        earningsCheckbox.disabled = false;
        vipWarning.style.display = 'none';
    } else {
        earningsCheckbox.disabled = true;
        vipWarning.style.display = 'block';
    }
});