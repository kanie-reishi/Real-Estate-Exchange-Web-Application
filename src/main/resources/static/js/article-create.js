/*** REGION 1 - Global variables - Vùng khai báo biến, hằng số, tham số TOÀN CỤC */
import { Uppy, DragDrop, XHRUpload, Dashboard } from "https://releases.transloadit.com/uppy/v3.26.1/uppy.min.mjs"
const uppy = new Uppy({
    restrictions: {
        maxFileSize: 10 * 1024 * 1024, // 10MB
        // ... other restrictions
    },
    autoProceed: true,
})
    .use(Dashboard, {
        height: 350,
        inline: true,
        target: '#drag-drop-area',
        hideUploadButton: true,
        locale: {
            strings: {
                addMoreFiles: 'Thêm ảnh',
                addingMoreFile: 'Đang thêm ảnh',
                dashboardTitle: 'Upload ảnh',
                back: 'Quay lại',
                removeFile: 'Xóa ảnh',
                dropHint: 'Kéo thả ảnh vào đây',
                uploadComplete: 'Tải lên thành công',
                uploadPaused: 'Tải lên tạm dừng',
                resumeUpload: 'Tiếp tục tải lên',
                pauseUpload: 'Tạm dừng tải lên',
                cancelUpload: 'Hủy tải lên',
                xFilesSelected: {
                    0: '%{smart_count} ảnh đã chọn',
                    1: '%{smart_count} ảnh đã chọn'
                },
                uploadingXFiles: {
                    0: 'Đang tải lên %{smart_count} ảnh',
                    1: 'Đang tải lên %{smart_count} ảnh'
                },
                processingXFiles: {
                    0: 'Đang xử lý %{smart_count} ảnh',
                    1: 'Đang xử lý %{smart_count} ảnh'
                },
                poweredBy: '',
                addMore: 'Thêm ảnh',
                save: 'Lưu',
                cancel: 'Hủy',
                dropPasteFiles: 'Kéo thả ảnh vào đây hoặc %{browseFiles}',
                dropPasteFolders: 'Kéo thả ảnh vào đây hoặc %{browseFolders}',
                dropPasteBoth: 'Kéo thả ảnh hoặc %{browseFiles} hoặc %{browseFolders}',
                browseFiles: 'chọn từ máy tính',
                browseFolders: 'chọn từ thư mục',
            }
        },
        proudlyDisplayPoweredByUppy: false,
    })
    .use(XHRUpload, { endpoint: 'http://localhost:8080/photo/upload' });
// Listen on file added event
uppy.on('file-added', (file) => {
    const url = URL.createObjectURL(file.data);
    const img = new Image();
    img.onload = function () {
        URL.revokeObjectURL(url);
        // Check if image is less than 300x300
        if (this.width < 300 || this.height < 300) {
            uppy.removeFile(file.id);
            alert('Kích thước ảnh tối thiểu 300x300px');
        }
    };
    img.src = url;
});
// Listen on upload success event
uppy.on('upload-success', (file, response) => {
     // Get the file URL from the response
     var fileId = response.body.data.id;

     // Store the file URL in the array
     imageIds.push(fileId);
});
// Add quill editor
var quill = new Quill('#editor', {
    theme: 'snow', // Set theme to snow
    placeholder: 'Nhập mô tả' // Set placeholder text here
});
// Khai báo biến toàn cục
var selectedChipValue = 1;
var selectedChipNeedValue = 1;
var imageIds = [];
/*** REGION 2 - Vùng gán / thực thi hàm xử lý sự kiện cho các elements */
document.querySelectorAll('.chip-action').forEach(function (chip) {
    chip.addEventListener('click', function () {
        // Remove the selected class from all chips
        document.querySelectorAll('.chip-action').forEach(function (otherChip) {
            otherChip.classList.remove('selected');
        });

        // Add the selected class to the clicked chip
        this.classList.add('selected');
        // Get the value of the clicked chip
        selectedChipValue = this.getAttribute('data-value');
    });
});

document.querySelectorAll('.chip-action-need').forEach(function (chip) {
    chip.addEventListener('click', function () {
        // Remove the selected class from all chips
        document.querySelectorAll('.chip-action-need').forEach(function (otherChip) {
            otherChip.classList.remove('selected');
        });

        // Add the selected class to the clicked chip
        this.classList.add('selected');
        // Get the value of the clicked chip
        selectedChipNeedValue = this.getAttribute('data-value');
    });
});
$(document).ready(function () {
    // Load Province Data
    loadProvinceData();
    // On Province Selected
    $('#select-province').change(function () {
        const provinceId = $(this).val();
        onProvinceSelectChange(provinceId);
    });
    // On District Selected
    $('#select-district').change(function () {
        const districtId = $(this).val();
        onDistrictSelectChange(districtId);
    });
    // Khi nút đăng tin được ấn
    $('#btn-dang-tin').click(function () {
        onBtnDangTinClick();
    })
});
/*** REGION 3 - Event handlers - Vùng khai báo các hàm xử lý sự kiện */
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
            console.log(error);
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
            console.log(error);
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
            console.log(error);
        }
    });
}
function onBtnDangTinClick(){
    let realestateObj = {};
    // Collect Data From Form
    realestateObj = collectDataFromForm();
    // Validate Real Estate Data
    if(validateRealEstateData(realestateObj)){
        // Post Request to create new real estate article
        $.ajax({
            url: 'http://localhost:8080/realestate',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(realestateObj),
            success: function (data) {
                // Redirect to Real Estate Detail Page
                console.log(data);
                alert('Đăng tin thành công');
                // window.location.href = `http://localhost:8080/real-estates/${data.id}`;
            },
            error: function (error) {
                alert('Đăng tin thất bại');
                console.log(error);
            }
        });
    }
}
/*** REGION 4 - Common funtions - Vùng khai báo hàm dùng chung trong toàn bộ chương trình*/
// Load Province Data
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
            console.log(error);
        }
    });
}
// Thu thập dữ liệu từ form
function collectDataFromForm() {
    let realestateObj = {};
    realestateObj.type = selectedChipValue;
    realestateObj.title = $('#input-title').val();
    realestateObj.price = $('#input-price').val() + $('#select-price').val();
    realestateObj.acreage = $('#input-acreage').val();
    realestateObj.address = $('#input-address').val();
    realestateObj.description = quill.root.innerHTML;
    realestateObj.addressDetail = {};
    realestateObj.addressDetail.street = {};
    realestateObj.addressDetail.ward = {};
    realestateObj.addressDetail.district = {};
    realestateObj.addressDetail.province = {};
    realestateObj.addressDetail.province.id = $('#select-province').val();
    realestateObj.addressDetail.district.id = $('#select-district').val();
    realestateObj.addressDetail.ward.id = $('#select-ward').val();
    realestateObj.addressDetail.street.id = $('#select-street').val();
    realestateObj.addressDetail.latitude = $('#location-coords-lat').val();
    realestateObj.addressDetail.longitude = $('#location-coords-lng').val();
    realestateObj.request = selectedChipNeedValue;
    realestateObj.photoIds = imageIds;
    console.log(realestateObj);
    return realestateObj;
}
// Validate Real Estate Data
function validateRealEstateData(realestateObj) {
    if (realestateObj.title == '' || realestateObj.price == '' || realestateObj.acreage == '' || realestateObj.address == '' || realestateObj.description == '' || realestateObj.provinceId == '' || realestateObj.districtId == '' || realestateObj.wardId == '' || realestateObj.request == '') {
        alert('Vui lòng nhập đầy đủ thông tin');
        return false;
    }
    return true;
}