import random

npc.start()

if npc.isMotorizedObject:

    front_tile = npc.getFrontTile()
    placeable = front_tile.getPlaceable()

    if placeable and placeable.getType() == TRAFFIC_LIGHT:

        if npc.getOrientation() in [NORTH, SOUTH] and npc.currentTrafficLightState() != VERTICAL_GREEN:
            npc.emergencyBrake()

        elif npc.getOrientation() in [EAST, WEST] and npc.currentTrafficLightState() != HORIZONTAL_GREEN:
            npc.emergencyBrake()

#    if not npc.isTrafficLightGreen(0.2):
#        npc.emergencyBrake()

    randomint = random.randint(0,4)

else:

    if npc.currentVelocity() == 1:
        npc.brake()
        npc.brake()

    randomint = random.randint(0,1000)


if randomint == 0 :
    npc.turnLeft()
elif randomint == 1:
    npc.turnRight()





    