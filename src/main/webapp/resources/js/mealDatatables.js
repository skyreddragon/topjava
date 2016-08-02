var ajaxUrl = 'ajax/profile/meals/';
var datatableApi;

function updateTable() {
    $.ajax({
        url: ajaxUrl + 'filter',
        type: 'POST',
        data: $('#filter').serialize(),
        success: updateTableByData
    });
    return false;
}

$(function () {
    datatableApi = $('#datatable').DataTable({
        "ajax": {
            "url": ajaxUrl,
            "dataSrc": ""
        },
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime",
                "render": function (date, type, row) {
                    if (type == 'display') {
                        var dateObject = new Date(date);
                        return dateObject.toISOString().substring(0, 10) +
                            " " +
                            dateObject.toISOString().substring(11, 19);
                    }
                    return date;
                }
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "defaultContent": "",
                "orderable": false,
                "render": renderEditBtn
            },
            {
                "defaultContent": "",
                "orderable": false,
                "render": renderDeleteBtn
            }
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ],
        "createdRow": function (row, data, dataIndex) {
            if (!data.exceed) {
                $(row).css("color", "green");
            }
            else {
                $(row).css("color", "red");
            }
        },
        "initComplete": makeEditable
    });
    $('#startDate').datetimepicker({
        timepicker: false,
        format: 'Y-m-d'
    });
    $('#endDate').datetimepicker({
        timepicker: false,
        format: 'Y-m-d'
    });
    $('#startTime').datetimepicker({
        datepicker: false,
        format: 'H:i'
    });
    $('#endTime').datetimepicker({
        datepicker: false,
        format: 'H:i'
    })
});
