const incrementBtn = document.querySelector('.increment-btn');
const decrementBtn = document.querySelector('.decrement-btn');
const quantityInput = document.getElementById('quantity');




incrementBtn.addEventListener('click', function() {
    let currentValue = parseInt(quantityInput.value);
    let max = parseInt(quantityInput.max);
    let step = parseInt(quantityInput.step);

    if (currentValue < max) {
        quantityInput.value = (currentValue + step);
    }
});


decrementBtn.addEventListener('click', function() {
    let currentValue = parseInt(quantityInput.value);
    let min = parseInt(quantityInput.min);
    let step = parseInt(quantityInput.step);

    if (currentValue > min) {
        quantityInput.value = (currentValue - step);
    }
});



// let currentFrame = 1;
//     const totalFrames = 36;
//     const imageElement = document.getElementById('product3d');


//     function updateImage(frame) {
//       imageElement.src = `https://suckhoedoisong.qltns.mediacdn.vn/Images/haiyen/2018/12/10/tao.jpg`;
//     }


//     imageElement.addEventListener('mousemove', function(event) {
//       const x = event.clientX;
//       const width = imageElement.offsetWidth;
//       const frameIndex = Math.ceil((x / width) * totalFrames);
//       updateImage(frameIndex);
//     });



    // Thêm vào giỏ hàng với số lượng
    function addToCart() {
        let productName = document.querySelector('.product-title').textContent;
        let quantity = parseFloat(quantityInput.value);
        alert(`Đã thêm ${quantity} kg x ${productName} vào giỏ hàng.`);
    }

    document.querySelector('.add-to-cart').addEventListener('click', addToCart);
