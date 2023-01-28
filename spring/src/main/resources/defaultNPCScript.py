import random

npc.start()

if npc.isMotorizedObject:

    if not npc.isTrafficLightGreen(0.2):
        npc.emergencyBrake()

    randomint = random.randint(0,4)
    if randomint == 0 :
        npc.turnLeft()
    elif randomint == 1:
        npc.turnRight()

else:

    if npc.currentVelocity() == 1:
        npc.brake()
        npc.brake()

    randomint = random.randint(0,1000)
    if randomint == 0 :
        npc.turnLeft()
    elif randomint == 1:
        npc.turnRight()





    