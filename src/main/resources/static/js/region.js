// Get District list when province change
function onProvinceSelectChange(paramProvinceId) {
    // Call API to get District Data
    $.ajax({
        url: `http://localhost:8080/provinces/${paramProvinceId}/districts`,
        type: 'GET',
        success: function (data) {
            // Clear District Select Element
            $('#select-district').empty();
            $('#select-ward').empty();
            $('#select-street').empty();
            $('#select-district').append(`<option value="">Chọn Quận/Huyện</option>`);
            $('#select-ward').append(`<option value="">Chọn Phường/Xã</option>`);
            $('#select-street').append(`<option value="">Chọn Đường</option>`);
            // Insert District Data to Select Element
            data.content.forEach(district => {
                $('#select-district').append(`<option value="${district.id}">${district.name}</option>`);
            });
        },
        error: function (error) {
            alert('Error when loading District Data');
            //console.log(error);
        }
    });
}
function onDistrictSelectChange(paramDistrictId) {
    // Call API to get Ward Data
    $.ajax({
        url: `http://localhost:8080/districts/${paramDistrictId}/wards`,
        type: 'GET',
        success: function (data) {
            // Clear Ward Select Element
            $('#select-ward').empty();
            $('#select-ward').append(`<option value="">Chọn Phường/Xã</option>`);
            // Insert Ward Data to Select Element
            data.content.forEach(ward => {
                $('#select-ward').append(`<option value="${ward.id}">${ward.name}</option>`);
            });
        },
        error: function (error) {
            alert('Error when loading Ward Data');
            //console.log(error);
        }
    });
    // Call API to get Street Data
    $.ajax({
        url: `http://localhost:8080/districts/${paramDistrictId}/streets`,
        type: 'GET',
        success: function (data) {
            // Clear Street Select Element
            $('#select-street').empty();
            $('#select-street').append(`<option value="">Chọn Đường</option>`);
            // Insert Street Data to Select Element
            data.content.forEach(street => {
                $('#select-street').append(`<option value="${street.id}">${street.name}</option>`);
            });
        },
        error: function (error) {
            alert('Error when loading Street Data');
            //console.log(error);
        }
    });
}

function loadProvinceData() {
    // Call API to get Province Data
    $.ajax({
        url: 'http://localhost:8080/provinces',
        type: 'GET',
        success: function (data) {
            // Clear District Select Element
            $('#select-district').empty();
            $('#select-ward').empty();
            $('#select-street').empty();
            $('#select-district').append(`<option value="">Chọn Quận/Huyện</option>`);
            $('#select-ward').append(`<option value="">Chọn Phường/Xã</option>`);
            $('#select-street').append(`<option value="">Chọn Đường</option>`);
            // Insert Province Data to Select Element
            data.forEach(province => {
                $('#select-province').append(`<option value="${province.id}">${province.name}</option>`);
            });
        },
        error: function (error) {
            alert('Error when loading Province Data');
            //console.log(error);
        }
    });
}