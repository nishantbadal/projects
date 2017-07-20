README
Galaga - Endless Wave Mode

Description:
A version of Galaga with endless waves of enemies.

Controls:
    Main Menu:
        Space - Select
        Up Arrow - Up
        Down Arrow - Down
    
    In Game:
        Space - Shoot
        Left Arrow - Left
        Right Arrow - Right

Basic features:
-Ship can move left to right
-Displays lives
-Displays score
-Has enemies move in formation across screen
-Fighters come down and attack player
-If fighter collides with player, it kills player
-Ship fires bullets
-Game increases in difficulty with every wave, enemies wait less and less to attack
-Score is saved after player dies
-High scores are loaded when player launches game, top 10 scores, reads/writes to file
-Sprites represent ship and enemies
-MVC model implemented

Additional features:
-Fighters come in at trajectories all at once
-Bullets are fired at 3 angles by every enemy
-Explosion animation for enemies and player
-Player can fire 5 bullets at a time, not just 2. Bullets also will kill multiple enemies.
-Fighters will change sprites based on the point they are traveling to and travel at different angles
-Game is endless, player plays until they die, new waves will constantly spawn
-After a fighter has broken out of formation to attack player and misses, it will continue to target player and fly in from the top again and again
-Moving space background

Note:
-Game takes a few seconds to load when first launched
-You can select items from the main menu using the up and down keys and the space bar
-When viewing the high score page, you can push space bar to return to the main menu
-After a wave of enemies is beaten, there is a bit of lag as the next wave is created (using OpenGL would help with this)
