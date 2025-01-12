/*** REGION 1 - Global variables - Vùng khai báo biến, hằng số, tham số TOÀN CỤC */

/*** REGION 2 - Vùng gán / thực thi hàm xử lý sự kiện cho các elements */
$(document).ready(function () {
    // Get realestate id from url
    const url = window.location.href;
    const urlParts = url.split('/');
    const realestateId = urlParts[urlParts.length - 2]; // Lấy phần tử thứ hai từ cuối cùng

    // Call api to get realestate detail
    $.ajax({
        url: `http://localhost:8080/realestate/${realestateId}/detail`,
        type: 'GET',
        success: function (data) {
            // Set data to elements
            setDataToElements(data);
        },
        error: function (e) {
            alert('Error when loading realestate detail');
            // console.log(e);
        }
    });
    initializeTabs();
    initializeTooltips();
    setActionOnClick(realestateId);
});
/*** REGION 3 - Event handlers - Vùng khai báo các hàm xử lý sự kiện */
// Hàm xử lý khi click vào nút xác nhận xóa mềm
function onBtnSoftDeleteClick(realEstateId) {
    $.ajax({
        url: `http://localhost:8080/admin/realestate/${realEstateId}`,
        type: 'DELETE',
        success: function (data) {
            alert('Soft delete realestate successfully');
            window.location.href = '/admin/realestate-table';
        },
        error: function (e) {
            alert('Error when soft delete realestate');
            //console.log(e);
        }
    });
}
// Hàm xử lý khi click vào nút xác nhận khôi phục
function onBtnRestoreClick(realEstateId) {
    $.ajax({
        url: `http://localhost:8080/admin/realestate/restore/${realEstateId}`,
        type: 'PUT',
        success: function (data) {
            alert('Restore realestate successfully');
            window.location.href = '/admin/realestate-table';
        },
        error: function (e) {
            alert('Error when restore realestate');
            //console.log(e);
        }
    });
}
// Hàm xử lý khi click vào nút xác nhận xóa vĩnh viễn
function onBtnHardDeleteClick(realEstateId) {
    $.ajax({
        url: `http://localhost:8080/admin/realestate/hard-delete/${realEstateId}`,
        type: 'DELETE',
        success: function (data) {
            alert('Hard delete realestate successfully');
            window.location.href = '/admin/realestate-table';
        },
        error: function (e) {
            alert('Error when hard delete realestate');
            //console.log(e);
        }
    });
}
// Hàm xử lý khi click vào nút xác nhận xác thực
function onBtnVerifyClick(realEstateId) {
    $.ajax({
        url: `http://localhost:8080/admin/realestate/verify/${realEstateId}`,
        type: 'PUT',
        success: function (data) {
            alert('Verify realestate successfully');
            window.location.href = '/admin/realestate-table';
        },
        error: function (e) {
            alert('Error when verify realestate');
            //console.log(e);
        }
    });
}
/*** REGION 4 - Common funtions - Vùng khai báo hàm dùng chung trong toàn bộ chương trình*/
// Hàm gắn onclick cho các action
function setActionOnClick(realEstateId) {
    $('#a-edit').on('click', function () {
        window.location.href = `/admin/realestate/form/${realEstateId}`;
    });
    $('#a-soft-delete').on('click', function () {
        $('#modal-soft-delete').modal('show');
    });
    $('#a-restore').on('click', function () {
        $('#modal-restore').modal('show');
    });
    $('#a-hard-delete').on('click', function () {
        $('#modal-hard-delete').modal('show');
    });
    $('#a-verify').on('click', function () {
        $('#modal-verify').modal('show');
    });
    $('#btn-soft-delete').on('click', function () {
        onBtnSoftDeleteClick(realEstateId);
    });
    $('#btn-restore').on('click', function () {
        onBtnRestoreClick(realEstateId);
    });
    $('#btn-hard-delete').on('click', function () {
        onBtnHardDeleteClick(realEstateId);
    });
    $('#btn-verify').on('click', function () {
        onBtnVerifyClick(realEstateId);
    });
}
// Hàm khởi tạo tooltip
function initializeTooltips() {
    $('[data-bs-toggle="tooltip"]').tooltip();
}
// Hàm thiết lập dữ liệu cho các phần tử <span>
function setDataToElements(data) {
    $('#el_realestate_id').html(`<span>${data.id || ''}</span>`);
    $('#el_realestate_codeName').html(`<span>${data.realEstateCode || ''}</span>`);
    $('#el_realestate_address').html(`<span>${data.addressDetail?.addressDetail || ''}</span>`);
    $('#el_realestate_province').html(`<span>${data.addressDetail?.province?.name || ''}</span>`);
    $('#el_realestate_district').html(`<span>${data.addressDetail?.district?.name || ''}</span>`);
    $('#el_realestate_wards').html(`<span>${data.addressDetail?.ward ? data.addressDetail.ward.prefix + " " + data.addressDetail.ward.name : ''}</span>`);
    $('#el_realestate_street').html(`<span>${data.addressDetail?.street ? data.addressDetail.street.prefix + " " + data.addressDetail.street.name : ''}</span>`);
    $('#el_realestate_project').html(`<span>${data.project?.name || ''}</span>`);
    $('#el_realestate__title').html(`<span>${data.title || ''}</span>`);
    $('#el_realestate_type').html(`<span>${convertRealEstateType(data.type) || ''}</span>`);
    $('#el_realestate__request').html(`<span>${convertRequestType(data.request) || ''}</span>`);
    $('#el_realestate_customer_id').html(`<span>${data.customer ? (data.customer?.fullName || '') + " " + (data.customer?.phone || '') : ''}</span>`);
    $('#el_realestate_price').html(`<span>${(data.price || '') + " " + (convertPriceUnit(data.priceUnit) || '')}</span>`);
    $('#el_realestate_price_min').html(`<span>${data.detail?.priceMin || ''}</span>`);
    $('#el_realestate_price_time').html(`<span>${convertPriceTime(data.priceTime) || ''}</span>`);
    $('#el_realestate_apart_code').html(`<span>${data.apartDetail?.apartCode || ''}</span>`);
    $('#el_realestate_wall_area').html(`<span>${data.detail?.wallArea || ''}</span>`);
    $('#el_realestate_bedroom').html(`<span>${data.bedroom || ''}</span>`);
    $('#el_realestate_balcony').html(`<span>${data.detail?.balcony || ''}</span>`);
    $('#el_realestate_landscape_view').html(`<span>${data.landscapeView || ''}</span>`);
    $('#el_realestate_apart_loca').html(`<span>${convertApartLoca(data.apartDetail?.apartLoca) || ''}</span>`);
    $('#el_realestate_apart_type').html(`<span>${convertApartType(data.apartDetail?.apartType) || ''}</span>`);
    $('#el_realestate_furniture_type').html(`<span>${convertFurnitureType(data.detail?.furnitureType) || ''}</span>`);
    $('#el_realestate_price_rent').html(`<span>${data.detail?.priceRent || ''}</span>`);
    $('#el_realestate_return_rate').html(`<span>${data.detail?.returnRate ? data.detail.returnRate + "%" : '0%'}</span>`);
    $('#el_realestate_date_create').html(`<span>${data.createdAt || ''}</span>`);
    $('#el_realestate_acreage').html(`<span>${(data.acreage || '') + " " + (convertAcreageUnit(data.acreageUnit) || '')}</span>`);
    $('#el_realestate_direction').html(`<span>${convertDirection(data.direction) || ''}</span>`);
    $('#el_realestate_total_floors').html(`<span>${data.totalFloors || ''}</span>`);
    $('#el_realestate_number_floors').html(`<span>${data.apartDetail?.numberFloors || ''}</span>`);
    $('#el_realestate_bath').html(`<span>${data.bath || ''}</span>`);
    $('#el_realestate_legal_doc').html(`<span>${data.legalDoc || ''}</span>`);
    $('#el_realestate_description').html(`<span>${data.description || ''}</span>`);
    $('#el_realestate_width_y').html(`<span>${data.widthY || ''}</span>`);
    $('#el_realestate_long_x').html(`<span>${data.longX || ''}</span>`);
    $('#el_realestate_street_house').html(`<span>${data.streetHouse || ''}</span>`);
    $('#el_realestate_FSBO').html(`<span>${data.detail?.FSBO || ''}</span>`);
    $('#el_realestate_view_num').html(`<span>${data.viewNum || ''}</span>`);
    $('#el_realestate_create_by').html(`<span>${data.createBy || ''}</span>`);
    $('#el_realestate_update_by').html(`<span>${data.updateBy || ''}</span>`);
    $('#el_realestate_shape').html(`<span>${data.detail.shape || ''}</span>`);
    $('#el_realestate_distance2facade').html(`<span>${data.detail?.distance2Facade || ''}</span>`);
    $('#el_realestate_adjacent_facade_num').html(`<span>${data.detail?.adjacentFacadeNum || ''}</span>`);
    $('#el_realestate_adjacent_road').html(`<span>${data.detail?.adjacentRoad || ''}</span>`);
    $('#el_realestate_CTXD_price').html(`<span>${data.detail?.ctxdPrice || ''}</span>`);
    $('#el_realestate_CTXD_value').html(`<span>${data.detail?.ctxdValue || ''}</span>`);
    $('#el_realestate_alley_min_width').html(`<span>${data.alleyMinWidth || ''}</span>`);
    $('#el_realestate_adjacent_alley_min_width').html(`<span>${data.adjacentAlleyMinWidth || ''}</span>`);
    $('#el_realestate_factor').html(`<span>${data.factor || ''}</span>`);
    $('#el_realestate_structure').html(`<span>${data.structure || ''}</span>`);
    $('#el_realestate_DTSXD').html(`<span>${data.detail?.DTSXD || ''}</span>`);
    $('#el_realestate_CLCL').html(`<span>${data.CLCL || ''}</span>`);
    $('#el_realestate__lat').html(`<span>${data.addressDetail.latituude || ''}</span>`);
    $('#el_realestate__lng').html(`<span>${data.addressDetail.longitude || ''}</span>`);
}

