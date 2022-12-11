package de.hsrm.mi.swt_project.demo.movables;

public enum MoveableType {

    PASSENGER() {
        @Override
        public MoveableObject createMovable(int xPos, int yPos, float maxVelocity) {
            MoveableObject mvObj = new Passenger(xPos, yPos, maxVelocity);
            mvObj.type = this;
            return mvObj;
        }

        @Override
        public MoveableObject createMovable() {
            MoveableObject mvObj = new Passenger();
            mvObj.type = this;
            return mvObj;
        }
    },
    MOTORIZED_OBJECT() {
        @Override
        public MoveableObject createMovable(int xPos, int yPos, float maxVelocity) {
            MoveableObject mvObj = new MotorizedObject(xPos, yPos, maxVelocity);
            mvObj.type = this;
            return mvObj;
        }

        @Override
        public MoveableObject createMovable() {
            MoveableObject mvObj = new MotorizedObject();
            mvObj.type = this;
            return mvObj;
        }
    };

    public abstract MoveableObject createMovable();
    public abstract MoveableObject createMovable(int xPos, int yPos, float maxVelocity);
}
