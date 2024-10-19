/*** REGION 1 - Global variables - Vùng khai báo biến, hằng số, tham số TOÀN CỤC */

/*** REGION 2 - Vùng gán / thực thi hàm xử lý sự kiện cho các elements */
$(document).ready(function () {
    // Get realestate id from url
    const urlParams = new URLSearchParams(window.location.search);
    const realestateId = urlParams.get('id');

    // Call api to get realestate detail
    $.ajax({
        url: `http://localhost:8080/realestate/${realestateId}/detail`,
        type: 'GET',
        success: function (data) {
            // Set data to elements
            $('#el_realestate_id').html(`<span>${data.id}</span>`);
            $('#el_realestate_codeName').html(`<span>${data.realEstateCode}</span>`);
            $('#el_realestate_address').html(`<span>${data.addressDetail.address}</span>`);
            $('#el_realestate_province').html(`<span>${data.addressDetail.province.name}</span>`);
            $('#el_realestate_district').html(`<span>${data.addressDetail.district.name}</span>`);
            $('#el_realestate_ward').html(`<span>${data.addressDetail.ward.name}</span>`);
            $('#el_realestate_street').html(`<span>${data.addressDetail.street}</span>`);
            $('#el_realestate_project').html(`<span>${data.project}</span>`);
            $('#el_realestate_title').html(`<span>${data.title}</span>`);
            $('#el_realestate_type').html(`<span>${data.type}</span>`);
        },
        error: function (e) {
            console.log(e);
        }
    });
});
/*** REGION 3 - Event handlers - Vùng khai báo các hàm xử lý sự kiện */

/*** REGION 4 - Common funtions - Vùng khai báo hàm dùng chung trong toàn bộ chương trình*/