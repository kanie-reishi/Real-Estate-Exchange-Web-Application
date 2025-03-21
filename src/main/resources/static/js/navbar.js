document.addEventListener("DOMContentLoaded", function () {
    function toggleNavButtons() {
        if (window.innerWidth < 768) {
            $("#desktop-login-group, #desktop-post").hide();
            $("#mobile-login, #mobile-post").show();
        } else {
            $("#desktop-login-group, #desktop-post").show();
            $("#mobile-login, #mobile-post").hide();
        }
    }

    toggleNavButtons();
    window.addEventListener("resize", toggleNavButtons);
});
