# Jay Nash 2023 COM407
# With assistance from Russell Kosovsky and Brooke Brandenbruger during the theoretical design stage
import libpyAI as ai # Expected Error without proper enviornment
import numpy as num
import random as random

def AI_loop():
  #Release keys
  ai.thrust(0)
  ai.turnLeft(0)
  ai.turnRight(0)
  ai.setPower(25)
  #Set variables
  rTurn = False #Unused
  lTurn = True #Unused
  
  # Wall Feelers
  heading = int(ai.selfHeadingDeg())
  tracking = int(ai.selfTrackingDeg())
  
  frontWall = ai.wallFeeler(1000,heading)
  frontWallL = ai.wallFeeler(1000,heading+10)
  frontWallR = ai.wallFeeler(1000,heading-10)
  
  leftWall = ai.wallFeeler(1000,heading+90)
  leftWallL = ai.wallFeeler(1000,heading+100)
  leftWallR = ai.wallFeeler(1000,heading+80)
  
  rightWall = ai.wallFeeler(1000,heading+270)
  rightWallL = ai.wallFeeler(1000,heading+280)
  rightWallR = ai.wallFeeler(1000,heading+260)
  
  backWall = ai.wallFeeler (1000,heading+180)
  backWallL = ai.wallFeeler(1000,heading+190)
  backWallR = ai.wallFeeler(1000,heading+170)
  
  frontWallT = frontWall+frontWallL+frontWallR
  leftWallT = leftWall+leftWallL+leftWallR
  rightWallT = rightWall+rightWallL+rightWallR
  backWallT = backWall+backWallL+backWallR
  
  trackWall = ai.wallFeeler(1000,tracking)
  trackWallL = ai.wallFeeler(1000,tracking+10)
  trackWallR = ai.wallFeeler(1000,tracking-10)
  
  trackWallT = trackWall+trackWallR+trackWallL
  
  print(ai.enemyDistance(0)) # This seems to make it work better. Maybe I've just been looking at it too long but I swear it does
  
  # Small production system for thrust
  if frontWallT > 1500 and ai.selfSpeed() < 15:
    ai.thrust(1)
  elif frontWallT > leftWallT and frontWallT > rightWallT and frontWallT > backWallT and ai.selfSpeed() < 15:
    ai.thrust(1)
  elif trackWallT < 450 and 270 >= abs(tracking-heading) >= 90:
    ai.thrust(1)
  elif backWall < 10:
    ai.thrust(1)
    
  # Main production system for turning and aiming
  if heading > ai.aimdir(0) and ai.enemyDistance(0) < 300 and trackWall > 100:
    ai.turnRight(1)
  elif heading < ai.aimdir(0) and ai.enemyDistance(0) < 300 and trackWall > 100:
    ai.turnLeft(1)
  elif leftWallT > rightWallT and trackWallT > 900 and ai.selfSpeed() > 5:
    ai.turnLeft(1)
  elif leftWallT < rightWallT and trackWallT > 900 and ai.selfSpeed() > 5:
    ai.turnRight(1)
  elif heading > (tracking+180)%360 and trackWallT < 900 and ai.selfSpeed() > 10:
    ai.turnRight(1)
  elif heading < (tracking+180)%360 and trackWallT < 900 and ai.selfSpeed() > 10:
    ai.turnLeft(1)
  elif leftWallT > rightWallT and trackWallT < 500:
    ai.turnLeft(1)
  elif rightWallT > leftWallT and trackWallT < 500:
    ai.turnRight(1)
  elif heading > ai.aimdir(0) and frontWallT > 100 and leftWallT > 100 and rightWallT > 100 and backWallT > 100:
    ai.turnRight(1)
  elif heading < ai.aimdir(0) and frontWallT > 100 and leftWallT > 100 and rightWallT > 100 and backWallT > 100:
    ai.turnLeft(1)
  elif leftWallT > rightWallT:
    ai.turnLeft(1)
  elif leftWallT > rightWallT:
    ai.turnRight(1)
    
  # Shooting
  if (ai.aimdir(0)+5)%360 >= heading >= (ai.aimdir(0)-5)%360:
    ai.fireShot()
  
ai.start(AI_loop,["-name","Help","-join","localhost"])
