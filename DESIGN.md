design
======

###High Level Design Goals of the Project

My design goals were to create a game where the hero fights different types of mobster enemies while dodging obstacles 
to get to the finish line.

###How to Add New Features to the Project

To add an additional enemy or obstacle to one of the existing levels, you just have to go to that levels placeEnemies
or placeObstacles method and select the x and y values of the object as well as what type of enemy or obstacle you 
would like it to be. To add a completely new type of obstacle or enemy, you just need to make a new class that either
extends Enemy or Obstacle and then place some into the levels by adding an if statement checking type in placeAnEnemy
or placeAnObstacle and then doing what I said in the first sentence. To add a new power up, make a new class that 
extends Obstacle and do what I said in the previous sentences as well as adding a new method in the levels showing
what will happen if the hero picks up that power up and placing said method in step.

###Justifying Major Decision Choices in My Project

For the multiple different screens, I decided to each have them have their own separate class because they are each so
different that there would be an absurd number of parameters for a constructor in a superclass. This does lead to some
duplicated code in the multiple classes but I believe it would be simpler than creating a superclass with only two
methods, init and establishHandler, that would be able to do all of the different things that all of my different 
screens do. For the levels, I decided it was better to have two separate classes than a superclass with two different
subclasses because of the difference of placing obstacles and enemies as well as the different movements of the boss.
This also leads to some duplicated code and makes it more difficult to add on another level, but I believe what I did
was the simpler choice.

###Assumptions or Decisions Made to Simplify or Resolve Ambiguities in My Project's Functionality

I believe the functionality of my project is pretty straightforward. One decision I can think of was to take the 
different parts of the method step in both Level1 and BossLevel and make them into separate methods to make step
shorter and more readable.