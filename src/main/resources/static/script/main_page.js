const slideshow = document.querySelector('.slideshow');
const slides = slideshow.querySelectorAll('img');
const slideCount = slides.length;
let currentSlide = 0;

function nextSlide() {
    // убираем класс active у текущего слайда
    slides[currentSlide].classList.remove('active');
    // добавляем класс previous к текущему слайду
    slides[currentSlide].classList.add('previous');

    // устанавливаем следующий слайд
    currentSlide = (currentSlide + 1) % slideCount;

    // если это первый слайд после показа последнего, то убираем класс previous у всех слайдов
    if (currentSlide === 0) {
      slides.forEach(slide => slide.classList.remove('previous'));
    }
    slides[currentSlide].classList.add('active');
    // добавляем класс active к следующему слайду

  }
// устанавливаем первый слайд активным
slides[currentSlide].classList.add('active');

// запускаем переключение слайдов каждые 3 секунды
setInterval(nextSlide, 4000);