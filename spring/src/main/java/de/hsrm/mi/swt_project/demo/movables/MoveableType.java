package de.hsrm.mi.swt_project.demo.movables;

public enum MoveableType {

    PASSENGER() {
        @Override
        public MoveableObject createMovable(int xPos, int yPos, float maxVelocity) {
            return new Passenger(xPos, yPos, maxVelocity);
        }

        @Override
        public MoveableObject createMovable() {
            return new Passenger();
        }
    },
    MOTORIZED_OBJECT() {
        @Override
        public MoveableObject createMovable(int xPos, int yPos, float maxVelocity) {
            return new MotorizedObject(xPos, yPos, maxVelocity);
        }

        @Override
        public MoveableObject createMovable() {
            return new MotorizedObject();
        }
    };

    public abstract MoveableObject createMovable();
    public abstract MoveableObject createMovable(int xPos, int yPos, float maxVelocity);
}
