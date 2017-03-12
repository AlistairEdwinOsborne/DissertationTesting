/* A State within the game
*/
var State = function(old) {
/* the player who's turn it currently is
                */
    this.turn = "";
 /* the number of moves the AI have taken
                */
    this.oMovesCount = 0;
                /* the current state of the game
                */
    this.result = "ongoing";

    /*
     * public : the board configuration in this state
     */
    this.board = [];

    /* Begin Object Construction */
    if(typeof old !== "undefined") {
        // if the state is constructed using a copy of another state
        var len = old.board.length;
        this.board = new Array(len);
        for(var itr = 0 ; itr < len ; itr++) {
            this.board[itr] = old.board[itr];
        }

        this.oMovesCount = old.oMovesCount;
        this.result = old.result;
        this.turn = old.turn;
    }
    /* End Object Construction */

    /*
     * public : advances the turn in a the state
     */
    this.advanceTurn = function() {
        this.turn = this.turn === "X" ? "O" : "X";
    }

    /*
     * public function that enumerates the empty cells in state
     * @return [Array]: indices of all empty cells
     */
    this.emptyCells = function() {
        var indexs = [];
        for(var itr = 0; itr < 9 ; itr++) {
            if(this.board[itr] === "E") {
                indexs.push(itr);
            }
        }
        return indexs;
    }

        
    /*this.resetCells = function(){
        var boardLength = this.board.length;
        for(var i = boardLength -1; i >= 0 ; i--){
            this.board.splice(i, "E");
            ui.insertAt(i, "");
        }
        
    }*/
        
  
    
    /*
     * public  function that checks if the state is a terminal state or not
     * the state result is updated to reflect the result of the game
     * @returns [Boolean]: true if it's terminal, false otherwise
     */

    this.isTerminal = function() {
        var B = this.board;
        //console.log(B);
        //check rows
        for(var i = 0; i <= 6; i = i + 3) {
            if(B[i] !== "E" && B[i] === B[i + 1] && B[i + 1] == B[i + 2]) {
                this.result = B[i] + "-winningstate"; //update the state result
                return true;
            }
        }

        //check columns
        for(var i = 0; i <= 2 ; i++) {
            if(B[i] !== "E" && B[i] === B[i + 3] && B[i + 3] === B[i + 6]) {
                this.result = B[i] + "-winningstate"; //update the state result
                return true;
            }
        }

        //check diagonals
        for(var i = 0, j = 4; i <= 2 ; i = i + 2, j = j - 2) {
            if(B[i] !== "E" && B[i] == B[i + j] && B[i + j] === B[i + 2*j]) {
                this.result = B[i] + "-winningstate"; //update the state result
                return true;
            }
        }

        var available = this.emptyCells();
        if(available.length == 0) {
            //the game is draw
            this.result = "drawingstate"; //update the state result
            return true;
        }
        else {
            return false;
        }    
    };

};



/*
 * Constructs a game object to be played
 * @param autoPlayer [AIPlayer] : the AI player to be play the game with
 */
var Game = function(autoPlayer) {

    //public : initialize the ai player for this game
    //this.aiX = autoPlayer1;
    this.ai = autoPlayer

    // public : initialize the game current state to empty board configuration
    this.currentState = new State();

    //"E" stands for empty board cell
    this.currentState.board = ["E", "E", "E",
                               "E", "E", "E",
                               "E", "E", "E"];

    this.currentState.turn = "X"; //X plays first

    /*
     * initialize game status to beginning
     */
    this.status = "beginning";
    
  /*checks to see if the current state of the board matches a state within the array
    * @param board [Array]: the current state of the board as an array of strings
    */
    this.compareMoves = function(board){
        
          var a = board.toString();
            
            for(var i = 0; i < boardArray.length; i++){
                var b = boardArray[i].toString();
                    if(a===b){
                        return i;
                    }
                }
            }

    /*
     * public function that advances the game to a new state
     * @param _state [State]: the new state to advance the game to
     */
    this.advanceTo = function(_state) {
        this.currentState = _state;
        if(_state.isTerminal()) {
            
            this.status = "ended";
             //return onendCall(this.currentState);
            if(_state.result === "X-winningstate"){
                //X won 
                ui.switchViewTo("won");
                }
            else if(_state.result === "O-winningstate"){
                //X lost         
                ui.switchViewTo("lost");
                }
            else{
                //it's a draw
                ui.switchViewTo("drawingstate");
                }
        }
        else {
            //the game is ongoing

            if(this.currentState.turn === "X") {
                ui.switchViewTo("human");
                //this.aiO.notify("X");
            }
            else {
                ui.switchViewTo("robot");

                //notify the AI player its turn has come up
                this.ai.notify("O");
            }
        }
    };
    


        

    /*
     * starts the game
     */
    
    this.start = function() {
        console.log(boardArray);  
        console.log(beadIntArray);    
        if(this.status = "beginning") {
            //invoke advanceTo with the initial state
            this.advanceTo(this.currentState);
            this.status = "running";
        }
    }
    
            /*
     * specify a callback when the game ends
     */
    this.onEnd = function(func) {
        onendCall = func;
    }


};
/*
 * public static function that calculates the score of the x player in a given terminal state
 * @param _state [State]: the state in which the score is calculated
 * @return [Number]: the score calculated for the human player
 */
Game.score = function(_state) {
   if(_state.result !== "still running") {
    if(_state.result === "X-winningstate"){
        // the x player won
        return 10 - _state.oMovesCount;
    }
    else if(_state.result === "O-winningstate") {
        //the x player lost
        return - 10 + _state.oMovesCount;
    }
    else {
        //it's a draw
        return 0;
    }
}
}
