const popupImages = {
    circular_portrait1: './Images/popup_image1.jpg',
    circular_portrait2: './Images/popup_image2.jpg',
    circular_portrait3: './Images/popup_image3.jpg',
    circular_portrait4: './Images/popup_image4.jpg',
    circular_portrait5: './Images/popup_image5.jpg',
};

// // Function to open the image popup with different images
// function openPopup(imageSrc) {
//     let popupImage = document.getElementById('imagePopup').querySelector('img');
//     popupImage.src = imageSrc;
//     let imagePopup = document.getElementById('imagePopup');
//     imagePopup.classList.add('flip-animation'); // Add the flip animation class
//     imagePopup.style.display = 'block';
// }

// // Function to close the image popup
// function closePopup() {
//     let imagePopup = document.getElementById('imagePopup');
//     imagePopup.classList.remove('flip-animation'); // Remove the flip animation class
//     imagePopup.style.display = 'none';
// }

// Function to open the image popup with different images
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


// Adding event listeners to all circular portraits using a loop
for (let portraitId in popupImages) {
    let portrait = document.getElementById(portraitId);
    portrait.addEventListener('mouseenter', function() {
        openPopup(popupImages[portraitId]);
    });

    portrait.addEventListener('mouseleave', function() {
        closePopup();
    });
}
