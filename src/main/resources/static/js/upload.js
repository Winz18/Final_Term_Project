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
      <input type="text" id="tag-input" name="tags" placeholder="Add a tag" onkeypress="addTag(event)">
    `;
}

document.addEventListener('DOMContentLoaded', () => {
    const earningsCheckbox = document.getElementById('earnings-mode');
    const vipWarning = document.getElementById('vip-warning');
});