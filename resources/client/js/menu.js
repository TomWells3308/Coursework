let w=0,h=0;
let imageFiles = ["StackedDecksLogo.JPG", "FriendsButton.png", "LeaderboardButton.png", "SettingsButton.png", "LoginSignUpButton.png", "PokerButton.png", "OldMaidButton.png", "MafiaButton.png", "BlankButton.png"];
let images = [];
let mousePosition = {x: 0, y: 0}, leftMouseDown = false, rightMouseDown = false;
let logoSizePosition = {x: 20, y:0, h:123, w:424};
let friendsSizePosition = {x: 570, y:40, h:48, w:160};
let leaderboardSizePosition = {x: 850, y:40, h:48, w:271};
let settingsSizePosition = {x: 1240, y:40, h:52, w:180};
let loginSizePosition = {x: 2200, y: 30, h: 72, w: 245};
let pokerSizePosition = {x:101, y:160, h:82, w:262};
let oldMaidSizePosition = {x:101, y:280, h:82, w:262};
let mafiaSizePosition = {x:101, y:400, h:82, w:262};

function fixSize(){
    w = window.innerWidth;
    h = window.innerHeight;
    const canvas = document.getElementById("menu");
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

    const canvas = document.getElementById('menu')

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
    const canvas = document.getElementById("menu");
    const context = canvas.getContext("2d");

    context.fillStyle = "#400101";
    context.fillRect(0, 0, w, h);
    context.fillStyle = "#ffffff";
    context.fillRect(20, 0, w-60, h-20);
    context.drawImage(images[0], logoSizePosition.x, logoSizePosition.y);
    context.drawImage(images[1], friendsSizePosition.x, friendsSizePosition.y);
    context.drawImage(images[2], leaderboardSizePosition.x, leaderboardSizePosition.y);
    context.drawImage(images[3], settingsSizePosition.x, settingsSizePosition.y);
    context.drawImage(images[4], w-310, loginSizePosition.y);
    context.drawImage(images[5], pokerSizePosition.x, pokerSizePosition.y);
    context.drawImage(images[6], oldMaidSizePosition.x, oldMaidSizePosition.y);
    context.drawImage(images[7], mafiaSizePosition.x, mafiaSizePosition.y);
    context.drawImage(images[8], mafiaSizePosition.x, (mafiaSizePosition.y+120));
    context.drawImage(images[8], mafiaSizePosition.x, (mafiaSizePosition.y+240));
    context.drawImage(images[8], mafiaSizePosition.x, (mafiaSizePosition.y+360));
    if((leftMouseDown) && (mousePosition.x <= (logoSizePosition.x + logoSizePosition.w)) && (mousePosition.x >= logoSizePosition.x) && (mousePosition.y <= logoSizePosition.h)){
        window.location.replace("http://localhost:63342/Coursework/Coursework-master/src/main/java/server/menu.html");
    }
    if((leftMouseDown) && (mousePosition.x <= (friendsSizePosition.x + friendsSizePosition.w)) && (mousePosition.x >= friendsSizePosition.x) && (mousePosition.y <= (friendsSizePosition.h + friendsSizePosition.y)) && (mousePosition.y >= friendsSizePosition.y)){
        window.location.replace("http://localhost:63342/Coursework/Coursework-master/src/main/java/server/friends.html");
    }
    if((leftMouseDown) && (mousePosition.x <= (leaderboardSizePosition.x + leaderboardSizePosition.w)) && (mousePosition.x >= leaderboardSizePosition.x) && (mousePosition.y <= (leaderboardSizePosition.h + leaderboardSizePosition.y)) && (mousePosition.y >= leaderboardSizePosition.y)){
        window.location.replace("http://localhost:63342/Coursework/Coursework-master/src/main/java/server/leaderboard.html");
    }
    if((leftMouseDown) && (mousePosition.x <= (settingsSizePosition.x + settingsSizePosition.w)) && (mousePosition.x >= settingsSizePosition.x) && (mousePosition.y <= (settingsSizePosition.h + settingsSizePosition.y)) && (mousePosition.y >= settingsSizePosition.y)){
        window.location.replace("http://localhost:63342/Coursework/Coursework-master/src/main/java/server/settings.html");
    }
    if((leftMouseDown) && (mousePosition.x <= (w-310 + loginSizePosition.w)) && (mousePosition.x >= w-310) && (mousePosition.y <= (loginSizePosition.h + loginSizePosition.y)) && (mousePosition.y >= loginSizePosition.y)){
        window.location.replace("http://localhost:63342/Coursework/Coursework-master/src/main/java/server/login.html");
    }
    if((leftMouseDown) && (mousePosition.x <= (pokerSizePosition.x + pokerSizePosition.w)) && (mousePosition.x >= pokerSizePosition.x) && (mousePosition.y <= (pokerSizePosition.h + pokerSizePosition.y)) && (mousePosition.y >= pokerSizePosition.y)){
        window.location.replace("http://localhost:63342/Coursework/Coursework-master/src/main/java/server/game1.html");
    }
    if((leftMouseDown) && (mousePosition.x <= (oldMaidSizePosition.x + oldMaidSizePosition.w)) && (mousePosition.x >= oldMaidSizePosition.x) && (mousePosition.y <= (oldMaidSizePosition.h + oldMaidSizePosition.y)) && (mousePosition.y >= oldMaidSizePosition.y)){
        window.location.replace("http://localhost:63342/Coursework/Coursework-master/src/main/java/server/game2.html");
    }
    if((leftMouseDown) && (mousePosition.x <= (mafiaSizePosition.x + mafiaSizePosition.w)) && (mousePosition.x >= mafiaSizePosition.x) && (mousePosition.y <= (mafiaSizePosition.h + mafiaSizePosition.y)) && (mousePosition.y >= mafiaSizePosition.y)){
        window.location.replace("http://localhost:63342/Coursework/Coursework-master/src/main/java/server/game3.html");
    }
    window.requestAnimationFrame(redraw);
}