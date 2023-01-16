
class NPC():

    def __init__(self):
        pass

    def setSpeed(self, speed):
        moveable.setCurrentVelocity(speed)

    def accelerate(self):
        speed = moveable.getCurrentVelocity()
        moveable.setCurrentVelocity(speed + 0.1)

    def brake(self):
        speed = moveable.getCurrentVelocity()
        moveable.setCurrentVelocity(speed - 0.1)

    def turnLeft(self):
        moveable.turn(Direction.LEFT)

    def turnRight(self):
        moveable.turn(Direction.RIGHT)


class Car(NPC):

    def honk(self):
        pass