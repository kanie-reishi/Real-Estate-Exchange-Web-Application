/*** REGION 1 - Global variables - Vùng khai báo biến, hằng số, tham số TOÀN CỤC */

/*** REGION 2 - Vùng gán / thực thi hàm xử lý sự kiện cho các elements */
$(document).ready(function () {
    // Load province data
    loadProvinceData();
    // Load real estate data
    loadBatDongSanPhuHop();
    loadBatDongSanNoiBat();
    // Handle search button click event
    $("#btn-search").click(function () {
        var provinceId = $("#province").val();
        var address = $("#inp-address").val();
        var price = $("input[name='price']:checked").val();
        var acreage = $("input[name='acreage']:checked").val();
        // go to list.html
        window.location.href = "list.html?provinceId=" + provinceId + "&address=" + address + "&price=" + price + "&acreage=" + acreage;
    });
});
/*** REGION 3 - Event handlers - Vùng khai báo các hàm xử lý sự kiện */

/*** REGION 4 - Common funtions - Vùng khai báo hàm dùng chung trong toàn bộ chương trình*/
// Hàm load dữ liệu bất động sản phù hợp
function loadBatDongSanPhuHop() {
    $.ajax({
        url: "http://localhost:8080/realestate?size=6",
        type: "GET",
    }).then(function (data) {
        console.log(data);
        // Load province and district data with AJAX promise
        var promises = data.content.map(function (realEstate) {
            var provincePromise = $.ajax({
                url: "http://localhost:8080/provinces/" + realEstate.provinceId,
                type: "GET"
            });
            var districtPromise = $.ajax({
                url: "http://localhost:8080/districts/" + realEstate.districtId,
                type: "GET"
            });
            return Promise.all([provincePromise, districtPromise]).then(function (results) {
                var province = results[0];
                var district = results[1];
                if (realEstate.photoUrls.length == 0) {
                    realEstate.photoUrls[0] = 'images/house.webp';
                }
                if (realEstate.title == null) {
                    realEstate.title = 'Không có tiêu đề';
                }
                var realEstateCard = `
                        <div class="bg-white cursor-pointer slider-hover relative rounded-lg border border-gray-v1">
                        <a href="article-detail.html?article-id=${realEstate.id}">
                            <div class="rounded-lg transition-shadow duration-300 text-black-v8 bg-white w-full w-full">
                                <div class="relative overflow-hidden rounded-lg">
                                    <div>
                                        <div class="swiper-container">
                                            <div class="swiper-wrapper">
                                                ${realEstate.photoUrls.map(url => `
                                                <div class="swiper-slide">
                                                    <img src="${url}" alt="${realEstate.title}" class="image-fill mx-auto mx-auto rounded-lg w-full" width="330" height="220">
                                                    <div class="absolute h-4 w-auto rounded-xl bg-[rgba(52,52,52,0.6)] text-xxs text-white bottom-1.5 left-2 flex items-center px-1 py-0.5">
                                                    <div class="w-[7px] h-[7px] bg-green-v3 border border-solid border-white rounded mr-1"></div>
                                                    <span>Môi giới</span>
                                                </div>
                                                </div>
                                                `).join('')}
                                            </div>
                                            <!-- Add Pagination -->
                                            <div class="swiper-pagination"></div>
                                            <!-- Add Navigation -->
                                            <div class="swiper-button-next"></div>
                                            <div class="swiper-button-prev"></div>
                                        </div>
                                        
                                        <div class="absolute top-2 right-2">
                                            <div class="fresnel-container fresnel-greaterThanOrEqual-xl ">
                                                <button class="flex items-center justify-center relative h-8 w-8 rounded-full z-[1] bg-[rgba(52,52,52,0.25)]" aria-label="button-favorite">
                                                    <i class="ml ml-favorite_border text-white absolute top-1/2 -translate-y-1/2 text-[22px]"></i>
                                                    <i class="ml ml-favorite text-[rgba(52,52,52,0.4)] text-[20px]"></i>
                                                </button>
                                            </div>
                                            <div class="fresnel-container fresnel-lessThan-xl "></div>
                                            <div class="fresnel-container fresnel-greaterThanOrEqual-xl ">
                                                <div class="mt-2">
                                                    <button class="rounded-full z-[1] w-8 h-8 bg-[rgba(52,52,52,0.25)]" aria-label="button-compare">
                                                        <div class="flex items-center justify-center relative">
                                                            <span class="ml ml-compare text-[1.25rem]">
                                                                <span class="path1 text-[rgba(52,52,52,0.4)]"></span>
                                                                <span class="path2 text-[rgba(52,52,52,0.4)]"></span>
                                                                <span class="path3 text-white"></span>
                                                            </span>
                                                        </div>
                                                    </button>
                                                </div>
                                            </div>
                                            <div class="fresnel-container fresnel-lessThan-xl "></div>
                                        </div>
                                        <div class="absolute bottom-2 right-2 z-[1] text-white text-xs font-medium flex items-center">
                                            <div class="flex items-center">
                                                <i class="ml ml-image text-white text-[1rem] mr-0.5"></i>
                                                <span>8</span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="h-[168px] md:h-[168px] p-3">
                                        <div class="flex flex-col">
                                            <div>
                                                <div class="mb-[0.375rem] lg:mb-3 text-base font-medium line-clamp-2 text-[#2b2b2b] h-12">
                                                    <h3>
                                                        <a title="${realEstate.title}" href="/co-dien-tich-chung-la-90m2-ban-nha-vi-tri-thuan-loi-toa-lac-tren-cau-giay-ha-noi-nhin-chung-gom-20-phong-ngu-20-wc-lien-he-chinh-chu-1712081528257">
                                                            ${realEstate.title} 
                                                        </a>
                                                    </h3>
                                                </div>
                                            </div>
                                            <p class="h-7 mb-1 text-base font-semibold lg:line-clamp-1 flex items-center">
                                                <span>${realEstate.price} Tỷ</span>
                                                <span class="h-[3px] w-[3px] inline-block bg-[#C4C4C4] rounded mx-1.5 mb-1"></span>
                                                <span>${(realEstate.price * 1000 / realEstate.acreage).toPrecision(4)} Tr/m²</span>
                                            </p>
                                            <div class="text-sm text-black-v6 lg:text-black-v8 flex h-5">
                                                <div class="text-sm break-word line-clamp-1">
                                                    <span>${realEstate.acreage} m
                                                        <sup>2</sup>
                                                    </span>
                                                </div>
                                            </div>
                                        </div>
                                        <div>
                                            <div class="w-full border-b border-dashed border-black-v1 my-2"></div>
                                            <div class="flex items-center justify-between text-sm text-black-v7">
                                                <p class="line-clamp-1">${district.name}, ${province.name}</p>
                                                <p class="inline-block whitespace-nowrap ml-4 text-xs">${convertDate(realEstate.dateCreate)}</p>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="fresnel-container fresnel-greaterThanOrEqual-lg "></div>
                                </div>
                                <div class="absolute top-[60%] bg-black-v1 w-full h-[1px] opacity-0"></div>
                                <div class="absolute top-[50%] right-0 bg-black-v1 w-4 h-4 opacity-0"></div>
                                </a>
                            </div>
                           
                        `;
                $("#real-estate-phu-hop-container").append(realEstateCard);
                // Initialize Swiper
                const swiper = new Swiper('.swiper-container', {
                    slidesPerView: 1,
                    spaceBetween: 10,
                    direction: 'horizontal',
                    loop: true,
                    navigation: {
                        nextEl: '.swiper-button-next',
                        prevEl: '.swiper-button-prev',
                    },
                    pagination: {
                        el: '.swiper-pagination',
                        clickable: true,
                    },
                });
            });
        });
    });
}
// Hàm load dữ liệu bất động sản nổi bật
function loadBatDongSanNoiBat() {
    $.ajax({
        url: "http://localhost:8080/realestate?page=224&size=6&sort=id",
        type: "GET",
    }).then(function (data) {
        console.log(data);
        // Load province and district data with AJAX promise
        var promises = data.content.map(function (realEstate) {
            var provincePromise = $.ajax({
                url: "http://localhost:8080/provinces/" + realEstate.provinceId,
                type: "GET"
            });
            var districtPromise = $.ajax({
                url: "http://localhost:8080/districts/" + realEstate.districtId,
                type: "GET"
            });
            return Promise.all([provincePromise, districtPromise]).then(function (results) {
                var province = results[0];
                var district = results[1];
                if (realEstate.photoUrls.length == 0) {
                    realEstate.photoUrls[0] = 'images/house.webp';
                }
                if (realEstate.title == null) {
                    realEstate.title = 'Không có tiêu đề';
                }
                var realEstateCard = `
                        <div class="bg-white cursor-pointer slider-hover relative rounded-lg border border-gray-v1">
                            <a href="article-detail.html?article-id=${realEstate.id}">
                                <div class="rounded-lg transition-shadow duration-300 text-black-v8 bg-white w-full w-full">
                                    <div class="relative overflow-hidden rounded-lg">
                                        <div>
                                            <div class="swiper-container">
                                                <div class="swiper-wrapper">
                                                    ${realEstate.photoUrls.map(url => `
                                                    <div class="swiper-slide">
                                                        <img src="${url}" alt="${realEstate.title}" class="image-fill mx-auto mx-auto rounded-lg w-full" width="330" height="220">
                                                        <div class="absolute h-4 w-auto rounded-xl bg-[rgba(52,52,52,0.6)] text-xxs text-white bottom-1.5 left-2 flex items-center px-1 py-0.5">
                                                        <div class="w-[7px] h-[7px] bg-green-v3 border border-solid border-white rounded mr-1"></div>
                                                        <span>Môi giới</span>
                                                    </div>
                                                    </div>
                                                    `).join('')}
                                                </div>
                                                <!-- Add Pagination -->
                                                <div class="swiper-pagination"></div>
                                                <!-- Add Navigation -->
                                                <div class="swiper-button-next"></div>
                                                <div class="swiper-button-prev"></div>
                                            </div>
                                            <div class="absolute top-2 right-2">
                                                <div class="fresnel-container fresnel-greaterThanOrEqual-xl ">
                                                    <button class="flex items-center justify-center relative h-8 w-8 rounded-full z-[1] bg-[rgba(52,52,52,0.25)]" aria-label="button-favorite">
                                                        <i class="ml ml-favorite_border text-white absolute top-1/2 -translate-y-1/2 text-[22px]"></i>
                                                        <i class="ml ml-favorite text-[rgba(52,52,52,0.4)] text-[20px]"></i>
                                                    </button>
                                                </div>
                                                <div class="fresnel-container fresnel-lessThan-xl "></div>
                                                <div class="fresnel-container fresnel-greaterThanOrEqual-xl ">
                                                    <div class="mt-2">
                                                        <button class="rounded-full z-[1] w-8 h-8 bg-[rgba(52,52,52,0.25)]" aria-label="button-compare">
                                                            <div class="flex items-center justify-center relative">
                                                                <span class="ml ml-compare text-[1.25rem]">
                                                                    <span class="path1 text-[rgba(52,52,52,0.4)]"></span>
                                                                    <span class="path2 text-[rgba(52,52,52,0.4)]"></span>
                                                                    <span class="path3 text-white"></span>
                                                                </span>
                                                            </div>
                                                        </button>
                                                    </div>
                                                </div>
                                                <div class="fresnel-container fresnel-lessThan-xl "></div>
                                            </div>
                                            <div class="absolute bottom-2 right-2 z-[1] text-white text-xs font-medium flex items-center">
                                                <div class="flex items-center">
                                                    <i class="ml ml-image text-white text-[1rem] mr-0.5"></i>
                                                    <span>8</span>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="h-[168px] md:h-[168px] p-3">
                                            <div class="flex flex-col">
                                                <div>
                                                    <div class="mb-[0.375rem] lg:mb-3 text-base font-medium line-clamp-2 text-[#2b2b2b] h-12">
                                                        <h3>
                                                            <a title="${realEstate.title}" href="article-detail.html?article-id=${realEstate.id}">
                                                                ${realEstate.title} 
                                                            </a>
                                                        </h3>
                                                    </div>
                                                </div>
                                                <p class="h-7 mb-1 text-base font-semibold lg:line-clamp-1 flex items-center">
                                                    <span>${realEstate.price} Tỷ</span>
                                                    <span class="h-[3px] w-[3px] inline-block bg-[#C4C4C4] rounded mx-1.5 mb-1"></span>
                                                    <span>${(realEstate.price * 1000 / realEstate.acreage).toPrecision(4)} Tr/m²</span>
                                                </p>
                                                <div class="text-sm text-black-v6 lg:text-black-v8 flex h-5">
                                                    <div class="text-sm break-word line-clamp-1">
                                                        <span>${realEstate.acreage} m
                                                            <sup>2</sup>
                                                        </span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div>
                                                <div class="w-full border-b border-dashed border-black-v1 my-2"></div>
                                                <div class="flex items-center justify-between text-sm text-black-v7">
                                                    <p class="line-clamp-1">${district.name}, ${province.name}</p>
                                                    <p class="inline-block whitespace-nowrap ml-4 text-xs">${convertDate(realEstate.dateCreate)}</p>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="fresnel-container fresnel-greaterThanOrEqual-lg "></div>
                                    </div>
                                    <div class="absolute top-[60%] bg-black-v1 w-full h-[1px] opacity-0"></div>
                                    <div class="absolute top-[50%] right-0 bg-black-v1 w-4 h-4 opacity-0"></div>
                                </a>
                            </div>
                        `;
                $("#real-estate-noi-bat-container").append(realEstateCard);
                // Initialize Swiper
                const swiper = new Swiper('.swiper-container', {
                    slidesPerView: 1,
                    spaceBetween: 10,
                    direction: 'horizontal',
                    loop: true,
                    navigation: {
                        nextEl: '.swiper-button-next',
                        prevEl: '.swiper-button-prev',
                    },
                    pagination: {
                        el: '.swiper-pagination',
                        clickable: true,
                    },
                });
            });
        });
    });
}
// Hàm load dữ liệu tỉnh/thành phố
function loadProvinceData() {
    $.ajax({
        url: "http://localhost:8080/provinces",
        type: "GET",
    }).then(function (data) {
        data.forEach(function (province) {
            var provinceOption = `<option value="${province.id}">${province.name}</option>`;
            $("#province").append(provinceOption);
        });
    });
}
// Hàm đổi date
function convertDate(paramDate) {
    var date = new Date(paramDate);
    var day = date.getDate();
    var month = date.getMonth() + 1; // Months are zero based
    var year = date.getFullYear();
    return day + '/' + month + '/' + year;
}