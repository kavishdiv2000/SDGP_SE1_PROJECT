const popupImages = {
    circular_portrait1: '../images/popup_image1.jpg',
    circular_portrait2: '../images/popup_image2.jpg',
    circular_portrait3: '../images/popup_image3.jpg',
    circular_portrait4: '../images/popup_image4.jpg',
    circular_portrait5: '../images/popup_image5.jpg',
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
