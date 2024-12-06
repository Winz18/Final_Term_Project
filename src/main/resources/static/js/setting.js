function savePassword() {
    alert('Password updated successfully!');
}

function savePreferences() {
    const isChecked = document.getElementById('email-preferences').checked;
    alert(`Promotional emails: ${isChecked ? 'Enabled' : 'Disabled'}`);
}

function saveDisplayMode() {
    const mode = document.getElementById('display-mode').value;
    alert(`Display mode set to: ${mode}`);
}

function saveLanguage() {
    const language = document.getElementById('language').value;
    alert(`Language set to: ${language}`);
}

function deleteAccount() {
    const confirmDelete = confirm('Are you sure you want to delete your account? This action cannot be undone.');
    if (confirmDelete) {
        alert('Account deleted.');
    }
}
