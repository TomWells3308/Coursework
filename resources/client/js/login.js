let w=0,h=0;
let imageFiles = ["StackedDecksLogo.JPG"];
let images = [];
let mousePosition = {x: 0, y: 0}, leftMouseDown = false, rightMouseDown = false;
let logoSizePosition = {x: 20, y:0, h:123, w:424};
function fixSize(){
    w = window.innerWidth;
    h = window.innerHeight;
    const canvas = document.getElementById("login");
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

    const canvas = document.getElementById('login')

    canvas.addEventListener('mousemove', event => {
        mousePosition.x = event.clientX;
        mousePosition.y = event.clientY;
    }, false);

    canvas.addEventListener('mousedown', event => {
        if (event.button === 0) {
            leftMouseDown = true;
        } else {
            rightMouseDown = true;
        }
    }, false);

    canvas.addEventListener('mouseup', event => {
        if (event.button === 0) {
            leftMouseDown = false;
        } else {
            rightMouseDown = false;
        }
    }, false);
}

function redraw(){
    const canvas = document.getElementById("login");
    const context = canvas.getContext("2d");

    context.fillStyle = "#400101";
    context.fillRect(0, 0, w, h);
    context.fillStyle = "#ffffff";
    context.fillRect(20, 0, w-60, h-20);
    context.drawImage(images[0], logoSizePosition.x, logoSizePosition.y);
    if((leftMouseDown) && (mousePosition.x <= (logoSizePosition.x + logoSizePosition.w)) && (mousePosition.x >= logoSizePosition.x) && (mousePosition.y <= logoSizePosition.h)){
        window.location.replace("http://localhost:63342/Coursework/Coursework-master/src/main/java/server/menu.html");
    }
    window.requestAnimationFrame(redraw);
}

function postUserLogin(){
    console.log("Invoked postUserLogin() ");

    let url = "/user/login";
    let formData = new FormData(document.getElementById('loginForm'));

    fetch(url, {
        method: "POST",
        body: formData,
    }).then(response => {
        return response.json();                 //now return that promise to JSON
    }).then(response => {
        if (response.hasOwnProperty("Error")) {
            alert(JSON.stringify(response));        // if it does, convert JSON object to string and alert
        } else {
            Cookies.set("token", response.token);
            Cookies.set("username", response.username);
            window.open("index.html", "_self");       //open index.html in same tab
        }
    });

}
