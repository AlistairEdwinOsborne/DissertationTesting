/*
 * Constructs an action that the ai player could make
 * @param pos [Number]: the cell position the ai would make its action in
 * made that action
 */
var AIAction = function(pos) {

    // public : the position on the board that the action would put the letter on
    this.movePosition = pos;

    //public : the minimax value of the state that the action leads to when applied
    this.minimaxVal = 0;

    /*
     * public : applies the action to a state to get the next state
     * @param state [State]: the state to apply the action to
     * @return [State]: the next state
     */
    this.applyTo = function(state) {
        var next = new State(state);

        //put the letter on the board
        next.board[this.movePosition] = state.turn;

        if(state.turn === "O")
            next.oMovesCount++;

        next.advanceTurn();

        return next;
    }
};

/*
 * public static function that defines a rule for sorting AIActions in ascending manner
 * @param firstAction [AIAction] : the first action in a pairwise sort
 * @param secondAction [AIAction]: the second action in a pairwise sort
 * @return [Number]: -1, 1, or 0
 */
AIAction.ASCENDING = function(firstAction, secondAction) {
    if(firstAction.minimaxVal < secondAction.minimaxVal)
        return -1; //indicates that firstAction goes before secondAction
    else if(firstAction.minimaxVal > secondAction.minimaxVal)
        return 1; //indicates that secondAction goes before firstAction
    else
        return 0; //indicates a tie
}

/*
 * public static function that defines a rule for sorting AIActions in descending manner
 * @param firstAction [AIAction] : the first action in a pairwise sort
 * @param secondAction [AIAction]: the second action in a pairwise sort
 * @return [Number]: -1, 1, or 0
 */
AIAction.DESCENDING = function(firstAction, secondAction) {
    if(firstAction.minimaxVal > secondAction.minimaxVal)
        return -1; //indicates that firstAction goes before secondAction
    else if(firstAction.minimaxVal < secondAction.minimaxVal)
        return 1; //indicates that secondAction goes before firstAction
    else
        return 0; //indicates a tie
}


/*
 * Constructs an AI player with a specific level of intelligence
 * @param level [String]: the desired level of intelligence
 */
