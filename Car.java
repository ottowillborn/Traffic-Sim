import java.util.ArrayList;

public class Car {
    private int x; // x position of the car on the grid
    private int y; // y position of the car on the grid
    private int speed; // speed of the car (could be in units per second)
    private String direction; // direction of the car ("north", "south", "east", "west")
    private boolean isMoving; // whether the car is moving or not
    private boolean isTurningRight;
    private String imagePath;

    // Constructor to initialize the car's properties
    public Car(int x, int y, int speed, String direction, boolean isTurningRight, String imagePath) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.direction = direction;
        this.isTurningRight = isTurningRight;
        this.imagePath = imagePath;
        this.isMoving = false; // Cars start off stopped
    }

    // Getters and setters for the car's attributes
    public int getX() {
        return x;
    }

    public boolean isTurningRight(){
        return this.isTurningRight;
    }

    public String getImagePath(){
        return this.imagePath;
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

