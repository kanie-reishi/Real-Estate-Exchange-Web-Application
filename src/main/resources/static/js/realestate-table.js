/*** REGION 1 - Global variables - Vùng khai báo biến, hằng số, tham số TOÀN CỤC */

/*** REGION 2 - Vùng gán / thực thi hàm xử lý sự kiện cho các elements */
$(document).ready(function () {

    $('#realestate-table').DataTable({
        "processing": true,
        "serverSide": true,
        "ajax": {
            "url": "http://localhost:8080/realestate/table",
            "type": "GET",
            "dataSrc": "data",
        },
        "lengthMenu": [[10, 25, 50], [10, 25, 50]], 
        "columnDefs": [
            {"orderable": false,
        "targets": [0, 1, 2, 3, 4, 5, 6, 7, 8, 13]}
        ],
        "columns": [
            { "data": null, // Chức năng
                "render": (data, type, row) => {
                    return `<a href="/realestate/' + row.id + '" data-toggle="tooltip" data-placement="top" title="Xem">
                                <i class="fa-solid fa-magnifying-glass" ></i>
                            </a>
                            <a href="/realestate/' + row.id + '/edit" data-toggle="tooltip" data-placement="top" title="Sửa">
                                <i class="fa-solid fa-pen"></i>
                            </a>
                            <a href="/realestate/' + row.id + '/delete" data-toggle="tooltip" data-placement="top" title="Xóa">
                                <i class="fa-solid fa-trash"></i>
                            </a>
                    `;
                }
            },
            { "data": "realEstateCode" },
            { "data": null, // Tỉnh thành phố
                "render": (data, type, row) => {
                    if(row.address == null){
                        return "";
                    }
                    return row.address.province ? row.address.province.name : "";
                }
            },
            { "data": null, // Quận huyện
                "render": (data, type, row) => {
                    if(row.address == null){
                        return "";
                    }
                    return row.address.district ? row.address.district.name : "";
                }
             },
            { "data": null, // Phường xã
                "render": (data, type, row) => {
                    if(row.address == null){
                        return "";
                    }
                    return row.address.ward ? row.address.ward.name : "";
                }
             },
            { "data": null, // Đường phố
                "render": (data, type, row) => {
                    if(row.address == null){
                        return "";
                    }
                    return row.address.street ? row.address.street.name : "";
                } 
            },
            { "data": null, // Loại hình bđs
                "render": (data, type, row) => {
                    // Danh mục tin đăng: 0.Đất, 1.Nhà ở,
                    // 2.Căn hộ/Chung cư, 3.Văn phòng, Mặt bằng
                    // 4.Kinh doanh, 5.Phòng trọ, 6.Khác
                    let realEstateType = row.type;
                    let typeName = "";
                    switch (realEstateType) {
                        case 0:
                            typeName = "Đất";
                            break;
                        case 1:
                            typeName = "Nhà ở";
                            break;
                        case 2:
                            typeName = "Căn hộ/Chung cư";
                            break;
                        case 3:
                            typeName = "Văn phòng, Mặt bằng";
                            break;
                        case 4:
                            typeName = "Kinh doanh";
                            break;
                        case 5:
                            typeName = "Phòng trọ";
                            break;
                        case 6:
                            typeName = "Khác";
                            break;
                    }
                    return typeName;
                }
            },
            { "data": null, // Nhu cầu
                "render": (data, type, row) => {
                    // Nhu cầu 0.Cần bán, 2.Cần mua, 3.Cho thuê, 4.Cần thuê
                    let request = row.request;
                    let requestName = "";
                    switch (request) {
                        case 0:
                            requestName = "Cần bán";
                            break;
                        case 2:
                            requestName = "Cần mua";
                            break;
                        case 3:
                            requestName = "Cho thuê";
                            break;
                        case 4:
                            requestName = "Cần thuê";
                            break;
                    }
                    return requestName;
                }
            },
            { "data": null, // Thông tin khách hàng
                "render": (data, type, row) => {
                    return row.customer.fullName + "-" + row.customer.phone;
                }},
            { "data": null, // Giá đăng
                "render": (data, type, row) => {
                    // Đơn vị giá: 0.Triệu, 1.Tỷ, 2.Triệu/m2, 3.Tỷ/m2, 4.Triệu/ha, 5.USD
                    let priceUnit = row.priceUnit;
                    let priceUnitName = "";
                    switch (priceUnit) {
                        case 0:
                            priceUnitName = "Triệu";
                            break;
                        case 1:
                            priceUnitName = "Tỷ";
                            break;
                        case 2:
                            priceUnitName = "Triệu/m2";
                            break;
                        case 3:
                            priceUnitName = "Tỷ/m2";
                            break;
                        case 4:
                            priceUnitName = "Triệu/ha";
                            break;
                        case 5:
                            priceUnitName = "USD";
                            break;
                    }
                    return row.price + " " + priceUnitName;
                },
                "orderable": true
             },
            { "data": "createdAt",
            "orderable": true },// Ngày đăng
            {
                "data": null, // Diện tích
                "render": function (data, type, row) {
                    let acreageUnit = row.acreageUnit;
                    let acreageUnitName = "";
                    switch (acreageUnit) {
                        case 0:
                            acreageUnitName = "m2";
                            break;
                        case 1:
                            acreageUnitName = "ha";
                            break;
                    }
                    return row.acreage + " " + acreageUnitName;
                },
                "orderable": true
            },
            {
                "data": "priceTime",
                "orderable": true
            },
            {
                "data": null, // Hình ảnh
                "render": function (data, type, row) {
                    let photoUrls = row.photoUrls;
                    if(photoUrls && photoUrls.length > 0) {
                        return photoUrls.map(url => `<img src="${url}" alt="Real Estate Image" width="50" height="50">`).join('');
                    } else {
                        return '';
                    }
                }
            }
        ]
    });

    $('[data-toggle="tooltip"]').tooltip();
});
/*** REGION 3 - Event handlers - Vùng khai báo các hàm xử lý sự kiện */

/*** REGION 4 - Common funtions - Vùng khai báo hàm dùng chung trong toàn bộ chương trình*/
function loadProvinceName(provinceId) {
    let provinceName = '';
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/provinces/" + provinceId,
        async: false,
        success: function (response) {
            provinceName = response.name;
        }
    });
    return provinceName;
}

function loadDistrictName(districtId) {
    let districtName = '';
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/districts/" + districtId,
        async: false,
        success: function (response) {
            districtName = response.name;
        }
    });
    return districtName;
}