function openAddForm() {
    document.getElementById('userFormTitle').innerText = 'Thêm người dùng';
    document.getElementById('userForm').reset();
    new bootstrap.Modal(document.getElementById('userFormModal')).show();
}

function openEditForm(userId) {
    // Đây là ví dụ. Tùy vào thực tế, bạn cần lấy dữ liệu từ backend.
    const userData = {
        '1': { name: 'John Doe', email: 'john@example.com', phone: '123-456-7890', dob: '1990-05-15' },
        '2': { name: 'Jane Smith', email: 'jane@example.com', phone: '987-654-3210', dob: '1985-07-20' },
    };

    const user = userData[userId];
    document.getElementById('userFormTitle').innerText = 'Chỉnh sửa người dùng';
    document.getElementById('userName').value = user.name;
    document.getElementById('userEmail').value = user.email;
    document.getElementById('userPhone').value = user.phone;
    document.getElementById('userDob').value = user.dob;
    new bootstrap.Modal(document.getElementById('userFormModal')).show();
}
