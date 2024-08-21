/*** REGION 1 - Global variables - Vùng khai báo biến, hằng số, tham số TOÀN CỤC */

/*** REGION 2 - Vùng gán / thực thi hàm xử lý sự kiện cho các elements */
$(document).ready(function () {

    $('#realestate-table').DataTable({
        "processing": true,
        "serverSide": true,
        "scrollX": true,
        "ajax": {
            "url": "http://localhost:8080/projects/table",
            "type": "GET",
            "dataSrc": "data",
        },
        "lengthMenu": [[10, 25, 50], [10, 25, 50]], 
        "columnDefs": [
            {"orderable": false,
        "targets": [0, 16]}
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
            { "data": "name" },// Tên dự án
            { "data": null, // Tỉnh thành phố
                "render": (data, type, row) => {
                    if(row.address == null){
                        return "";
                    }
                    return row.addressDetail.province ? row.addressDetail.province.name : "";
                }
            },
            { "data": null, // Quận huyện
                "render": (data, type, row) => {
                    if(row.addressDetail == null){
                        return "";
                    }
                    return row.addressDetail.district ? row.addressDetail.district.name : "";
                }
             },
            { "data": null, // Phường xã
                "render": (data, type, row) => {
                    if(row.addressDetail == null){
                        return "";
                    }
                    return row.addressDetail.ward ? row.addressDetail.ward.name : "";
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
            { "data": "address" }, // Địa chỉ
            { "data": "description" }, // Mô tả
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
            { "data": null,
                "render": (data, type, row) => {
                    let createdDate = new Date(row.createdAt);
                    return createdDate.toLocaleDateString();
                },
            "orderable": true },// Ngày đăng
            {
                "data": null, // Diện tích tổng
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
                "data": "constructor", // Nhà thầu
                "orderable": true
            },
            {
                "data": "investor", // Chủ đầu tư
                "orderable": true
            },
            {
                "data": "designUnit", // Đơn vị thiết kế
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