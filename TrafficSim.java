import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

public class TrafficSim {
    private static Timer mainTimer;
    private static Timer trafficLightTimer;
    private static Timer yellowLightTimer;
    static ArrayList<Car> cars = new ArrayList<>();
    static TrafficLight[] trafficLights = new TrafficLight[4];

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

        // Init cars
        Car EBCar = new Car(11, 0, 1, "east");
        Car NBCar = new Car(19, 11, 1, "north");
        Car SBCar = new Car(0, 8, 1, "south");
        Car WBCar = new Car(8, 19, 1, "west");
        Car WBCar2 = new Car(8, 16, 1, "west");
        cars.add(EBCar);
        cars.add(NBCar);
        cars.add(SBCar);
        cars.add(WBCar);
        cars.add(WBCar2);
        for (Car car : cars) {
            car.startCar();
            display.updateCarCell(car, false);
        }
        // Wait for start button to be pressed
        while (!display.simRunning) {
            System.out.println("!");
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
                    if (car.isMoving()) {
                        if (car.isAtIntersection()) {
                            if (car.canProceed(trafficLights)) {
                                updateCar(car, display, iterator);
                            }
                        } else {
                            if (!car.isCarInFront(cars)){
                                updateCar(car, display, iterator);
                            }
                            
                        }
                    }
                }
            }
        });

        // Timer for traffic Lights
        trafficLightTimer = new Timer(8000, new ActionListener() {
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

        // Start the timers
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
        display.updateCarCell(car, true);
        if (!car.willMoveOff()) {
            car.move();
            display.updateCarCell(car, false);
        }else {
            iterator.remove();
        }
    }

    // Method to update all traffic lights
    private static void updateLights(GridDisplay display) {
        for (TrafficLight light : trafficLights) {
            display.updateLightCell(light);
        }
    }
}
