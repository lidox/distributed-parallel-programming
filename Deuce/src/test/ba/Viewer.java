package ba;



import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Viewer {
    private BufferedImage image;

    public Viewer() {
        image = new BufferedImage(600,600,1);
	    image.flush();
    }


    public void writeImage(){
      
      try {
          ImageIO.write(image, "jpg", new File("src/test/ba/Image.jpg"));
      } catch (IOException e) {
          e.printStackTrace();
      }
    }
    
    public void drawPoint(int x,int y,int col){
      image.setRGB(x,y,col);
    }
}
