import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class WeatherAppGui extends JFrame {
    public WeatherAppGui(){
        //set up our gui and add a title
        super("Weather App");
        // configure gui to end the program's process once it's been closed
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //set the size of our gui in pixels:
        setSize(450, 650);

        //load our gui at the center of the screen
        setLocationRelativeTo(null);

        //make our layout manager null to manually position our components within the gui
        setLayout(null);

        // prevent any resize of our gui
        setResizable(false);

        addGuiComponents();
    }

    private void addGuiComponents(){
        //search field
        JTextField searchTextField = new JTextField();

        //set location and size of the component
        searchTextField.setBounds(15, 15, 351, 45);

        //change font style and size
        searchTextField.setFont(new Font("Dialog", Font.PLAIN, 24));

        add(searchTextField);

        //search button
        JButton searchButton = new JButton(loadImage("src/assets/search.png"));

        // change the cursor to a hand when hovering over this button:
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBounds(375, 13, 47, 45);
        add(searchButton);

        //weather images
        JLabel weatherConditionImage = new JLabel(loadImage("src/assets/cloudy.png"));
        weatherConditionImage.setBounds(0, 125, 450, 217);
        add(weatherConditionImage);

        //temperature text
        JLabel temperatureText = new JLabel("10 C");
        temperatureText.setBounds(0, 350, 450, 54);
        temperatureText.setFont(new Font("Dialog", Font.BOLD, 48));

        // center the text
        temperatureText.setHorizontalAlignment(SwingConstants.CENTER);
        add(temperatureText);
    }

    //used to create images in our gui component:
    private ImageIcon loadImage(String resourcePath){
        try{
            //read the image file from the path given
            BufferedImage image = ImageIO.read(new File(resourcePath));

            //returns an image icon so that our component can render it;
            return new ImageIcon(image);
        }catch(IOException e){
            e.printStackTrace();
        }
        System.out.println("Could not find resource");
        return null;
    }
}
