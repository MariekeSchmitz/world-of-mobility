import random


npc.start()

if npc.isMotorizedObject():

    front_tile = npc.getFrontTile()
    current_tile = npc.getCurrentTile()
    placeable = front_tile.getPlaceable()

    randomint = random.randint(0,4)

    if placeable.getType() == "TRAFFIC_LIGHT":

        if npc.getOrientation() in ["NORTH", "SOUTH"] and placeable.getState() != "VERTICAL_GREEN" and npc.distanceTo(front_tile) <= 0.7:
            npc.stop()

        elif npc.getOrientation() in ["EAST", "WEST"] and placeable.getState() != "HORIZONTAL_GREEN" and npc.distanceTo(front_tile) <= 0.7:
            npc.stop()


    if current_tile.getType() == "PEDESTRIAN_CROSSING":

        for obj in filter(lambda x: npc.distanceTo(x) <= 0.5, npc.nearbyRoadUsers()):
            if npc.checkPassenger(obj):
                npc.stop()
                break; 	


else:

    if npc.getCurrentVelocity() == 0.5:
        npc.brake()
        npc.brake()

    randomint = random.randint(0,20)


if randomint == 0 :
    npc.turnLeft()
elif randomint == 1:
    npc.turnRight()





    