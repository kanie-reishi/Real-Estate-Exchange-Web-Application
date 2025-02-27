$(document).ready(function () {
    // Xử lý sự kiện khi click vào nút đăng ký
    $("#btn-register").click(function () {
        onBtnRegisterClick();
    });
});

// Hàm xử lý khi click vào nút đăng ký
function onBtnRegisterClick() {
    var user = collectRegisterData();
    if (validateUser(user)) {
        // Gửi dữ liệu đăng ký lên server
        $.ajax({
            url: "http://localhost:8080/signup",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(user),
            success: function (response) {
                if (response.statusCode === 200) {
                    alert("Đăng ký thành công");
                    // Chuyển hướng đến trang đăng nhập
                    window.location.href = "/login";
                } else {
                    alert(response.message);
                }
            },
            error: function (response) {
                alert("Đăng ký thất bại");
                console.log(response);
            }
        });
    }
}

// Hàm thu thập dữ liệu đăng ký
function collectRegisterData() {
    var user = {
        username: $("#inp-register-username").val(),
        email: $("#inp-register-email").val(),
        password: $("#inp-register-password").val(),
    };
    return user;
}

// Hàm kiểm tra tính hợp lệ của thông tin đăng ký
function validateUser(user) {
    var isValid = true;

    if (!user.username || user.username.length < 3 || user.username.length > 20) {
        isValid = false;
        alert("Tên đăng nhập phải chứa từ 3 đến 20 ký tự");
    }

    var emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
    if (!user.email || !emailRegex.test(user.email)) {
        isValid = false;
        alert("Email không hợp lệ");
    }

    var repeatPassword = $("#inp-register-repeat-password").val();
    if (user.password !== repeatPassword) {
        isValid = false;
        alert("Mật khẩu không khớp");
    }

    if (!user.password || user.password.length < 5 || user.password.length > 40) {
        isValid = false;
        alert("Mật khẩu phải chứa từ 5 đến 40 ký tự");
    }

    return isValid;
}