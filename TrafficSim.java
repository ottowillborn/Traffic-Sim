import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class TrafficSim {
    private static Timer mainTimer;
    private static Timer trafficLightTimer;
    private static Timer yellowLightTimer;
    private static Timer newCarTimer;
    static ArrayList<Car> cars = new ArrayList<>();
    static TrafficLight[] trafficLights = new TrafficLight[4];
    private static Random rand = new Random();

    public static void main(String[] args) {
        GridDisplay display = new GridDisplay();
        // Init Lights
        TrafficLight NBLight = new TrafficLight(7, 12, "S");
        TrafficLight EBLight = new TrafficLight(12, 12, "W");
        TrafficLight SBLight = new TrafficLight(12, 7, "N");
        TrafficLight WBLight = new TrafficLight(7, 7, "E");
        NBLight.changeLight(LightColor.GREEN);
        SBLight.changeLight(LightColor.GREEN);
        trafficLights[0] = NBLight;
        trafficLights[1] = EBLight;
        trafficLights[2] = SBLight;
        trafficLights[3] = WBLight;
        for (TrafficLight light : trafficLights) {
            display.updateLightCell(light);
        }

        //Init cars
        //Car EBCar = new Car(11, 0, 1, "east", true, false, "Assets/whitecar.png");
        Car EBCar2 = new Car(10, 0, 1, "east", false, true, "Assets/yellowcar.png");
        //Car NBCar = new Car(19, 11, 1, "north", false, false, "Assets/bluecar.png");
        Car WBCar = new Car(9, 19, 1, "west", false, true, "Assets/yellowcar.png");
        //Car SBCar = new Car(0, 8, 1, "south", false, false, "Assets/bluecar.png");
        Car SBCar2 = new Car(0, 9, 1, "south", false, true, "Assets/yellowcar.png");
        //Car WBCar = new Car(8, 19, 1, "west", false, false, "Assets/bluecar.png");
        Car NBCar2 = new Car(19, 10, 1, "north", false, true, "Assets/yellowcar.png");
        //cars.add(EBCar);
        cars.add(EBCar2);
        //cars.add(NBCar);
        //cars.add(SBCar);
        cars.add(SBCar2);
        cars.add(NBCar2);
        //cars.add(WBCar);
        cars.add(WBCar);
        for (Car car : cars) {
            car.startCar();
            display.updateCarCell(car, false);
        }
        // Wait for start button to be pressed
        while (!display.simRunning) {
            System.out.println("");
        }
        startSimulation(display);
    }

    // Root of simulation timer
    public static void startSimulation(GridDisplay display) {
        System.out.println("Starting sim!");

        // Timer for cars
        mainTimer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle car movement
                Iterator<Car> iterator = cars.iterator();
                while (iterator.hasNext()) {
                    Car car = iterator.next();
                    if (car.isAtIntersection() && !car.isCarInFront(cars)) {
                        if (car.canProceed(trafficLights)) {
                            updateCar(car, display, iterator);
                            car.startCar();
                        } else {
                            car.stopCar();
                        }
                    } else {
                        if (!car.isCarInFront(cars)){
                            updateCar(car, display, iterator);
                            car.startCar();
                        } else {
                            car.stopCar();
                        }        
                    }                   
                }
            }
        });

        // Timer for traffic Lights
        trafficLightTimer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchToYellow();
                updateLights(display);
            }
        });

        // Timer for switching from yellow to red after 2 seconds
        yellowLightTimer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchToRedAndGreen();
                updateLights(display);
            }
        });

        // Timer for adding random car
        int randomDelay = rand.nextInt(2000) + 2000;
        newCarTimer = new Timer(randomDelay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Car randomCar = generateRandomCar();
                randomCar.startCar();
                cars.add(randomCar);

                // Set random delay and restart
                newCarTimer.setInitialDelay(rand.nextInt(2000) + 2000);
                newCarTimer.restart();
            }
        });


        // Start the timers
        newCarTimer.setRepeats(false);
        //newCarTimer.start();
        trafficLightTimer.start();
        mainTimer.start();
    }

    // Method to switch green lights to yellow
    public static void switchToYellow() {
        for (TrafficLight light : trafficLights) {
            if (light.color == LightColor.GREEN) {
                light.color = LightColor.YELLOW;
            }
        }
        yellowLightTimer.start();
        trafficLightTimer.stop();
    }

    // Method to switch yellow lights to red and others to green
    private static void switchToRedAndGreen() {
        for (TrafficLight light : trafficLights) {
            if (light.color == LightColor.YELLOW) {
                light.color = LightColor.RED;
            } else {
                light.color = LightColor.GREEN;
            }
        }
        yellowLightTimer.stop();
        trafficLightTimer.start();
    }

    // Method to update car on display and remove ones that move off
    private static void updateCar(Car car, GridDisplay display, Iterator<Car> iterator) {
        if (!car.willMoveOff()) {
            if (car.isMoving()) {
                if (car.wantsToTurnRight() && car.isInRightTurnPosition()) {
                    car.setDirection(car.getRightTurnDirection());
                }
                if (car.wantsToTurnLeft() && (car.isInLeftTurnPosition() || car.isTurningLeft())) {
                    car.setIsTurningLeft(true);
                    if (car.leftTurnComplete()){
                        car.setDirection(car.getFinalLeftTurnDirection());
                        display.updateCarCell(car, true);
                        car.move();
                        display.updateCarCell(car, false);
                        car.setIsTurningLeft(false);
                    } else {
                        car.setDirection(car.getIntermediateLeftTurnDirection());
                        display.updateCarCell(car, true);
                        car.diagonalMove();
                        display.updateCarCell(car, false);
                    }
                } else {
                    display.updateCarCell(car, true);
                    car.move();
                    display.updateCarCell(car, false);
                }
                
            }
            
        }else {
            display.updateCarCell(car, true);
            iterator.remove();
        }
    }

    // Method to update all traffic lights
    private static void updateLights(GridDisplay display) {
        for (TrafficLight light : trafficLights) {
            display.updateLightCell(light);
        }
    }

    public static Car generateRandomCar() {
        Random rand = new Random();
        // Randomly select a direction
        String[] directions = {"north", "south", "east", "west"};
        String randomDirection = directions[rand.nextInt(directions.length)];
        int x = 0;
        int y = 0;
        switch (randomDirection) {
            case "north":
                x = 19;
                y = 11;
                break;
            case "south":
                x = 0;
                y = 8;
                break;
            case "east":
                x = 11;
                y = 0;
                break;
            case "west":
                x = 8;
                y = 19;
                break;       
        }
        boolean wantsToTurnRight = (rand.nextDouble() < 0.5) ? true : false;
        System.out.println(randomDirection);
        Car randCar = new Car(
            x, 
            y, 
            1, 
            randomDirection, 
            wantsToTurnRight, 
            false,
            wantsToTurnRight ? "Assets/whitecar.png" : "Assets/bluecar.png"
            );
        return randCar;
    }
}
