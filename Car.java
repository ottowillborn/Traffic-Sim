import java.util.ArrayList;

public class Car {
    private int x; // x position of the car on the grid
    private int y; // y position of the car on the grid
    private int speed; // speed of the car (could be in units per second)
    private String direction; // direction of the car ("north", "south", "east", "west")
    private boolean isMoving; // whether the car is moving or not
    private boolean wantsToTurnRight;
    private boolean wantsToTurnLeft;
    private boolean isTurningLeft;
    private boolean signalOn;

    private String imagePath;
    private String blinkingImagePath;

    // Constructor to initialize the car's properties
    public Car(
        int x, 
        int y, 
        int speed, 
        String direction, 
        boolean wantsToTurnRight, 
        boolean wantsToTurnLeft,
        String imagePath,
        String blinkingImagePath) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.direction = direction;
        this.wantsToTurnRight = wantsToTurnRight;
        this.wantsToTurnLeft = wantsToTurnLeft;
        this.imagePath = imagePath;
        this.blinkingImagePath = blinkingImagePath;
        this.isMoving = false; // Cars start off stopped
    }

    // Getters and setters for the car's attributes
    public int getX() {
        return x;
    }
    public boolean getSignalOn() {
        return this.signalOn;
    }
    public void setSignalOn(boolean b) {
        this.signalOn = b;
    }

    /*--------------------- Right Turn Methods ---------------------*/

    public boolean wantsToTurnRight(){
        return this.wantsToTurnRight;
    }

    public void setWantsToTurnRight(boolean b) {
        this.wantsToTurnRight = b;
    }

    public String getRightTurnDirection() {
        switch (direction) {
            case "north":
                return "east";
            case "south":
                return "west";
            case "east":
                return "south";
            case "west":
                return "north";
        }
        return "";
    }

    public boolean isInRightTurnPosition() {
        switch (direction) {
            case "north":
                return (x==11 && y==11) ?  true : false;
            case "south":
                return (x==8 && y==8) ?  true : false;
            case "east":
                return (x==11 && y==8) ?  true : false;
            case "west":
                return (x==8 && y==11) ?  true : false;
        }
        return true;
    }

    /*--------------------- Left Turn Methods ---------------------*/

    public boolean wantsToTurnLeft(){
        return this.wantsToTurnLeft;
    }

    public void setWantsToTurnLeft(boolean b) {
        this.wantsToTurnLeft = b;
    }

    public boolean isTurningLeft(){
        return this.isTurningLeft;
    }

    public void setIsTurningLeft(boolean b){
        this.isTurningLeft = b;
    }

    public boolean leftTurnComplete() {
        switch (direction) {
            case "north":
                return x == 9  ?  true : false;
            case "northwest":
                return x == 9 ?  true : false;
            case "south":
                return x == 10 ?  true : false;
            case "southeast":
                return x == 10 ?  true : false;
            case "east":
                return y == 10 ?  true : false;
            case "northeast":
                return y == 10 ?  true : false;
            case "west":
                return y == 9 ?  true : false;
            case "southwest":
                return y == 9 ?  true : false;
        }
        return true;
    }

    public String getFinalLeftTurnDirection() {
        switch (direction) {
            case "northwest":
                return "west";
            case "southwest":
                return "south";
            case "northeast":
                return "north";
            case "southeast":
                return "east";
        }
        return "";
    }

    public String getIntermediateLeftTurnDirection() {
        switch (direction) {
            case "north"://
                return "northwest";
            case "south":
                return "southeast";
            case "east"://
                return "northeast";
            case "west"://
                return "southwest";
            default:
                return this.direction;
        }
    }

    public boolean isInLeftTurnPosition() {
        switch (direction) {
            case "north":
                return (x==11 && y==10) ?  true : false;
            case "south":
                return (x==8 && y==9) ?  true : false;
            case "east":
                return (x==10 && y==8) ?  true : false;
            case "west":
                return (x==9 && y==11) ?  true : false;
        }
        return true;
    }



    public String getImagePath(){
        return signalOn ? this.blinkingImagePath : this.imagePath;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean isMoving) {
        this.isMoving = isMoving;
    }

    // Method to move the car based on its direction and speed
    public void move() {
        if (isMoving) {
            switch (direction) {
                case "north":
                    x -= speed; // Move up
                    break;
                case "south":
                    x += speed; // Move down
                    break;
                case "east":
                    y += speed; // Move right
                    break;
                case "west":
                    y -= speed; // Move left
                    break;
            }
        }
    }

    public void diagonalMove() {
        if (isMoving) {
            switch (direction) {
                case "northwest":
                    x -= speed; 
                    y -= speed;
                    break;
                case "southeast":
                    x += speed; 
                    y += speed;
                    break;
                case "northeast":
                    y += speed; 
                    x -= speed;
                    break;
                case "southwest":
                    y -= speed; 
                    x += speed;
                    break;
            }
        }
    }

    public boolean isCarInFront(ArrayList<Car> cars){
        
        switch (this.direction) {
            case "north":
                for (Car car : cars) {
                    if (car.x == this.x - 1 && car.y == this.y){
                        return true;
                    }
                }
                return false;
                    
            case "south":
                for (Car car : cars) {
                    if (car.x == this.x + 1 && car.y == this.y){
                        return true;
                    }
                }
                return false;
                              
            case "east":
                for (Car car : cars) {
                    if (car.y == this.y + 1 && car.x == this.x){
                        return true;
                    }
                }
                return false;
                
            case "west":
                for (Car car : cars) {
                    if (car.y == this.y - 1 && car.x == this.x){
                        return true;
                    }
                }
                return false;
        }
        return false;
    }

    public boolean willMoveOff() {
        switch (direction) {
            case "north":
                if (this.x - speed < 0) {
                    return true;
                }else {
                    return false;
                }
            case "south":
                if (this.x + speed > 19) {
                    return true;
                }else {
                    return false;
                }
                
            case "east":
                if (this.y + speed > 19) {
                    return true;
                }else {
                    return false;
                }
            case "west":
                if (this.y - speed < 0) {
                    return true;
                }else {
                    return false;
                }
        }
        return false;
    }

    public boolean canProceed(TrafficLight[] trafficLights) {
        switch (direction) {
            case "north":
                return (trafficLights[0].color != LightColor.GREEN) ?  false : true;
            case "south":
                return (trafficLights[2].color != LightColor.GREEN) ?  false : true;
            case "east":
                return (trafficLights[1].color != LightColor.GREEN) ?  false : true;
            case "west":
                return (trafficLights[3].color != LightColor.GREEN) ?  false : true;
        }
        return true;
    }


    public boolean willNotCollide(ArrayList<Car> cars){
        switch (direction) {
            case "east":
                for (Car car : cars) {
                    if (isInRange(car.x, 8, 9) && isInRange(car.y, 10, 13) && car != this && !car.isTurningLeft()) {
                        return false;
                    }
                }
                return true;
            case "west":
                for (Car car : cars) {
                    if (isInRange(car.x, 10, 11) && isInRange(car.y, 7, 10) && car != this && !car.isTurningLeft()) {
                        return false;
                    }
                }
                return true;
            case "north":
                for (Car car : cars) {
                    if (isInRange(car.y, 8, 9) && isInRange(car.x, 7, 10) && car != this && !car.isTurningLeft()) {
                        return false;
                    }
                }
                return true;
            case "south":
                for (Car car : cars) {
                    if (isInRange(car.y, 10, 11) && isInRange(car.x, 9, 12) && car != this && !car.isTurningLeft()) {
                        return false;
                    }
                }
                return true;

        }

        return true;
    }

    public static boolean isInRange(int value, int min, int max) {
        return value >= min && value <= max;
    }

    public boolean isAtIntersection() {
        switch (direction) {
            case "north":
                return (x==12 && (y==11 || y == 10)) ?  true : false;
            case "south":
                return (x==7 && (y==8 || y == 9)) ?  true : false;
            case "east":
                return ((x==11 || x == 10) && y==7) ?  true : false;
            case "west":
                return ((x==8 || x == 9) && y==12) ?  true : false;
        }
        return true;
    }

    // Method to stop the car
    public void stopCar() {
        isMoving = false;
    }

    // Method to start the car
    public void startCar() {
        isMoving = true;
    }

    // Method to display the car's current status
    @Override
    public String toString() {
        return "Car at (" + x + ", " + y + ") moving " + direction + " at speed " + speed + " units/sec.";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        else return false;
    }
}