var AI = function(level) {

    //private attribute: level of intelligence the player has
    var levelOfIntelligence = level;

    //private attribute: the game the player is playing
    var game = {};

    /*
     * private recursive function that computes the minimax value of a game state
     * @param state [State] : the state to calculate its minimax value
     * @returns [Number]: the minimax value of the state
     */
    function minimaxValue(state) {
        if(state.isTerminal()) {
            //a terminal game state is the base case
            return Game.score(state);
        }
        else {
            var stateScore; // this stores the minimax value we'll compute

            if(state.turn === "X")
            // X wants to maximize --> initialize to a value smaller than any possible score
                stateScore = -1000;
            else
            // O wants to minimize --> initialize to a value larger than any possible score
                stateScore = 1000;

            var availablePositions = state.emptyCells();

            //enumerate next available states using the info form available positions
            var availableNextStates = availablePositions.map(function(pos) {
                var action = new AIAction(pos);

                var nextState = action.applyTo(state);

                return nextState;
            });

            /* calculate the minimax value for all available next states
             * and evaluate the current state's value */
            availableNextStates.forEach(function(nextState) {
                var nextScore = minimaxValue(nextState);
                if(state.turn === "X") {
                    // X wants to maximize --> update stateScore iff nextScore is larger
                    if(nextScore > stateScore)
                        stateScore = nextScore;
                }
                else {
                    // O wants to minimize --> update stateScore iff nextScore is smaller
                    if(nextScore < stateScore)
                        stateScore = nextScore;
                }
            });

            return stateScore;
        }
    }
    
    /* private function: allows the player 2 to make any move and abides by the same rules of non illegal * placing set by the Ai
    * @param turn [String]: the player to play, either X or O  
    */
    function takeAPlayerMove(turn){
  
            $(".cell").each(function() {
                var $this = $(this);
                $this.click(function() {
                    if(!$this.hasClass('occupied')) {
                       var indx = parseInt($this.data("indx"));
             
                        var plot = new State(game.currentState);
                        
                            plot.board[indx] = "O";
                            

                                //console.log(indx);
                        var action = new AIAction(indx);
                        var next = action.applyTo(game.currentState);
                            ui.insertAt(indx, "O");
                            game.advanceTo(next);
                            //console.log(next);
                            }
                    })
            });
          
    };

    
    function takeMenaceMove(turn){
        //var plot = new State(game.currentState.board);
        //plot.board; is the current state of the board
        //boardArray; 
       //beadIntArray; ["X","E","E","E","E","E","E","E","O"];
        //console.log(plot);
		var check = game.currentState.board;
		var compare = check.toString();
		var i;
		function rotateRight90(){
			var checker1 = ["E","E","E","E","E","E","E","E","E"];
			checker1.splice(0, 1,check[6]);
            checker1.splice(1, 1,check[3]);
            checker1.splice(2, 1,check[0]);
            checker1.splice(3, 1,check[7]);
            checker1.splice(4, 1,check[4]);
			checker1.splice(5, 1,check[1]);
            checker1.splice(6, 1,check[8]);
            checker1.splice(7, 1,check[5]);
			checker1.splice(8, 1,check[2]);
			return checker1;
		}
		
		function rotateLeft90(){
			var checker2 = ["E","E","E","E","E","E","E","E","E"];
			checker2.splice(0, 1,check[2]);
			checker2.splice(1, 1,check[5]);
            checker2.splice(2, 1,check[8]);
            checker2.splice(3, 1,check[1]);
            checker2.splice(4, 1,check[4]);
			checker2.splice(5, 1,check[7]);
            checker2.splice(6, 1,check[0]);
            checker2.splice(7, 1,check[3]);
            checker2.splice(8, 1,check[6]);
			return checker2;
		}
		
			function rotate180(){
			var checker3 = ["E","E","E","E","E","E","E","E","E"];
			checker3.splice(0, 1,check[8]);
            checker3.splice(1, 1,check[7]);
            checker3.splice(2, 1,check[6]);
            checker3.splice(3, 1,check[5]);
            checker3.splice(4, 1,check[4]);
            checker3.splice(5, 1,check[3]);
            checker3.splice(6, 1,check[2]);
            checker3.splice(7, 1,check[1]);
            checker3.splice(8, 1,check[0]);
			return checker3;
		}
		
			function reflectVertical(){
			var checker4 = ["E","E","E","E","E","E","E","E","E"];
			checker4.splice(0, 1,check[2]);
            checker4.splice(1, 1,check[1]);
            checker4.splice(2, 1,check[0]);
			checker4.splice(3, 1,check[5]);
            checker4.splice(4, 1,check[4]);
            checker4.splice(5, 1,check[3]);
			checker4.splice(6, 1,check[8]);
			checker4.splice(7, 1,check[7]);
			checker4.splice(8, 1,check[6]);
			return checker4;
		}
		
			function reflectHorizontal(){
			var checker5 = ["E","E","E","E","E","E","E","E","E"];
            checker5.splice(0, 1,check[6]);
            checker5.splice(1, 1,check[7]);
            checker5.splice(2, 1,check[8]);
            checker5.splice(3, 1,check[3]);
            checker5.splice(4, 1,check[4]);
            checker5.splice(5, 1,check[5]);
            checker5.splice(6, 1,check[0]);
			checker5.splice(7, 1,check[1]);
			checker5.splice(8, 1,check[2]);
			return checker5;
		}
		
			function rotate180andReflectHorizontal(){
			var checker6 = ["E","E","E","E","E","E","E","E","E"];		
			checker6.splice(0, 1,check[2]);
            checker6.splice(1, 1,check[1]);
            checker6.splice(2, 1,check[6]);
            checker6.splice(3, 1,check[5]);
            checker6.splice(4, 1,check[4]);
			checker6.splice(5, 1,check[3]);
            checker6.splice(6, 1,check[8]);
			checker6.splice(7, 1,check[7]);
            checker6.splice(8, 1,check[0]);
			return checker6;
		}
		
			function rotateLeft90andReflectHorizontal(){
			var checker7 = ["E","E","E","E","E","E","E","E","E"];
			checker7.splice(0, 1,check[8]);
            checker7.splice(1, 1,check[5]);
            checker7.splice(2, 1,check[2]);
            checker7.splice(3, 1,check[7]);
            checker7.splice(4, 1,check[4]);
            checker7.splice(5, 1,check[1]);
            checker7.splice(6, 1,check[6]);
            checker7.splice(7, 1,check[3]);
            checker7.splice(8, 1,check[0]);
			return checker7;
		}
		
			function rotateRight90andReflectVertical(){
			var checker8 = ["E","E","E","E","E","E","E","E","E"];
			checker8.splice(0, 1,check[0]);
            checker8.splice(1, 1,check[3]);
            checker8.splice(2, 1,check[6]);
			checker8.splice(3, 1,check[1]);
            checker8.splice(4, 1,check[4]);
            checker8.splice(5, 1,check[7]);
			checker8.splice(6, 1,check[2]);
			checker8.splice(7, 1,check[5]);			
			checker8.splice(8, 1,check[8]);			
			return checker8;
		}
        
        /*simple adder function
        * @Param [int, int]
        */
          function add(a, b){
            return a + b;
        }
        
        /*rotates the index back to the current game state form
        * @Param [int]
        */
        function reverseRight90(a){
                if( a === 2){
                    return 0;
                }
                else if( a === 5){
                    return 1;
                }
                else if (a === 8){
                    return 2;
                }
                else if( a === 1){
                    return 3;
                }
                else if( a === 4){
                    return 4;
                }
                else if( a === 7){
                    return 5;
                }
                else if( a === 0){
                    return 6;
                }
                else if( a === 3){
                    return 7;
                }
                else if( a === 6){
                    return 8;
                }

        }
        /*rotates the index back to the current game state form
        * @Param [int]
        */
          function reverseLeft90(a){
                if( a === 6){
                    return 0;
                }
                else if( a === 3){
                    return 1;
                }
                else if (a === 0){
                    return 2;
                }
                else if( a === 7){
                    return 3;
                }
                else if( a === 4){
                    return 4;
                }
                else if( a === 1){
                    return 5;
                }
                else if( a === 8){
                    return 6;
                }
                else if( a === 5){
                    return 7;
                }
                else if( a === 2){
                    return 8;
                }

        }
        
        /*rotates the index back to the current game state form
        * @Param [int]
        */
          function reverse180(a){
                if( a === 8){
                    return 0;
                }
                else if( a === 7){
                    return 1;
                }
                else if (a === 6){
                    return 2;
                }
                else if( a === 5){
                    return 3;
                }
                else if( a === 4){
                    return 4;
                }
                else if( a === 3){
                    return 5;
                }
                else if( a === 2){
                    return 6;
                }
                else if( a === 1){
                    return 7;
                }
                else if( a === 0){
                    return 8;
                }

        }
        
        /*rotates the index back to the current game state form
        * @Param [int]
        */
          function reverseReflectHorizontal(a){
                if( a === 6){
                    return 0;
                }
                else if( a === 7){
                    return 1;
                }
                else if (a === 8){
                    return 2;
                }
                else if( a === 3){
                    return 3;
                }
                else if( a === 4){
                    return 4;
                }
                else if( a === 5){
                    return 5;
                }
                else if( a === 0){
                    return 6;
                }
                else if( a === 1){
                    return 7;
                }
                else if( a === 2){
                    return 8;
                }

        }
        
         /*rotates the index back to the current game state form
        * @Param [int]
        */
          function reverseReflectVertical(a){
                if( a === 2){
                    return 0;
                }
                else if( a === 1){
                    return 1;
                }
                else if (a === 0){
                    return 2;
                }
                else if( a === 5){
                    return 3;
                }
                else if( a === 4){
                    return 4;
                }
                else if( a === 3){
                    return 5;
                }
                else if( a === 8){
                    return 6;
                }
                else if( a === 7){
                    return 7;
                }
                else if( a === 6){
                    return 8;
                }

        }
       /*rotates the index back to the current game state form
        * @Param [int]
        */
          function reverseLeft90andReflectHorizontal(a){
                if( a === 8){
                    return 0;
                }
                else if( a === 5){
                    return 1;
                }
                else if (a === 2){
                    return 2;
                }
                else if( a === 7){
                    return 3;
                }
                else if( a === 4){
                    return 4;
                }
                else if( a === 1){
                    return 5;
                }
                else if( a === 6){
                    return 6;
                }
                else if( a === 3){
                    return 7;
                }
                else if( a === 0){
                    return 8;
                }

        }
      /*rotates the index back to the current game state form
        * @Param [int]
        */
          function reverseRight90andReflectVertical(a){
                if( a === 0){
                    return 0;
                }
                else if( a === 3){
                    return 1;
                }
                else if (a === 6){
                    return 2;
                }
                else if( a === 1){
                    return 3;
                }
                else if( a === 4){
                    return 4;
                }
                else if( a === 7){
                    return 5;
                }
                else if( a === 2){
                    return 6;
                }
                else if( a === 5){
                    return 7;
                }
                else if( a === 8){
                    return 8;
                }

        }
            /*rotates the index back to the current game state form
        * @Param [int]
        */
          function reverse180andReflectHorizontal(a){
                if( a === 2){
                    return 0;
                }
                else if( a === 1){
                    return 1;
                }
                else if (a === 6){
                    return 2;
                }
                else if( a === 5){
                    return 3;
                }
                else if( a === 4){
                    return 4;
                }
                else if( a === 3){
                    return 5;
                }
                else if( a === 8){
                    return 6;
                }
                else if( a === 7){
                    return 7;
                }
                else if( a === 0){
                    return 8;
                }

        }
        
		console.log("=================ROTATIONS=============================");
		console.log("Original Input");
		console.log(compare);
		console.log("Input Rotated 90 Right");
		console.log(rotateRight90());
		console.log(rotateRight90().toString());
		console.log("Input Rotated 90 Left");
		console.log(rotateLeft90());
		console.log(rotateLeft90().toString());
		console.log("Input Rotated 180");
		console.log(rotate180());
		console.log(rotate180().toString());
		console.log("Input Reflected Horizontally");
		console.log(reflectHorizontal());
		console.log(reflectHorizontal().toString());
		console.log("Input Reflected Vertically");
		console.log(reflectVertical());
		console.log(reflectVertical().toString());
		console.log("Input Roated 90 Left then Refect Horizontal");
		console.log(rotateLeft90andReflectHorizontal());
		console.log(rotateLeft90andReflectHorizontal().toString());
		console.log("Input Roated 90 Right then Reflect Vertical");
		console.log(rotateRight90andReflectVertical());
		console.log(rotateRight90andReflectVertical().toString());
		console.log("Input Roate 180 and Reflect Horizontal");
		console.log(rotate180andReflectHorizontal());
		console.log(rotate180andReflectHorizontal().toString());
		console.log("=================ROTATIONS=============================");
        
        
      
        var next;
        
		for(i = 0; i < boardArray.length; i++){
            
            //if its the same as a state in the array
			if (compare === boardArray[i].toString()){
                //console.log(i);
                var testing =  beadIntArray[i].reduce(add, 0);
                //console.log(testing);
                var cumulativeProbability = 0;
                var p = Math.floor(Math.random() * testing) + 1;
                //console.log(p);
                for(var j = 0; j < beadIntArray[i].length; j++){
                    cumulativeProbability += beadIntArray[i][j];
                        if(p <= cumulativeProbability && beadIntArray[i][j] != 0){
                            console.log(j);
                        break;
                        //console.log("its met");
                    }
                }
                var action = new AIAction(j);

                next = action.applyTo(game.currentState);
            
                ui.insertAt(j, turn);
            

				break;
			}
            
            //if its the same as a state in the array rotated 90 Right
			else if (rotateRight90().toString() === boardArray[i].toString()){
                //console.log(i);
               var testing =  beadIntArray[i].reduce(add, 0);
                //console.log(testing);
                var cumulativeProbability = 0;
                var p = Math.floor(Math.random() * testing) + 1;
                //console.log(p);
                for(var j = 0; j < beadIntArray[i].length; j++){
                    cumulativeProbability += beadIntArray[i][j];
                        if(p <= cumulativeProbability && beadIntArray[i][j] != 0){
                            console.log(j);
                        break;
                      
                    }
                }
                var boardPlace = reverseRight90(j);
                var action = new AIAction(boardPlace);
                next = action.applyTo(game.currentState);
                ui.insertAt(boardPlace, turn);
                console.log("rotateRight90");
				break;
				
			}
            
            
             //if its the same as a state in the array rotated 90 Left
			else if (rotateLeft90().toString() === boardArray[i].toString()){
				//console.log(i);
                var testing =  beadIntArray[i].reduce(add, 0);
                //console.log(testing);
                var cumulativeProbability = 0;
                var p = Math.floor(Math.random() * testing) + 1;
                //console.log(p);
                for(var j = 0; j < beadIntArray[i].length; j++){
                    cumulativeProbability += beadIntArray[i][j];
                        if(p <= cumulativeProbability && beadIntArray[i][j] != 0){
                            console.log(j);
                        break;
                      
                    }
                }
                var boardPlace = reverseLeft90(j);
                var action = new AIAction(boardPlace);
                next = action.applyTo(game.currentState);
                ui.insertAt(boardPlace, turn);
				console.log("rotateLeft90");
				break;
			}
            
            
            //if its the same as a state in the array rotated 180
			else if (rotate180().toString() === boardArray[i].toString()){
					//console.log(i);
                var testing =  beadIntArray[i].reduce(add, 0);
                //console.log(testing);
                var cumulativeProbability = 0;
                var p = Math.floor(Math.random() * testing) + 1;
                //console.log(p);
                for(var j = 0; j < beadIntArray[i].length; j++){
                    cumulativeProbability += beadIntArray[i][j];
                        if(p <= cumulativeProbability && beadIntArray[i][j] != 0){
                            console.log(j);
                        break;
                      
                    }
                }
                var boardPlace = reverse180(j);
                var action = new AIAction(boardPlace);
                next = action.applyTo(game.currentState);
                ui.insertAt(boardPlace, turn);
				console.log("rotate180");
				break;
			}
            
            
            //if its the same as a state in the array reflected horizontally
			else if (reflectHorizontal().toString() === boardArray[i].toString()){
						//console.log(i);
                var testing =  beadIntArray[i].reduce(add, 0);
                //console.log(testing);
                var cumulativeProbability = 0;
                var p = Math.floor(Math.random() * testing) + 1;
                //console.log(p);
                for(var j = 0; j < beadIntArray[i].length; j++){
                    cumulativeProbability += beadIntArray[i][j];
                        if(p <= cumulativeProbability && beadIntArray[i][j] != 0){
                            console.log(j);
                        break;
                      
                    }
                }
                var boardPlace = reverseReflectHorizontal(j);
                var action = new AIAction(boardPlace);
                next = action.applyTo(game.currentState);
                ui.insertAt(boardPlace, turn);
				console.log("reflectHorizontal");
				break;
			}
            
            
            //if its the same as a state in the array reflected vertically
			else if (reflectVertical().toString() === boardArray[i].toString()){
							//console.log(i);
                var testing =  beadIntArray[i].reduce(add, 0);
                //console.log(testing);
                var cumulativeProbability = 0;
                var p = Math.floor(Math.random() * testing) + 1;
                //console.log(p);
                for(var j = 0; j < beadIntArray[i].length; j++){
                    cumulativeProbability += beadIntArray[i][j];
                        if(p <= cumulativeProbability && beadIntArray[i][j] != 0){
                            console.log(j);
                        break;
                      
                    }
                }
                var boardPlace = reverseReflectVertical(j);
                var action = new AIAction(boardPlace);
                next = action.applyTo(game.currentState);
                ui.insertAt(boardPlace, turn);
				console.log("reflectVertical");
				break;
			}
            
            
            //if its the same as a state in the array rotated left 90 and reflected horizontally
			else if (rotateLeft90andReflectHorizontal().toString() === boardArray[i].toString()){
							//console.log(i);
                var testing =  beadIntArray[i].reduce(add, 0);
                //console.log(testing);
                var cumulativeProbability = 0;
                var p = Math.floor(Math.random() * testing) + 1;
                //console.log(p);
                for(var j = 0; j < beadIntArray[i].length; j++){
                    cumulativeProbability += beadIntArray[i][j];
                        if(p <= cumulativeProbability && beadIntArray[i][j] != 0){
                            console.log(j);
                        break;
                      
                    }
                }
                var boardPlace = reverseLeft90andReflectHorizontal(j);
                var action = new AIAction(boardPlace);
                next = action.applyTo(game.currentState);
                ui.insertAt(boardPlace, turn);
				console.log("rotateLeft90andReflectHorizontal");
				break;
			}
            
            
            //if its the same as a state in the array rotated right 90 and reflected vertically
			else if (rotateRight90andReflectVertical().toString() === boardArray[i].toString()){
							//console.log(i);
                var testing =  beadIntArray[i].reduce(add, 0);
                //console.log(testing);
                var cumulativeProbability = 0;
                var p = Math.floor(Math.random() * testing) + 1;
                //console.log(p);
                for(var j = 0; j < beadIntArray[i].length; j++){
                    cumulativeProbability += beadIntArray[i][j];
                        if(p <= cumulativeProbability && beadIntArray[i][j] != 0){
                            console.log(j);
                        break;
                      
                    }
                }
                var boardPlace = reverseRight90andReflectVertical(j);
                var action = new AIAction(boardPlace);
                next = action.applyTo(game.currentState);
                ui.insertAt(boardPlace, turn);
				console.log("rotateRight90andReflectVertical");
				break;
			}
            
            
            //if its the same as a state in the array rotated 180 and reflected horizontally    
			else if (rotate180andReflectHorizontal().toString() === boardArray[i].toString()){
							//console.log(i);
                var testing =  beadIntArray[i].reduce(add, 0);
                //console.log(testing);
                var cumulativeProbability = 0;
                var p = Math.floor(Math.random() * testing) + 1;
                //console.log(p);
                for(var j = 0; j < beadIntArray[i].length; j++){
                    cumulativeProbability += beadIntArray[i][j];
                        if(p <= cumulativeProbability && beadIntArray[i][j] != 0){
                            console.log(j);
                        break;
                      
                    }
                }
                var boardPlace = reverse180andReflectHorizontal(j);
                var action = new AIAction(boardPlace);
                next = action.applyTo(game.currentState);
                ui.insertAt(boardPlace, turn);
				console.log("rotate180andReflectHorizontal");
				break;
			}
		}
		console.log(i);
        
        game.advanceTo(next);
		//rotation courtesy of PHS Learning Winning Strategies in Games 200/5 Chapter 3 Page 17-10
    };
    
      /*
     * private function: make the ai player take a blind move
     * that is: choose the cell to place its symbol randomly
     * @param turn [String]: the player to play, either X or O
     */
        function takeABlindMove(turn) {
        var available = game.currentState.emptyCells();
        var randomCell = available[Math.floor(Math.random() * available.length)];
        var action = new AIAction(randomCell);

        var next = action.applyTo(game.currentState);
            
        ui.insertAt(randomCell, turn);
            
        game.advanceTo(next);
    }


  /*
     * private function: make the ai player take a master move,
     * that is: choose the optimal minimax decision
     * @param turn [String]: the player to play, either X or O
     */
    function takeAMasterMove(turn) {
        var available = game.currentState.emptyCells();
        
        console.log(game.currentState.board.toString());
        //enumerate and calculate the score for each avaialable actions to the ai player
        var availableActions = available.map(function(pos) {
            var action =  new AIAction(pos); //create the action object
            var next = action.applyTo(game.currentState); //get next state by applying the action

            action.minimaxVal = minimaxValue(next); //calculate and set the action's minmax value

            return action;
        });

        //sort the enumerated actions list by score
        if(turn === "X")
        //X maximizes --> sort the actions in a descending manner to have the action with maximum minimax at first
            availableActions.sort(AIAction.DESCENDING);
        else
        //O minimizes --> sort the actions in an ascending manner to have the action with minimum minimax at first
            availableActions.sort(AIAction.ASCENDING);


        //take the first action as it's the optimal
        var chosenAction = availableActions[0];
        var next = chosenAction.applyTo(game.currentState);

        ui.insertAt(chosenAction.movePosition, turn);
        console.log(next);    
        game.advanceTo(next);
    }


    /*
     * public method to specify the game the ai player will play
     * @param _game [Game] : the game the ai will play
     */
    this.plays = function(_game){
        game = _game;
    };

    /*
     * public function: notify the ai player that it's its turn
     * @param turn [String]: the player to play, either X or O
     */     
    this.notify = function(turn) {
        switch(levelOfIntelligence) {
            //invoke the desired behavior based on the level chosen
            case "master": takeAMasterMove(turn); break;
            case "blind": takeABlindMove(turn); break;
            case "human2" : takeAPlayerMove(turn); break;
            case "menace" : takeMenaceMove(turn); break;
        }
    };
};

/*
* https://github.com/Mostafa-Samir/Tic-Tac-Toe-AI As a basis for using the minimax algorithm for the 
* perfect player
*/