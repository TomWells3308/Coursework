let w=0,h=0,a=0,b=0;
let p1Hand=[], p2Hand=[], p3Hand=[], p4Hand=[];
let cards = ["10C.png", "9C.png", "8C.png", "7C.png", "6C.png", "5C.png", "4C.png", "3C.png", "2C.png", "10S.png", "9S.png", "8S.png", "7S.png", "6S.png", "5S.png", "4S.png", "3S.png", "2S.png", "10D.png", "9D.png", "8D.png", "7D.png", "6D.png", "5D.png", "4D.png", "3D.png", "2D.png","10H.png", "9H.png", "8H.png", "7H.png", "6H.png", "5H.png", "4H.png", "3H.png", "2H.png","JH.png", "AH.png", "KH.png", "QH.png", "JS.png", "AS.png", "KS.png", "QS.png", "JD.png", "AD.png", "KD.png", "QD.png", "JC.png", "AC.png", "KC.png", "QC.png"];
const cardImage = new Image();

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

function giveHands(){
    let i;
    let array=[];
    array=shuffle(cards);
    for(i=0; i<13; i++){
        p1Hand[i]=array[i];
        p2Hand[i]=array[13+i];
        p3Hand[i]=array[26+i];
        p4Hand[i]=array[39+i]
    }
}


function pageLoad(){

    window.addEventListener("resize", fixSize);
    fixSize();
    giveHands();
    cardImage.src = "AS.png";
    cardImage.onload = () => window.requestAnimationFrame(redraw);
}

function redraw(){
    const canvas = document.getElementById("game2");
    const context = canvas.getContext("2d");
    context.fillStyle = "#400101";
    context.fillRect(0, 0, w, h);
    let i = 1;
    for(let c of p1Hand){
        cardImage.src= c;
        context.drawImage(cardImage, (w/2)-(50*i), h-350, 200, 300, );
        i++;
    }
    //cardImage.src= p1Hand[0];
    //context.drawImage(cardImage, (w/2)-100, h-350, 200, 300, );
    window.requestAnimationFrame(redraw)
}