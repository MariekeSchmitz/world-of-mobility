package de.hsrm.mi.swt_project.demo.movables;

public enum MoveableType {

    CAR() {
        @Override
        public MoveableObject createMovable(float xPos, float yPos) {
            MoveableObject mvObj = new Car(xPos, yPos);
            mvObj.type = this;
            return mvObj;
        }

        @Override
        public MoveableObject createMovable() {
            MoveableObject mvObj = new Car();
            mvObj.type = this;
            return mvObj;
        }
    },
    SHEEP() {
        @Override
        public MoveableObject createMovable(float xPos, float yPos) {
            MoveableObject mvObj = new Sheep(xPos, yPos);
            mvObj.type = this;
            return mvObj;
        }

        @Override
        public MoveableObject createMovable() {
            MoveableObject mvObj = new Sheep();
            mvObj.type = this;
            return mvObj;
        }
    },
    TRUCK() {
        @Override
        public MoveableObject createMovable(float xPos, float yPos) {
            MoveableObject mvObj = new Truck(xPos, yPos);
            mvObj.type = this;
            return mvObj;
        }

        @Override
        public MoveableObject createMovable() {
            MoveableObject mvObj = new Truck();
            mvObj.type = this;
            return mvObj;
        }
    },
    PIG() {
        @Override
        public MoveableObject createMovable(float xPos, float yPos) {
            MoveableObject mvObj = new Pig(xPos, yPos);
            mvObj.type = this;
            return mvObj;
        }

        @Override
        public MoveableObject createMovable() {
            MoveableObject mvObj = new Pig();
            mvObj.type = this;
            return mvObj;
        }
    },
    TRACTOR() {
        @Override
        public MoveableObject createMovable(float xPos, float yPos) {
            MoveableObject mvObj = new Tractor(xPos, yPos);
            mvObj.type = this;
            return mvObj;
        }

        @Override
        public MoveableObject createMovable() {
            MoveableObject mvObj = new Tractor();
            mvObj.type = this;
            return mvObj;
        }
    },
    TUPEL() {
        @Override
        public MoveableObject createMovable(float xPos, float yPos) {
            MoveableObject mvObj = new Tupel(xPos, yPos);
            mvObj.type = this;
            return mvObj;
        }

        @Override
        public MoveableObject createMovable() {
            MoveableObject mvObj = new Tupel();
            mvObj.type = this;
            return mvObj;
        }
    };

    public abstract MoveableObject createMovable();
    public abstract MoveableObject createMovable(float xPos, float yPos);
}
