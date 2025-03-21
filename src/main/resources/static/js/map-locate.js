// Khởi tạo map và marker có thể kéo được
// Sử dụng thư viện LeafletJS (https://leafletjs.com/)
document.addEventListener("DOMContentLoaded", function () {
    // Khởi tạo map tại vị trí mặc định (ví dụ: Hà Nội)
    var map = L.map('map').setView([21.0278, 105.8342], 13);

    // Thêm tile layer (sử dụng OpenStreetMap)
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        attribution: '© OpenStreetMap'
    }).addTo(map);

    // Thêm marker có thể kéo được
    var marker = L.marker([21.0278, 105.8342], { draggable: true }).addTo(map);

    // Cập nhật trường ẩn với tọa độ của marker
    function updateMarkerCoords() {
        var pos = marker.getLatLng();
        document.getElementById('location-coords-lat').value = pos.lat;
        document.getElementById('location-coords-lng').value = pos.lng;
    }

    // Gọi khi marker được kéo và thả
    marker.on('dragend', updateMarkerCoords);

    // Gọi cập nhật ban đầu
    updateMarkerCoords();
    let debounceTimer;
    document.getElementById('input-address').addEventListener('input', function () {
        clearTimeout(debounceTimer);
        debounceTimer = setTimeout(() => {
            var address = getAddress();
            if (address) {
                geocodeAddress(address, marker, map);
            }
        }, 500); // 500ms debounce, bạn có thể điều chỉnh thời gian này
    });
    $('#select-province').change(function () {
        var address = getAddress();
        if (address) {
            geocodeAddress(address, marker, map);
        }
    });
    $('#select-district').change(function () {
        var address = getAddress();
        if (address) {
            geocodeAddress(address, marker, map);
        }
    });
});
// Gọi hàm Geocoding
function geocodeAddress(address, marker, map) {
    // Gọi API Nominatim
    var url = "https://nominatim.openstreetmap.org/search?format=json&q=" + encodeURIComponent(address);

    fetch(url)
        .then(response => response.json())
        .then(data => {
            if (data && data.length > 0) {
                // Lấy kết quả đầu tiên
                var lat = data[0].lat;
                var lon = data[0].lon;
                // Cập nhật vị trí map
                // Di chuyển marker đến vị trí mới
                marker.setLatLng([lat, lon]);

                // Nếu bạn muốn cập nhật view của bản đồ theo marker:
                map.setView(marker.getLatLng(), map.getZoom());


                // Cập nhật trường ẩn nếu cần
                document.getElementById('location-coords-lat').value = lat;
                document.getElementById('location-coords-lng').value = lon;
            } else {
                alert("Không tìm thấy địa chỉ. Vui lòng thử lại.");
            }
        })
        .catch(error => {
            console.error("Lỗi khi geocode: ", error);
            alert("Có lỗi xảy ra. Vui lòng thử lại.");
        });
}
// Hàm address lấy giá trị từ select tỉnh thành, quận thành và địa chỉ cụ thể
function getAddress() {
    let province = $('#select-province :selected').text();
    let district = $('#select-district :selected').text();
    // check if address is not selected
    if ($('#select-province').val() == 0) {
        province = "";
    }
    if ($('#select-district').val() == 0) {
        district = "";
    }
    var address = document.getElementById('input-address').value + ", " + district + ", " + province;
    return address;
}
