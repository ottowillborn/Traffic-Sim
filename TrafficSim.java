import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TrafficSim {
    private static Timer timer;

    public static void main(String[] args) {
        GridDisplay display = new GridDisplay();
        // Wait for start button to be pressed
        while (!display.simRunning) {
            System.out.println("!");
        }
        startSimulation(display);
    }
            
    // Root of simulation timer
    public static void startSimulation(GridDisplay display) {
        System.out.println("Starting sim!");
        // Init cars
        Car EBCar = new Car(11, 0, 1, "east");
        Car NBCar = new Car(19, 11, 1, "north");
        Car SBCar = new Car(0, 8, 1, "south");
        Car[] cars = {EBCar,NBCar,SBCar};
        for (Car car : cars) {
            car.startCar();
            display.updateCarCell(car, false);
        }
        // Set up the timer
        timer = new Timer(1000, new ActionListener() {
        @Override
            public void actionPerformed(ActionEvent e) {
                // Handle car movement
                for (Car car : cars) {
                    if (car.isMoving()) {
                        display.updateCarCell(car, true);
                        car.move();
                        display.updateCarCell(car,false);
                    }
                }
            }
        });

        // Start the timer
        timer.start();
   }
}
