
def setSpeed(currentVelocity):
    moveable.setCurrentVelocity(currentVelocity)

def turnLeft():
    moveable.turn(Direction.LEFT)

def turnRight():
    moveable.turn(Direction.RIGHT)

if __name__ == "__main__":

    setSpeed(0.1)
    turnLeft()
