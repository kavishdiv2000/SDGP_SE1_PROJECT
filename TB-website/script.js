window.onload = function() {
    const navItems = document.querySelectorAll('#nav-menu li');

    navItems.forEach((item) => {
        item.addEventListener('click', function() {
            navItems.forEach((item) => {
                item.classList.remove('active');
            });
            this.classList.add('active');
        });
    });
};