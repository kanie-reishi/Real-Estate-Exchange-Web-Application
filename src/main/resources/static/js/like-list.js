document.addEventListener("DOMContentLoaded", function () {
    const likedPropertiesDropdown = document.getElementById("likedPropertiesDropdown");
    // Ngăn chặn chuyển trang khi nhấn vào nút like
    $(".like-button").on("click", function (event) {
        event.preventDefault(); // Ngăn không cho thẻ `<a>` bị click
        event.stopPropagation(); // Ngăn không cho thẻ cha bị ảnh hưởng

        const propertyName = $(this).attr("data-name");
        const propertyLink = $(this).attr("data-link");

        addLikedProperty(propertyName, propertyLink); // Thêm vào danh sách yêu thích
        $(this).addClass("liked"); // Thêm hiệu ứng liked
    });
    // Ngăn chặn click trên thẻ `<a>` nếu nhấn vào nút like
    $(".realestate-link").on("click", function (event) {
        if ($(event.target).closest(".like-button").length > 0) {
            event.preventDefault(); // Ngăn không cho `<a>` bị click nếu nhấn vào `.like-button`
        }
    });
    // Load liked properties from localStorage
    function loadLikedProperties() {
        let likedProperties = JSON.parse(localStorage.getItem("likedProperties")) || [];
        likedPropertiesDropdown.innerHTML = '<li class="dropdown-header">Yêu thích</li>';

        if (likedProperties.length === 0) {
            likedPropertiesDropdown.innerHTML += '<li><span class="dropdown-item text-muted">Chưa có bất động sản yêu thích</span></li>';
        } else {
            likedProperties.forEach((property, index) => {
                likedPropertiesDropdown.innerHTML += `
                    <li class="d-flex justify-content-between align-items-center px-3">
                        <a href="${property.link}" class="dropdown-item">${property.name}</a>
                        <button class="btn btn-sm text-danger remove-like" data-index="${index}">&times;</button>
                    </li>`;
            });
        }
    }

    // Add property to liked list
    function addLikedProperty(name, link) {
        let likedProperties = JSON.parse(localStorage.getItem("likedProperties")) || [];

        // Avoid duplicate entries
        if (!likedProperties.some(property => property.name === name)) {
            likedProperties.push({ name, link });
            localStorage.setItem("likedProperties", JSON.stringify(likedProperties));
            loadLikedProperties();
        }
    }

    // Remove property from liked list
    likedPropertiesDropdown.addEventListener("click", function (event) {
        if (event.target.classList.contains("remove-like")) {
            let index = event.target.getAttribute("data-index");
            let likedProperties = JSON.parse(localStorage.getItem("likedProperties")) || [];
            likedProperties.splice(index, 1);
            localStorage.setItem("likedProperties", JSON.stringify(likedProperties));
            loadLikedProperties();
        }
    });

    // Attach like button functionality (Example: Liking a property)
    document.querySelectorAll(".like-button").forEach(button => {
        button.addEventListener("click", function () {
            const propertyName = this.getAttribute("data-name");
            const propertyLink = this.getAttribute("data-link");
            addLikedProperty(propertyName, propertyLink);
        });
    });

    // Load liked properties on page load
    loadLikedProperties();
});