let slideIndex = 1;
showSlides(slideIndex);
startSlideShow();
// Next/previous controls
function plusSlides(n) {
  showSlides(slideIndex += n);
  resetSlideShow(); 
}


function currentSlide(n) {
  showSlides(slideIndex = n);
  resetSlideShow(); 
}

function showSlides(n) {
  let i;
  let slides = document.getElementsByClassName("mySlides");
  let dots = document.getElementsByClassName("dot");
  if (n > slides.length) {slideIndex = 1}
  if (n < 1) {slideIndex = slides.length}
  for (i = 0; i < slides.length; i++) {
    slides[i].style.display = "none";
  }
  for (i = 0; i < dots.length; i++) {
    dots[i].className = dots[i].className.replace(" active", "");
  }
  slides[slideIndex-1].style.display = "block";
  dots[slideIndex-1].className += " active";
 
}

function startSlideShow() {
  slideInterval = setInterval(() => {
    plusSlides(1); 
  }, 5000); 
}


function resetSlideShow() {
  clearInterval(slideInterval); // Xóa interval cũ
  startSlideShow(); // Bắt đầu lại từ đầu
}


///hien text banner 1
const obsv= new IntersectionObserver((entries)=>{
  entries.forEach((entry)=>{
      console.log(entry)
      if(entry.isIntersecting){
          entry.target.classList.add('show_text_slide');
      }else{
          entry.target.classList.remove('show_text_slide')
      }
  })
})
const hiddenElements= document.querySelectorAll('.hidden_text_slide');
hiddenElements.forEach((el)=>obsv.observe(el))
// hien text banner 3
const obsv2= new IntersectionObserver((entries)=>{
  entries.forEach((entry)=>{
      console.log(entry)
      if(entry.isIntersecting){
          entry.target.classList.add('show_text_slide_3');
      }else{
          entry.target.classList.remove('show_text_slide_3')
      }
  })
})
const hiddenElements2= document.querySelectorAll('.hidden_text_slide_3');
hiddenElements2.forEach((el)=>obsv2.observe(el))