// Hàm khởi tạo các tab
function initializeTabs() {
    $('button[data-bs-toggle="tab"]').on('click', function (e) {
        e.preventDefault();
        $(this).tab('show');
    });
    $('button[data-bs-toggle="tab"]').on('shown.bs.tab', function (e) {
        // remove active
        $('button[data-bs-toggle="tab"]').removeClass('active');
        $('div.tab-pane').removeClass('active');
        $('div.tab-pane').removeClass('show');
        // add active to current tab
        $(e.target).addClass('active');
        let tabPaneId = e.target.getAttribute('data-bs-target');
        let tabPane = document.querySelector(tabPaneId);
        tabPane.classList.add('active');
        tabPane.classList.add('show');
    });
}

// Hàm chuyển đổi loại hình bất động sản từ số sang chuỗi
function convertRealEstateType(type) {
    switch (type) {
        case 0:
            return "Đất";
        case 1:
            return "Nhà ở";
        case 2:
            return "Căn hộ/Chung cư";
        case 3:
            return "Văn phòng, Mặt bằng";
        case 4:
            return "Kinh doanh";
        case 5:
            return "Phòng trọ";
        case 6:
            return "Khác";
        default:
            return "";
    }
}

// Hàm chuyển đổi nhu cầu từ số sang chuỗi
function convertRequestType(request) {
    switch (request) {
        case 0:
            return "Cần bán";
        case 2:
            return "Cần mua";
        case 3:
            return "Cho thuê";
        case 4:
            return "Cần thuê";
        default:
            return "";
    }
}

