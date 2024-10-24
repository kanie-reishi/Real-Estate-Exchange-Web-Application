// Javascript file chứa các hàm chuyển đổi dữ liệu từ số sang chuỗi

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