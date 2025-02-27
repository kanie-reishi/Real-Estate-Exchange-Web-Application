/*** REGION 1 - Global variables - Vùng khai báo biến, hằng số, tham số TOÀN CỤC */

/*** REGION 2 - Vùng gán / thực thi hàm xử lý sự kiện cho các elements */
$(document).ready(function () {
    if (sessionStorage.getItem("isLoggedIn")) {
        var user = JSON.parse(sessionStorage.getItem("user"));
        $('#btn-navbar-login').text('Hello, ' + user.username);
        $('#btn-navbar-login').removeAttr('data-bs-toggle data-bs-target'); // Disable login modal when logged in
    }
    // Xử lý sự kiện khi click vào nút hiển thị / ẩn mật khẩu
    $(".password-visibility").click(function () {
        togglePasswordVisibility(this);
    });
    // Xử lý sự kiện khi click vào nút đăng nhập
    $("#btn-login").click(function () {
        onBtnLoginClick();
    })
    // Xử lý sự kiện khi click vào nút đăng xuất
    $("#btn-logout").click(function() {
        if (confirm("Bạn có chắc chắn muốn đăng xuất?")) {
            let token = sessionStorage.getItem("jwtToken");

            if (token) {
                $.ajax({
                    url: "http://localhost:8080/auth/logout", // Change this to your backend logout endpoint
                    type: "POST",
                    headers: { "Authorization": "Bearer " + token },
                    success: function (response) {
                        alert("Bạn đã đăng xuất thành công!");
                        location.reload(); // Refresh to update UI
                    },
                    error: function (xhr, status, error) {
                        alert("Đăng xuất thất bại!");
                    },
                    complete: function () {
                        sessionStorage.removeItem("jwtToken"); // Remove JWT
                        sessionStorage.removeItem("user"); // Remove user info
                        sessionStorage.removeItem("isLoggedIn"); // Remove login state
                        alert("Bạn đã đăng xuất thành công!");
                        location.reload(); // Refresh to update UI
                    }
                });
            } else {
                sessionStorage.clear();
                alert("Bạn đã đăng xuất thành công!");
                location.reload();
            }
        }
    })
});
/*** REGION 3 - Event handlers - Vùng khai báo các hàm xử lý sự kiện */
// Hàm xử lý khi click vào nút đăng nhập
function onBtnLoginClick(){
    var userCreditiant = {};
    userCreditiant = collectLoginData();
    if(validiateUser(userCreditiant)){
        // Gửi dữ liệu đăng nhập lên server
        $.ajax({
            url: "http://localhost:8080/auth/user",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(userCreditiant),
            success: function (response) {
                if(response.statusCode === 200){
                    alert("Đăng nhập thành công");
                    // Lưu thông tin user vào sessionStorage
                    sessionStorage.setItem("user", JSON.stringify(response.data));
                    // Lưu trạng thái đăng nhập
                    sessionStorage.setItem("isLoggedIn", true);
                    var user = JSON.parse(sessionStorage.getItem("user"));
                    $('#btn-navbar-login').text('Hello, ' + user.username);
                    // Tải lại trang
                    location.reload();
                } else {
                    alert("Đăng nhập thất bại");
                }
            },
            error: function (response) {
                alert("Đăng nhập thất bại");
            }
        });
    }
}
/*** REGION 4 - Common funtions - Vùng khai báo hàm dùng chung trong toàn bộ chương trình*/
// Hàm thay đổi trạng thái hiển thị / ẩn mật khẩu
function togglePasswordVisibility(passwordVisibility) {
    var passwordInput = $(passwordVisibility).siblings("input");
    var passwordInputType = passwordInput.attr("type");
    if (passwordInputType === "password") {
        passwordInput.attr("type", "text");
        $(passwordVisibility).removeClass("password-hide").addClass("password-show");
    } else {
        passwordInput.attr("type", "password");
        $(passwordVisibility).removeClass("password-show").addClass("password-hide");
    }
}
// Hàm thu thập dữ liệu đăng nhập
function collectLoginData(){
    var user = {
        username: undefined,
        email: undefined,
        phoneNumber: undefined,
        password: undefined
    };
    var inputUsername = $("#inp-login-username").val();
    // check if inputUsername is username, email or phone number
    if (inputUsername.includes("@")) {
        user.email = inputUsername;
    } else {
        user.username = inputUsername;
    }
    user.password = $("#inp-login-password").val();
    return user;
}
// Hàm kiểm tra tính hợp lệ của thông tin đăng nhập
function validiateUser(user){
    var isValid = true;
    if(user.username === undefined && user.email === undefined && user.phoneNumber === undefined){
        isValid = false;
        alert("Vui lòng nhập tên đăng nhập, email hoặc số điện thoại");
    }
    if(user.email !== undefined){
        // kiểm tra tính hợp lệ của email
        var emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
        if(!emailRegex.test(user.email)){
            isValid = false;
            alert("Email không hợp lệ");
        }
    }
    if(user.phoneNumber !== undefined){
        // kiểm tra tính hợp lệ của số điện thoại
        var phoneNumberRegex = /(84|0[3|5|7|8|9])+([0-9]{8})\b/;
        if(!phoneNumberRegex.test(user.phoneNumber)){
            isValid = false;
            alert("Số điện thoại không hợp lệ");
        }
    }
    if(user.username !== undefined){
        // kiểm tra độ dài tên đăng nhập
        if(user.username.length < 3){
            isValid = false;
            alert("Tên đăng nhập phải chứa ít nhất 3 ký tự");
        }
        if(user.username.length > 20){
            isValid = false;
            alert("Tên đăng nhập không được quá 20 ký tự");
        }
    }
    if(user.password === undefined){
        isValid = false;
        alert("Vui lòng nhập mật khẩu");
    }
    // kiểm tra độ dài mật khẩu
    if(user.password.length < 5){
        isValid = false;
        alert("Mật khẩu phải chứa ít nhất 5 ký tự");
    }
    if(user.password.length > 40){
        isValid = false;
        alert("Mật khẩu không được quá 40 ký tự");
    }
    return isValid;
}