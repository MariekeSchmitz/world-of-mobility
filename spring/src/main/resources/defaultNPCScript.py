import random

velocity = random.randint(3, 7)

if npc.currentVelocity() == 0:
    while npc.currentVelocity() * 10 < velocity:
        npc.accelerate()

for user in npc.nearbyRoadUsers():
    if npc.distanceTo(user) < 0.5:
        npc.brake()
        break

if random.randint(0, 1):
    npc.turnLeft()
