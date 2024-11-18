// Lấy các phần tử từ DOM
const errorMessage = document.querySelector('.error-message');
const backHomeButton = document.querySelector('.back-home');


// Thêm sự kiện di chuột cho nút quay lại trang chủ
backHomeButton.addEventListener('mouseover', () => {
    backHomeButton.style.transform = 'scale(1.1)';
    backHomeButton.style.transition = 'transform 0.3s ease';
});

backHomeButton.addEventListener('mouseout', () => {
    backHomeButton.style.transform = 'scale(1)';
});

// Hiệu ứng mưa trái cây ngẫu nhiên
const fruits = ['🍎', '🍌', '🍉', '🍍', '🍓', '🍊', '🍇','🥑','🥝','🥥'];
const createFruitRain = () => {
    const fruit = document.createElement('div');
    fruit.innerText = fruits[Math.floor(Math.random() * fruits.length)];
    fruit.style.position = 'absolute';
    fruit.style.fontSize = '5rem';
    fruit.style.top = '0';
    fruit.style.left = Math.random() * 100 + 'vw';
    fruit.style.opacity = Math.random();
    document.body.appendChild(fruit);

    // Hiệu ứng rơi
    setTimeout(() => {
        fruit.style.transition = 'top 2s linear, opacity 2s';
        fruit.style.top = '100vh';
        fruit.style.opacity = 0;
    }, 10);

    // Xóa phần tử sau khi hoàn thành hiệu ứng
    setTimeout(() => {
        fruit.remove();
    }, 2000);
};

// Tạo mưa trái cây mỗi 500ms
setInterval(createFruitRain, 500);
