let w=0,h=0;

const backgroundImage = new Image();
const pressedKeys = {};
const mousePosition = {x: 0,y: 0};

let mouseClicked = false;

let loadBackground = new Promise(function (resolve) {
    backgroundImage.src = "background.jpg";
    backgroundImage.onload = () => resolve();
});

function fixSize() {
    w = window.innerWidth;
    h= window.innerHeight;
    const canvas = document.getElementById("game2")
    canvas.width = w;
    canvas.height = h;
}

function pageLoad(){
    window.addEventListener("resize", fixSize);
    fixSize();

    const canvas = document.getElementsById("game2");
    canvas.addEventListener("mousemove", event => {
        mousePosition.x = event.clientX;
        mousePosition.y = event.clientY;
    }, false);

    canvas.addEventListener("click", event => {
        mouseClicked = true;
    }, false);
}