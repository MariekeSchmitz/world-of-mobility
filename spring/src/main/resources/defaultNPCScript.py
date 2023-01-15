import random

nearbyRoadUsers = npc.nearbyRoadUsers()
surroundingTiles = npc.surroundingTiles()
currentVelocity = npc.currentVelocity()

velocity = random.randint(3, 7) / 10

if currentVelocity == 0:
    while currentVelocity < velocity:
        npc.accelerate()

for user in nearbyRoadUsers:
    if npc.distanceTo(user) < 0.5:
        npc.brake()
        break

if npc.getFrontTile().getType() == 'STREET_CROSS':
    npc.left()

