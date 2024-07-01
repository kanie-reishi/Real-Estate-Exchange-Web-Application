/*** REGION 1 - Global variables - Vùng khai báo biến, hằng số, tham số TOÀN CỤC */

/*** REGION 2 - Vùng gán / thực thi hàm xử lý sự kiện cho các elements */
$(document).ready(function () {
    // Xử lý sự kiện khi click vào nút hiển thị / ẩn mật khẩu
    $(".password-visibility").click(function () {
        togglePasswordVisibility(this);
    });
    // Xử lý sự kiện khi click vào nút đăng nhập
    $("#btn-login").click(function () {
        onBtnLoginClick();
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
            url: "http://localhost:8080/login",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(userCreditiant),
            success: function (response) {
                if(response.status === 200){
                    alert("Đăng nhập thành công");
                    // Lưu thông tin user vào sessionStorage
                    sessionStorage.setItem("user", JSON.stringify(response.data));
                    console.log(response.data);
                    // Tải lại trang
                    // location.reload();
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
    var user = {};
    var inputUsername = $("#inp-login-username").val();
    // check if inputUsername is username, email or phone number
    if (inputUsername.includes("@")) {
        user.email = inputUsername;
    } else if (inputUsername.includes("0") || inputUsername.includes("1") || inputUsername.includes("2") || inputUsername.includes("3") || inputUsername.includes("4") || inputUsername.includes("5") || inputUsername.includes("6") || inputUsername.includes("7") || inputUsername.includes("8") || inputUsername.includes("9")) {
        user.phoneNumber = inputUsername;
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
    if(user.password.length < 6){
        isValid = false;
        alert("Mật khẩu phải chứa ít nhất 6 ký tự");
    }
    if(user.password.length > 40){
        isValid = false;
        alert("Mật khẩu không được quá 40 ký tự");
    }
    return isValid;
}