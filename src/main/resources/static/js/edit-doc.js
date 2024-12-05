let tags = ['#Science', '#Technology'];

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
        <span class="tag">${tag} <i class="fas fa-times" onclick="removeTag('${tag}')"></i></span>
      `).join('')}
      <input type="text" id="tag-input" placeholder="Add a tag" onkeypress="addTag(event)">
    `;
}

function saveChanges(event) {
    event.preventDefault();
    const documentTitle = document.getElementById('document-title').value;
    const authorName = document.getElementById('author-name').value;
    const university = document.getElementById('university').value;

    alert(`
      Changes Saved!
      Title: ${documentTitle}
      Author: ${authorName}
      University: ${university}
      Tags: ${tags.join(', ')}
    `);

    // Gửi dữ liệu lên backend tại đây
}

document.addEventListener('DOMContentLoaded', renderTags);