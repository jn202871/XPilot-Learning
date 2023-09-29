# Jay Nash & Russell Kosovesky 2023 COM407
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
    shoot = False
    align = False
    avoid = False
    
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
  
    frontWallT = (frontWall+frontWallL+frontWallR)/3
    leftWallT = (leftWall+leftWallL+leftWallR)/3
    rightWallT = (rightWall+rightWallL+rightWallR)/3
    backWallT = (backWall+backWallL+backWallR)/3
  
    trackWall = ai.wallFeeler(1000,tracking)
    trackWallL = ai.wallFeeler(1000,tracking+10)
    trackWallR = ai.wallFeeler(1000,tracking-10)
  
    trackWallT = (trackWall+trackWallR+trackWallL)/3

    # Fuzzification of distances
    front1 = min(1,max(0,2-(frontWallT/200)))
    front2 = min(1,max(0,1-abs((frontWallT-400)/200)))
    front3 = min(1,max(0,(frontWallT-400)/300))
    wallProxFront = {"close" : front1, "near" : front2, "far" : front3}
    left1 = min(1,max(0,2-(leftWallT/200)))
    left2 = min(1,max(0,1-abs((leftWallT-400)/200)))
    left3 = min(1,max(0,(leftWallT-400)/300))
    wallProxLeft = {"close" : left1, "near" : left2, "far" : left3}
    right1 = min(1,max(0,2-(rightWallT/200)))
    right2 = min(1,max(0,1-abs((rightWallT-400)/200)))
    right3 = min(1,max(0,(rightWallT-400)/300))
    wallProxRight = {"close" : right1, "near" : right2, "far" : right3}
    back1 = min(1,max(0,2-(backWallT/200)))
    back2 = min(1,max(0,1-abs((backWallT-400)/200)))
    back3 = min(1,max(0,(backWallT-400)/300))
    wallProxBack = {"close" : back1, "near" : back2, "far" : back3}
    
    # Fuzzification of Alignment
    rawAlignment = abs(((ai.aimdir(0)-heading+540)%360)-180)
    if ai.aimdir(0) == -1:
        rawAlignment = 180
    align1 = min(1,max(0,(2-(rawAlignment/5))))
    align2 = min(1,max(0,1-abs((rawAlignment-20)/15)))
    align3 = min(1,max(0,1-abs((rawAlignment-60)/35)))
    align4 = min(1,max(0,(rawAlignment-60)/50))
    alignment = {"aligned" : align1, "near" : align2, "moderate" : align3, "poor" : align4}
    
    # Fuzzification of Enemy Distance
    rawDist = ai.enemyDistance(0)
    dist1 = min(1,max(0,2-(rawDist/400)))
    dist2 = min(1,max(0,(rawDist-400)))
    enemyDist = {"near" : dist1, "far" : dist2}
    if ai.wallBetween(ai.selfX(),ai.selfY(),ai.screenEnemyX(0),ai.screenEnemyY(0)) != -1:
        enemyDist["near"] = 0
    
    # Wall danger aggregation
    wallDanger1 = max(wallProxFront["close"],wallProxLeft["close"],wallProxRight["close"],wallProxBack["close"])
    wallDanger2 = min(wallProxFront["near"],wallProxLeft["near"],wallProxRight["near"],wallProxBack["near"])
    wallDanger3 = min(wallProxFront["far"],wallProxLeft["far"],wallProxRight["far"],wallProxBack["far"])
    wallDanger = {"close" : wallDanger1, "near":wallDanger2,"far":wallDanger3}
    
    # Behavior aggregation
    behavior = {"shoot" : 0.0, "avoidWall" : 0.0, "align" : 0.0}
  
    if wallDanger["close"] > wallDanger["near"] and wallDanger["close"] > wallDanger["far"]:
        behavior["avoidWall"] += wallDanger["close"]
    if enemyDist["far"] > enemyDist["near"]:
        behavior["avoidWall"] += enemyDist["far"]
    if enemyDist["near"] >= enemyDist["far"]:
        behavior["align"] += enemyDist["near"]
    if wallDanger["far"] > wallDanger["close"] or wallDanger["near"] > wallDanger["close"]:
        behavior["align"] += max(wallDanger["near"],wallDanger["far"])
    if alignment["aligned"] >= alignment["near"]:
        behavior["shoot"] += alignment["aligned"]
    if enemyDist["near"] >= enemyDist["far"]:
        behavior["align"] += enemyDist["near"]
    if enemyDist["far"] > enemyDist["near"]:
        behavior["avoidWall"] += enemyDist["far"] 
    if wallDanger["far"] > wallDanger["close"] and alignment["aligned"] <= 0:
        behavior["align"] += wallDanger["far"]
    behavior["shoot"] = behavior["shoot"]/1
    behavior["avoidWall"] = behavior["avoidWall"]/3
    behavior["align"] = behavior["align"]/4
    
    # Defuzzification
    if behavior["shoot"] >= behavior["align"] and behavior["shoot"] >= behavior["avoidWall"]:
        shoot = True
    elif behavior["align"] >= behavior["avoidWall"]:
        align = True
    else:
        avoid = True
    
    # Small production system for thrust
    head_track_diff = int(180 - abs(abs(heading - tracking) - 180))
    thrust = 0
    if frontWallT > 500 and ai.selfSpeed() < 15:
        thrust = 1
    elif frontWallT > leftWallT and frontWallT > rightWallT and frontWallT > backWallT and ai.selfSpeed() < 15:
        thrust = 1
    elif trackWallT < 300 and 270 >= abs(heading-tracking) >= 90:
        thrust = 1
    elif backWall < 10:
        thrust = 1
    elif ai.selfSpeed() == 0 and frontWall > 300:
        thrust = 1
    elif trackWall < 170 and head_track_diff > 90:
        thrust = 1
    elif backWall < 170 and head_track_diff > 90:
        thrust = 1
    
    aimDiff = ((ai.aimdir(0)-heading+540)%360)-180
    trackDiff = ((tracking-heading+540)%360)-180
    if rawDist > 1000:
        modDist = 1000
    else:
        modDist = rawDist

    inputs = str(heading/360) + "," + str(tracking/360) + "," + str(frontWall/1000) + "," + str(frontWallL/1000) + "," + str(frontWallR/1000) + "," + str(leftWall/1000) + "," + str(leftWallL/1000) + "," + str(leftWallR/1000) + "," + str(rightWall/1000) + "," + str(rightWallL/1000) + "," + str(rightWallR/1000) + "," + str(backWall/1000) + "," + str(backWallL/1000) + "," + str(backWallR/1000) + "," + str(trackWall/1000) + "," + str(trackWallL/1000) + "," + str(trackWallR/1000) + "," + str(modDist/1000) + "," + str(aimDiff/180) + "," + str(trackDiff/180) + "," + str(ai.selfSpeed()/20)
    print(ai.selfSpeed())
    shot = 0
    right = 0
    left = 0
    
    # if behavior is shoot
    if shoot:
        shot = 1
        ai.fireShot()
    
    # if behavior is align
    if align:
        if aimDiff > 0:
            left = 1
        elif aimDiff < 0:
            right = 1
    if avoid: # If behavior is not align then avoid
        if leftWallT > rightWallT and trackWallT > 500 and ai.selfSpeed() > 5:
            left = 1
        elif leftWallT < rightWallT and trackWallT > 500 and ai.selfSpeed() > 5:
            right = 1
        elif trackDiff > 0 and trackWallT < 500 and ai.selfSpeed() > 10:
            right = 1
        elif trackDiff < 0 and trackWallT < 500 and ai.selfSpeed() > 10:
            left = 1
        elif leftWallT > rightWallT and trackWallT < 500:
            left = 1
        elif rightWallT > leftWallT and trackWallT < 500:
            right = 1
        elif leftWallT > rightWallT:
            left = 1
        elif leftWallT < rightWallT:
            right = 1

    ai.turnLeft(left)
    ai.turnRight(right)
    ai.thrust(thrust)

    outputs = str(thrust) + "," + str(left) + "," + str(right) + "," + str(shot)
    if ai.selfAlive() == 1 and rawDist < 5000:
  	    file1 = open("inputs.txt", "a")  # append mode
  	    file2 = open("outputs.txt", "a")  # append mode
  	    file1.write(inputs + "\n")
  	    file1.close()
  	    file2.write(outputs + "\n")
  	    file2.close()
  
  
ai.start(AI_loop,["-name","FuzzyHelp","-join","localhost"])
