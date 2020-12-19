let w=0,h=0,a=0,b=0;
let p1Hand=[], p2Hand=[], p3Hand=[], p4Hand=[];
let cardImageFiles = ["10C.png", "9C.png", "8C.png", "7C.png", "6C.png", "5C.png", "4C.png", "3C.png", "2C.png", "10S.png", "9S.png", "8S.png", "7S.png", "6S.png", "5S.png", "4S.png", "3S.png", "2S.png", "10D.png", "9D.png", "8D.png", "7D.png", "6D.png", "5D.png", "4D.png", "3D.png", "2D.png","10H.png", "9H.png", "8H.png", "7H.png", "6H.png", "5H.png", "4H.png", "3H.png", "2H.png","JH.png", "AH.png", "KH.png", "QH.png", "JS.png", "AS.png", "KS.png", "QS.png", "JD.png", "AD.png", "KD.png", "QD.png", "JC.png", "AC.png", "KC.png", "QC.png"];
let cards = [];

function fixSize() {
    w = window.innerWidth;
    h = window.innerHeight;
    const canvas = document.getElementById("game2");
    canvas.width = w;
    canvas.height = h;
}

function shuffle(array){
    let currentIndex = array.length, temporaryValue, randomIndex;

    while (0 !== currentIndex){
        randomIndex = Math.floor(Math.random()*currentIndex);
        currentIndex -= 1;

        temporaryValue=array[currentIndex];
        array[currentIndex] = array[randomIndex];
        array[randomIndex] = temporaryValue;
    }
    return array;
}

let temp ;

function giveHands(){
    let i;
    let array=[];
    array=shuffle(cards);
    for(i=0; i<13; i++){
        p1Hand[i]=array[i];
        temp = i;
        p2Hand[i]=array[13+i];
        p3Hand[i]=array[26+i];
        p4Hand[i]=array[39+i]
    }

    /*setInterval(function () {
        temp++;
        p1Hand.push(array[temp]);
    }, 1000);*/

}

let loadCardImages = new Promise(function(resolve) {

    let loadedImageCount = 0;

    let loadCheck = function() {
        loadedImageCount++;
        if (loadedImageCount === cardImageFiles.length) {
            console.log("Success!");
            resolve();
        }
    };

    for (let c of cardImageFiles) {
        let img = new Image();
        img.src = "/client/img/" +  + c;
        console.log("Loading " + c);
        img.onload = () => loadCheck();
        cards.push(img);
    }

});

function pageLoad(){

    window.addEventListener("resize", fixSize);
    fixSize();
    giveHands();

    loadCardImages.then(() => {
        window.requestAnimationFrame(redraw);
    });

}

function redraw(){
    const canvas = document.getElementById("game2");
    const context = canvas.getContext("2d");

    let cardScale = 1.0;
    if (w < 1920) {
        cardScale = 1 - (1920 - w) / 1920;
    }

    context.fillStyle = "#400101";
    context.fillRect(0, 0, w, h);

    let spacing1 = w / (p1Hand.length + 4);
    for(let i = 0; i < p1Hand.length; i++){
        context.drawImage(p1Hand[i], + (i+1.5)*spacing1, h-350*cardScale, 200*cardScale, 300*cardScale);
    }

    /*let spacing2 = w / (p2Hand.length + 4);
    for(let i = 0; i < p2Hand.length; i++){
        context.drawImage(p2Hand[i], + (i+1.5)*spacing2, 2*h/3-150*cardScale, 200*cardScale, 300*cardScale);
    }*/

    window.requestAnimationFrame(redraw);
}