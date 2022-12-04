package de.hsrm.mi.swt_project.demo.movables;

public enum MoveableType {

    PASSENGER() {
        @Override
        public MoveableObject createMovable(int xPos, int yPos, float maxVelocity) {
            return new Passenger(xPos, yPos, maxVelocity);
        }
    },
    MOTORIZED_OBJECT() {
        @Override
        public MoveableObject createMovable(int xPos, int yPos, float maxVelocity) {
            return new MotorizedObject(xPos, yPos, maxVelocity);
        }
    };

    public abstract MoveableObject createMovable(int xPos, int yPos, float maxVelocity);
}
