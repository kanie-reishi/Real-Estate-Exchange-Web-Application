/*** REGION 1 - Global variables - Vùng khai báo biến, hằng số, tham số TOÀN CỤC */

/*** REGION 2 - Vùng gán / thực thi hàm xử lý sự kiện cho các elements */
$(document).ready(function () {
    $('#wardTable').DataTable({
        "processing": true,
        "serverSide": true,
        "scrollX": true,
        "responsive": true,
        "ajax": {
            "url": "http://localhost:8080/wards",
            "type": "GET",
            "dataSrc": "data",
        },
        "lengthMenu": [[10, 25, 50], [10, 25, 50]],
        "columnDefs": [
            {"orderable": false, "targets": [0]}
        ],
        "columns": [
            { "data": "id" },
            { "data": "prefix" + "name" },
            { "data": "provinceName" },
            { "data": "districtName" }
        ]
    });

    $('[data-toggle="tooltip"]').tooltip();
});
/*** REGION 3 - Event handlers - Vùng khai báo các hàm xử lý sự kiện */

/*** REGION 4 - Common funtions - Vùng khai báo hàm dùng chung trong toàn bộ chương trình*/