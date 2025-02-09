import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TrafficSim {
    private static Timer timer;
    static ArrayList<Car> cars = new ArrayList<>();
    static ArrayList<TrafficLight> trafficLights = new ArrayList<>();

    public static void main(String[] args) {
        GridDisplay display = new GridDisplay();
        // Init Lights
        TrafficLight NBLight = new TrafficLight(7, 12, "S");
        TrafficLight EBLight = new TrafficLight(12, 12, "W");
        TrafficLight SBLight = new TrafficLight(12, 7, "N");
        TrafficLight WBLight = new TrafficLight(7, 7, "E");
        trafficLights.add(NBLight);
        trafficLights.add(EBLight);
        trafficLights.add(SBLight);
        trafficLights.add(WBLight);
        for (TrafficLight light : trafficLights) {
            display.updateLightCell(light);
        }

        // Init cars
        Car EBCar = new Car(11, 0, 1, "east");
        Car NBCar = new Car(19, 11, 1, "north");
        Car SBCar = new Car(0, 8, 1, "south");
        cars.add(EBCar);
        cars.add(NBCar);
        cars.add(SBCar);
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

        // Set up the timer
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Handle car movement
                for (Car car : cars) {
                    if (car.isMoving()) {
                        display.updateCarCell(car, true);
                        car.move();
                        display.updateCarCell(car, false);
                    }
                }
            }
        });

        // Start the timer
        timer.start();
    }
}
