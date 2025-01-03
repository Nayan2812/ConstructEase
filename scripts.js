// Add some interactive animations on page load

window.onload = function() {
    const elements = document.querySelectorAll('.animate');
    elements.forEach((element, index) => {
        setTimeout(() => {
            element.style.opacity = 1;
        }, index * 500); // Stagger the animation
    });
};

// This function can be expanded to load specific content or views for each apartment
function viewApartment(viewType, apartmentNumber) {
    console.log(`Viewing Apartment ${apartmentNumber} in ${viewType}`);
    // You can redirect or show specific content here based on the viewType
}

// Show the modal with the image
function showImage(imageSrc) {
    const modal = document.getElementById('imageModal');
    const modalImage = document.getElementById('modalImage');
    modal.style.display = 'flex'; // Show the modal
    modalImage.src = imageSrc; // Set the image source
}

// Close the modal
function closeModal() {
    const modal = document.getElementById('imageModal');
    modal.style.display = 'none'; // Hide the modal
}

