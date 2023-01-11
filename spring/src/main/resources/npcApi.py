class NPC():

    def __init__(self):
        pass


    def setSpeed(self, speed):
        moveable.setCurrentVelocity(speed)


    def frontTile(self):
        
        orientation = moveable.getOrientation()

        delta_y = 1 if orientation == orientation.NORTH else -1

        x = int(moveable.getXPos())
        y = int(moveable.getYPos())

        if (y + delta_y) < 0 or (y + delta_y) >= len(tiles):
            return None

        return tiles[y+delta_y][x]


    def frontLeftTile(self):

        orientation = moveable.getOrientation()

        delta_x = -1 if orientation == orientation.NORTH else 1
        delta_y = 1 if orientation == orientation.NORTH else -1

        x = int(moveable.getXPos())
        y = int(moveable.getYPos())

        if (x + delta_x) < 0 or (x + delta_x) >= len(tiles) or (y + delta_y) < 0 or (y + delta_y) >= len(tiles):
            return None

        return tiles[y + delta_y][x + delta_x]


    def frontRightTile(self):

        orientation = moveable.getOrientation()

        delta_x = 1 if orientation == orientation.NORTH else -1
        delta_y = 1 if orientation == orientation.NORTH else -1

        x = int(moveable.getXPos())
        y = int(moveable.getYPos())

        if (x + delta_x) < 0 or (x + delta_x) >= len(tiles) or (y + delta_y) < 0 or (y + delta_y) >= len(tiles):
            return None

        return tiles[y + delta_y][x + delta_x]


    def leftTile(self):

        orientation = moveable.getOrientation()

        


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