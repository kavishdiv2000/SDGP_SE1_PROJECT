const popupImages = {
    circular_portrait1: '../images/Kavish_Profile.png',
    circular_portrait2: '../images/Rumal_Profile.png',
    circular_portrait3: '../images/Chandupa_Profile.png',
    circular_portrait4: '../images/Shashank_Profile.png',
    circular_portrait5: '../images/Felishia_Profile.png',
};

function openPopup(imageSrc) {
    let popupImage = document.getElementById('imagePopup').querySelector('img');
    popupImage.src = imageSrc;
    let imagePopup = document.getElementById('imagePopup');
    imagePopup.classList.add('gb-popup-scale-blur'); // Add the class to trigger animation
    imagePopup.style.display = 'block';
}

// Function to close the image popup
function closePopup() {
    let imagePopup = document.getElementById('imagePopup');
    imagePopup.classList.remove('gb-popup-scale-blur'); // Remove the class to stop animation
    imagePopup.style.display = 'none';
}


// Event listeners to all circular portraits using a loop
for (let portraitId in popupImages) {
    let portrait = document.getElementById(portraitId);
    portrait.addEventListener('mouseenter', function() {
        openPopup(popupImages[portraitId]);
    });

    portrait.addEventListener('mouseleave', function() {
        closePopup();
    });
}
