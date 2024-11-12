$(document).ready(function () {
    $('#provinceTable').DataTable({
        "processing": true,
        "serverSide": true,
        "scrollX": true,
        "responsive": true,
        "ajax": {
            "url": "http://localhost:8080/provinces/table",
            "type": "GET",
            "dataSrc": "data",
        },
        "lengthMenu": [[10, 25, 50], [10, 25, 50]],
        "columnDefs": [
            {"orderable": false, "targets": [0]}
        ],
        "columns": [
            { "data": "id" },
            { "data": "name" },
            { "data": "code" },
            { "data": "realEstateCount" }
        ]
    });

    $('[data-toggle="tooltip"]').tooltip();
});