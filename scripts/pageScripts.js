var reader;
stateArray = [];
tempBoardArray = [];
boardArray = [];
tempBeadArray = [];
beadArray = [];
beadIntArray= [];
function myFunction() {
    var x = document.getElementById("myTopnav");
    if (x.className === "topnav") {
        x.className += " responsive";
    } else {
        x.className = "topnav";
    }
}

function checkFileAPI(){
    if (window.File && window.FileReader && window.FileList && window.Blob){
        reader = new FileReader();
        return true;
    }
    else {
        alert('The File APIs arnt supported by this browser');
        return false;
    }
}

function dataRead(){
        //grabs the file to read in and splits each state into a new line and adds to statearray
        $.get('first_player_menace.txt', function(data){
                stateArray = data.split(/\n/);
            //splits each line in two then adds them to two seperate arrays
    for (var ix = 0; ix < stateArray.length; ix++){
                
        stateArray[ix] = stateArray[ix].split("~");
        
        tempBoardArray.push(stateArray[ix][0]);
        
        tempBeadArray.push(stateArray[ix][1]);
 
                }
            //splits all the board positions into seperate elements and creates a 2d array of this
        for (var j = 0; j < tempBoardArray.length; j++){
        
                boardArray[j] = tempBoardArray[j].split(",");
            }
            //console.log(boardArray);
            
     //splits each string up in an individual element (removes the ,)       
    for (var iy = 0; iy < tempBeadArray.length; iy++){
           beadArray[iy] = tempBeadArray[iy].split(",");
                }
    //converts all the strings into ints to be used when adding or taking away beads        
    for(var k = 0; k < beadArray.length; k++){
        beadIntArray[k] = []
        for(var l = 0; l< beadArray[k].length; l++){
            beadIntArray[k][l] = parseInt(beadArray[k][l], 10);
        }
    }
           //console.log(beadIntArray);
            }); 
            
        }



/*
1[2E3]4[5E6]7[8E9]10
11[12E13]14[15E16]17[18E19]20
21[22E23]24[25E26]27[28E29]30
*/


/*
* Allows a file to be read into the page either to a div or array elements for further use
* @param filePath [filePath]: where the file is to be read in
*/
