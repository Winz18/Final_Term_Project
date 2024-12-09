let selectedPaymentMethod = null;

function selectPaymentMethod(method) {
    selectedPaymentMethod = method;

    // Bật hoặc tắt thông tin thẻ tín dụng
    document.getElementById("credit-card-info").style.display =
        method === "credit-card" ? "block" : "none";

    // Thay đổi giao diện cho phương thức thanh toán
    document.querySelectorAll(".payment-method").forEach((element) => {
        element.classList.remove("active");
    });
    document.querySelectorAll(".payment-method").forEach((element) => {
        if (element.textContent.includes(method)) {
            element.classList.add("active");
        }
    });
}

function processPayment(event) {
    event.preventDefault();

    if (!selectedPaymentMethod) {
        alert("Please select a payment method.");
        return;
    }

    const paymentStatus = document.getElementById("payment-status");

    // Xử lý thanh toán (giả lập)
    setTimeout(() => {
        const isSuccess = true; // Giả lập trạng thái thành công hoặc thất bại
        if (isSuccess) {
            paymentStatus.textContent = "Payment Successful!";
            paymentStatus.className = "status success";
            paymentStatus.style.display = "block";
            setTimeout(() => {
                window.location.href = "/user/home"; // Chuyển về User Home sau khi thanh toán thành công
            }, 1000);
        } else {
            paymentStatus.textContent = "Payment Failed. Please try again.";
            paymentStatus.className = "status failure";
            paymentStatus.style.display = "block";
        }
    }, 1500);
}