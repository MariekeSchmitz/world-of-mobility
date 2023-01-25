import random

if npc.currentVelocity() == 0:
        npc.accelerate()

# for user in npc.nearbyRoadUsers():
#     if npc.distanceTo(user) < 0.5:
#         npc.brake()
#         break

if not npc.getFrontTrafficLight():
    npc.emergencyBrake()


if random.randint(0, 1):
    npc.turnLeft()
else:
    npc.turnRight()
