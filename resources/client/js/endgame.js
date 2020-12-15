let w=0,h=0;
let imageFiles = ["StackedDecksLogo.JPG"];
let images = [];
function fixSize(){
    w = window.innerWidth;
    h = window.innerHeight;
    const canvas = document.getElementById("endgame" +
        "");
    canvas.width = w;
    canvas.height = h;
}

let loadImages = new Promise(function(resolve) {
    let loadedImageCount = 0;

    let loadCheck = function () {
        loadedImageCount++;
        if (loadedImageCount === imageFiles.length) {
            console.log("Success!");
            resolve();
        }
    };

    for (let i of imageFiles) {
        let img = new Image();
        img.src = i;
        console.log("Loading" + i);
        img.onload = () => loadCheck();
        images.push(img)
    }
});

function pageLoad(){
    window.addEventListener("resize", fixSize);
    fixSize();

    loadImages.then(()=> {
        window.requestAnimationFrame(redraw);
    });
}

function redraw() {
    const canvas = document.getElementById("endgame");
    const context = canvas.getContext("2d");

    context.fillStyle = "#400101";
    context.fillRect(0, 0, w, h);
    context.fillStyle = "#ffffff";
    context.fillRect(20, 0, w - 60, h - 20);
    context.drawImage(images[0], 40, 0);

    window.requestAnimationFrame(redraw);
}