$(document).ready(function () {
    const realEstateList = $("#realestate-list");

    // Fetch real estate data from backend (dummy data for now)
    const realEstates = get

    function renderRealEstateList() {
        realEstateList.html(""); // Clear current listings
        realEstates.forEach((property) => {
            const card = `
                <div class="col-md-4">
                    <div class="card shadow-lg mb-4">
                        <img src="${property.image}" class="card-img-top" alt="${property.title}">
                        <div class="card-body">
                            <h5 class="card-title">${property.title}</h5>
                            <p class="card-text"><i class="fas fa-map-marker-alt"></i> ${property.address}</p>
                            <button class="btn btn-success verify-btn" data-id="${property.id}">
                                <i class="fas fa-check"></i> Verify
                            </button>
                        </div>
                    </div>
                </div>
            `;
            realEstateList.append(card);
        });
    }

    // Verify Button Click Event
    $(document).on("click", ".verify-btn", function () {
        const propertyId = $(this).data("id");
        if (confirm("Are you sure you want to verify this property?")) {
            // Send verification request to backend
            $.ajax({
                url: `http://localhost:8080/admin/verify/${propertyId}`,
                type: "POST",
                success: function () {
                    alert("Property verified successfully!");
                    $(`button[data-id="${propertyId}"]`).removeClass("btn-success").addClass("btn-secondary").text("Verified").prop("disabled", true);
                },
                error: function () {
                    alert("Error verifying property.");
                }
            });
        }
    });

    renderRealEstateList();
});

function getRealEstates() {
    $.ajax({
        url: "http://localhost:8080/realestate?size=6",
        type: "GET",
    }).then(function (data) {
        return data;
    });
}
