# fruit-loop

## CLI

- Sending via input {"board": {"width":10, "height": 10}} should create a new game.
  Should print in the output {"state": "success", violations: []} if success or {"state": "failed", violations ["invalid-width"]} if any error.

- Sending via input {"player": {"move": ":movement"}} (up, down, right ,left) should move the player 1 block
  Should print in the output {"state": "success", violations: []} if success or {"state": "failed", violations ["invalid-move", "invalid-position"]} if any error.

## REST

- POST /game - Should create a new game. Request body should be a json: {"width":10, "height": 10}. 
It should also create a fruit in a random location of the board.

- DELETE /game - Should reset/delete the game state 

- POST /player/move/:movement  (up, down, right ,left) - Should move the player 1 block

- GET /game/state - return the current state of the game + score:

json
{
  "score": 2,
  "player": {
    "x": 3
    "y": 7
  },
  "fruit": {
    "x": 2
    "y": 3
  }
}
- GET /game/display - Display the current state of the game + score as text/plain:

Score: 2
------------------------
|                   x   |
|                       |
|                       |
|                       |
|                       |
|     O                 |
|                       |
-------------------------
## Rules:
- If player next move is a invalid one, return a 400
- If Player next move is a fruit, the score should increment 1 and the game should generate a next fruit in a random location
- If score is 3, /game/display should return "Won!"# fruit-loop

## CLI

- Sending via input {"board": {"width":10, "height": 10}} should create a new game.
  Should print in the output the state of the game after this action in json.

- Sending via input {"player": {"move": ":movement"}} (up, down, right ,left) should move the player 1 block
  Should print in the output the state of the game after this action in json.

## REST

- POST /game - Should create a new game. Request body should be a json: {"width":10, "height": 10}. 
It should also create a fruit in a random location of the board.

- DELETE /game - Should reset/delete the game state 

- POST /player/move/:movement  (up, down, right ,left) - Should move the player 1 block

- GET /game/state - return the current state of the game + score:

json
{
  "score": 2,
  "player": {
    "x": 3
    "y": 7
  },
  "fruit": {
    "x": 2
    "y": 3
  }
}
- GET /game/display - Display the current state of the game + score as text/plain:

Score: 2
------------------------
|                   x   |
|                       |
|                       |
|                       |
|                       |
|     O                 |
|                       |
-------------------------
## Rules:
- If player next move is a invalid one, return a 400
- If Player next move is a fruit, the score should increment 1 and the game should generate a next fruit in a random location
- If score is 3, /game/display should return "Won!"