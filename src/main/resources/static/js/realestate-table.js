/*** REGION 1 - Global variables - Vùng khai báo biến, hằng số, tham số TOÀN CỤC */

/*** REGION 2 - Vùng gán / thực thi hàm xử lý sự kiện cho các elements */
$(document).ready(function () {

    $('#realestate-table').DataTable({
        "ajax": {
            "url": "http://localhost:8080/realestate",
            "dataSrc": "content"
        },
        "columns": [
            { "data": null,
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
            { "data": "title" },
            { "data": null,
                "render": (data, type, row) => {
                    return loadProvinceName(row.provinceId);
                }
            },
            { "data": null,
                "render": (data, type, row) => {
                    return loadDistrictName(row.districtId);
                }
             },
            { "data": "acreage" },
            { "data": "description" },
            { "data": "request" },
            { "data": "provinceId" },
            { "data": "districtId" },
            { "data": "photoUrls" },
            { "data": "bedroom" },
            {
                "data": null,
                "render": function (data, type, row) {
                    return '';
                }
            },
            {
                "data": null,
                "render": function (data, type, row) {
                    return '<a href="/realestate/' + row.id + '/edit">Sửa</a>';
                }
            },
            {
                "data": null,
                "render": function (data, type, row) {
                    return '<a href="/realestate/' + row.id + '/delete">Xóa</a>';
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