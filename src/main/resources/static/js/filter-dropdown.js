// Xử lý sự kiện các dropdown trong bộ lọc
$(document).ready(function () {
    $('#property-type-dropdown').on('click', function (e) {
        e.stopPropagation();
        // Nếu dropdown đang mở thì đóng lại
        if (!$('#property-type-options').hasClass('hidden')) {
            $('#property-type-options').addClass('hidden');
        } else {
            closeAllDropdowns();
            // Nếu dropdown đang đóng thì mở ra
            $('#property-type-options').toggleClass('hidden');
        }
    });

    $('#location-dropdown').on('click', function (e) {
        e.stopPropagation();
        if (!$('#location-options').hasClass('hidden')) {
            $('#location-options').addClass('hidden');
        } else {
            closeAllDropdowns();
            $('#location-options').toggleClass('hidden');
        }
    });

    $('#price-range-dropdown').on('click', function (e) {
        e.stopPropagation();
        if (!$('#price-range-options').hasClass('hidden')) {
            $('#price-range-options').addClass('hidden');
        } else {
            closeAllDropdowns();
            $('#price-range-options').toggleClass('hidden');
        }
    });

    $('#area-range-dropdown').on('click', function (e) {
        e.stopPropagation();
        if (!$('#area-range-options').hasClass('hidden')) {
            $('#area-range-options').addClass('hidden');
        } else {
            closeAllDropdowns();
            $('#area-range-options').toggleClass('hidden');
        }
    });

    $('#project-dropdown').on('click', function (e) {
        e.stopPropagation();
        if (!$('#project-options').hasClass('hidden')) {
            $('#project-options').addClass('hidden');
        } else {
            closeAllDropdowns();
            $('#project-options').toggleClass('hidden');
        }
    });
    $('body').on('click', function (e) {
        // Nếu click ra ngoài vùng dropdown
        if (!$(e.target).closest('#property-type-dropdown, #property-type-options, \
                                #location-dropdown, #location-options, \
                                #price-range-dropdown, #price-range-options, \
                                #area-range-dropdown, #area-range-options, \
                                #project-dropdown, #project-options').length) {
            closeAllDropdowns();
        }
    });
});

function closeAllDropdowns() {
    $('#property-type-options').addClass('hidden');
    $('#location-options').addClass('hidden');
    $('#price-range-options').addClass('hidden');
    $('#area-range-options').addClass('hidden');
    $('#project-options').addClass('hidden');
  }
  