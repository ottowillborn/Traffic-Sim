import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GridDisplay extends JFrame {
    private final int ROWS = 20;
    private final int COLS = 20;
    private JLayeredPane[][] gridCells;
    public boolean simRunning = false;
    JPanel gridPanel = new JPanel(new GridLayout(ROWS, COLS)); // Grid with spacing

    public GridDisplay() {
        setTitle("Grid Display");
        setSize(800, 825);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        gridCells = new JLayeredPane[ROWS][COLS];
        int[][] StartingLayout = {
                { 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'R', 'R', 'R', 'R', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O' },
                { 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'R', 'R', 'R', 'R', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O' },
                { 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'R', 'R', 'R', 'R', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O' },
                { 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'R', 'R', 'R', 'R', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O' },
                { 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'R', 'R', 'R', 'R', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O' },
                { 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'R', 'R', 'R', 'R', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O' },
                { 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'R', 'R', 'R', 'R', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O' },
                { 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'R', 'R', 'R', 'R', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O' },
                { 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R' },
                { 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R' },
                { 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R' },
                { 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R' },
                { 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'R', 'R', 'R', 'R', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O' },
                { 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'R', 'R', 'R', 'R', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O' },
                { 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'R', 'R', 'R', 'R', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O' },
                { 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'R', 'R', 'R', 'R', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O' },
                { 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'R', 'R', 'R', 'R', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O' },
                { 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'R', 'R', 'R', 'R', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O' },
                { 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'R', 'R', 'R', 'R', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O' },
                { 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'R', 'R', 'R', 'R', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O' }
        };
        // Initialize grid cells
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                switch (StartingLayout[row][col]) {
                    case 'O': // Grass
                        JLayeredPane grassLayeredPane = new JLayeredPane();
                        grassLayeredPane.setPreferredSize(new Dimension(40, 40)); // Set fixed size for the cell

                        // Create grass label
                        JLabel grassLabelSolo = new JLabel(resizeImage("Assets/grass.png", 40, 40));
                        grassLabelSolo.setBounds(0, 0, 40, 40); // Set to full panel size

                        // Add grass label to layered pane
                        grassLayeredPane.add(grassLabelSolo, Integer.valueOf(1)); // Grass as background

                        gridCells[row][col] = grassLayeredPane; // Replace the previous label with the layered pane
                        gridPanel.add(grassLayeredPane); // Add layered pane to the grid panel
                        break;

                    case 'R': // Road
                        JLayeredPane roadLayeredPane = new JLayeredPane();
                        roadLayeredPane.setPreferredSize(new Dimension(40, 40)); // Set fixed size for the cell

                        // Create road label
                        JLabel roadLabelSolo = new JLabel(resizeImage("Assets/road.jpg", 40, 40));
                        roadLabelSolo.setBounds(0, 0, 40, 40); // Set to full panel size

                        // Add road label to layered pane
                        roadLayeredPane.add(roadLabelSolo, Integer.valueOf(1)); // Road as background

                        gridCells[row][col] = roadLayeredPane; // Replace the previous label with the layered pane
                        gridPanel.add(roadLayeredPane); // Add layered pane to the grid panel
                        break;
                }
            }
        }
        // Create a panel for buttons at the bottom
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Center buttons

        // Create buttons and add them to the button panel
        JButton startButton = new JButton("Start");
        JButton button2 = new JButton("Button 2");
        JButton button3 = new JButton("Button 3");
        startButton.setPreferredSize(new Dimension(100, 25)); // 100px width, 50px height
        button2.setPreferredSize(new Dimension(100, 25)); // 100px width, 50px height
        button3.setPreferredSize(new Dimension(100, 25)); // 100px width, 50px height

        buttonPanel.add(startButton);
        buttonPanel.add(button2);
        buttonPanel.add(button3);

        // Add ActionListener for Button 1
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simRunning = true;
            }
        });

        // Add grid panel to the center and button panel to the bottom
        add(gridPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    // Method to resize an image to fit the JLabel
    private ImageIcon resizeImage(String imagePath, int width, int height) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    public void updateLightCell(TrafficLight light){
        gridPanel.remove(gridCells[light.getX()][light.getY()]);
        // Create a new JLayeredPane based on the new state
        JLayeredPane newLayeredPane = new JLayeredPane();
        newLayeredPane.setPreferredSize(new Dimension(40, 40)); // Set fixed size for the cell

        JLabel grassLabelForLight = new JLabel(resizeImage("Assets/grass.png", 40, 40));
        grassLabelForLight.setBounds(0, 0, 40, 40);
        String path = "";
        switch (light.getColor()) {
            case LightColor.RED:
                path = "Assets/redSL.png";
                break;
            case LightColor.YELLOW:
                path = "Assets/yellowSL.png";
                break;
            case LightColor.GREEN:
                path = "Assets/greenSL.png";
                break;
        }
        int rotationAngle = 0;
        switch (light.getFacingDirection()) {
            case "N":
                rotationAngle = 180;
                break;
            case "S":
                rotationAngle = 0;
                break;
            case "E":
                rotationAngle = 270;
                break;
            case "W":
                rotationAngle = 90;
                break;
            default:
                break;
        }
        JLabel lightLabel = new JLabel(resizeAndRotateImage(path, 40, 40, rotationAngle));
        lightLabel.setBounds(0, 0, 40, 40);
        newLayeredPane.add(grassLabelForLight, Integer.valueOf(1)); // grass background
        newLayeredPane.add(lightLabel, Integer.valueOf(2));
         // Replace the old cell with the new one in the grid panel
         gridCells[light.getX()][light.getY()] = newLayeredPane;
         gridPanel.add(newLayeredPane, light.getX() * COLS + light.getY()); // Re-add the new cell to the grid
         gridPanel.revalidate();
         gridPanel.repaint();
    }
    // Method to update a car on grid
    public void updateCarCell(Car car, boolean removeCar) {
        // Remove the current cell from the grid
        gridPanel.remove(gridCells[car.getX()][car.getY()]);

        // Create a new JLayeredPane based on the new state
        JLayeredPane newLayeredPane = new JLayeredPane();
        newLayeredPane.setPreferredSize(new Dimension(40, 40)); // Set fixed size for the cell

        JLabel roadLabelForCar = new JLabel(resizeImage("Assets/road.jpg", 40, 40));
        roadLabelForCar.setBounds(0, 0, 40, 40);
        int rotationAngle = 0;

        switch (car.getDirection()) {
            case "north":
                rotationAngle = 90;
                break;
            case "south":
                rotationAngle = 270;
                break;
            case "east":
                rotationAngle = 180;
                break;
            case "west":
                rotationAngle = 0;
                break;
            case "northeast":
                rotationAngle = 135;
                break;
            case "southwest":
                rotationAngle = 315;
                break;
            case "southeast":
                rotationAngle = 225;
                break;
            case "northwest":
                rotationAngle = 45;
                break;
            default:
                break;
        }
        JLabel carLabel = new JLabel(resizeAndRotateImage(car.getImagePath(), 40, 40, rotationAngle)); // Rotate car
        // image
        carLabel.setBounds(0, 0, 40, 40);
        newLayeredPane.add(roadLabelForCar, Integer.valueOf(1)); // Road background
        // if calling after car moved, replace with road only
        if (!removeCar) {
            newLayeredPane.add(carLabel, Integer.valueOf(2)); // Car foreground
        }
        // Replace the old cell with the new one in the grid panel
        gridCells[car.getX()][car.getY()] = newLayeredPane;
        gridPanel.add(newLayeredPane, car.getX() * COLS + car.getY()); // Re-add the new cell to the grid
        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private ImageIcon resizeAndRotateImage(String imagePath, int width, int height, double angle) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image img = icon.getImage();

        // Create a buffered image
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();

        // Enable anti-aliasing for smoother rotation
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // Rotate the image
        g2d.rotate(Math.toRadians(angle), width / 2.0, height / 2.0);
        g2d.drawImage(img, 0, 0, width, height, null);
        g2d.dispose();

        return new ImageIcon(bufferedImage);
    }

}
