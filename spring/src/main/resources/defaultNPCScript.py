import random


npc.start()

if npc.isMotorizedObject():

    front_tile = npc.getFrontTile()
    current_tile = npc.getCurrentTile()
    placeable = front_tile.getPlaceable()

    randomint = random.randint(0,4)

    # if placeable.getType() and placeable.getType().name() == "TRAFFIC_LIGHT":
    #     if npc.getOrientation().name() in ["NORTH", "SOUTH"] and npc.currentTrafficLightState().name() != "VERTICAL_GREEN" and npc.distanceTo(npc.getFrontTile()) <= 1.6:
    #         npc.stop()

    #     elif npc.getOrientation().name() in ["EAST", "WEST"] and npc.currentTrafficLightState().name() != "HORIZONTAL_GREEN" and npc.distanceTo(npc.getFrontTile()) <= 1.6:
    #         npc.stop()

    if not npc.isTrafficLightGreen(0.2):
        npc.stop()

    if current_tile and current_tile.getType().name() == "PEDESTRIAN_CROSSING":
        for user in filter(lambda x: npc.distanceTo(x) < 0.5, npc.nearbyRoadUsers()):
            if npc.checkPassenger(user):
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





    