// Hàm chuyển đổi đơn vị giá từ số sang chuỗi
function convertPriceUnit(priceUnit) {
    switch (priceUnit) {
        case 0:
            return "Triệu";
        case 1:
            return "Tỷ";
        case 2:
            return "Triệu/m2";
        case 3:
            return "Tỷ/m2";
        case 4:
            return "Triệu/ha";
        case 5:
            return "USD";
        default:
            return "";
    }
}

// Hàm chuyển đổi đơn vị diện tích từ số sang chuỗi
function convertAcreageUnit(acreageUnit) {
    switch (acreageUnit) {
        case 0:
            return "m2";
        case 1:
            return "ha";
        default:
            return "";
    }
}

// Hàm chuyển đổi loại nội thất từ số sang chuỗi
function convertFurnitureType(furnitureType) {
    switch (furnitureType) {
        case 0:
            return "Cơ bản";
        case 1:
            return "Đầy đủ";
        case 2:
            return "Không";
        case 3:
            return "Chưa biết";
        default:
            return "";
    }
}

// Hàm chuyển đổi thời gian bán từ số sang chuỗi
function convertPriceTime(priceTime) {
    switch (priceTime) {
        case 1:
            return "Bán nhanh";
        case 2:
            return "Bán Chậm";
        default:
            return "";
    }
}

// Hàm chuyển đổi hướng nhà từ số sang chuỗi
function convertDirection(direction) {
    switch (direction) {
        case 1:
            return "Đông";
        case 2:
            return "Tây";
        case 3:
            return "Bắc";
        case 4:
            return "Nam";
        case 5:
            return "Đông Bắc";
        case 6:
            return "Tây Bắc";
        case 7:
            return "Đông Nam";
        case 8:
            return "Tây Nam";
        case 9:
            return "Không rõ";
        default:
            return "";
    }
}

// Hàm chuyển đổi loại căn hộ từ số sang chuỗi
function convertApartType(apartType) {
    switch (apartType) {
        case 0:
            return "Cao cấp";
        case 1:
            return "Văn phòng";
        case 2:
            return "Bình dân";
        default:
            return "";
    }
}

// Hàm chuyển vị trí căn hộ từ số sang chuỗi
function convertApartLoca(apartLoca) {
    switch (apartLoca) {
        case 0:
            return "Trung tâm";
        case 1:
            return "Ngoại ô";
        case 2:
            return "Hẻm";
        default:
            return "";
    }
}
