package Duelyst.Utility;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class ImageHolder {
   private static ArrayList<ImageHolder> imageHolders = new ArrayList<>();

   private String addressOfImage;
   private Image image;


    public ImageHolder(String addressOfImage, Image image) {
        this.addressOfImage = addressOfImage;
        this.image = image;
        getImageHolders().add(this);
    }

    public ImageHolder(String addressOfImage)
    {
        this(addressOfImage,new Image(addressOfImage));
    }

    public static Image findImageInImageHolders(String addressOfImage)
    {
        for (int i =0;i<getImageHolders().size();i++)
        {
            ImageHolder imageHolder = getImageHolders().get(i);
            if (imageHolder!=null && imageHolder.getAddressOfImage().equals(addressOfImage))
            {
                return imageHolder.getImage();
            }
        }
        ImageHolder imageHolder = new ImageHolder(addressOfImage);
        return imageHolder.getImage();
    }


    public static ArrayList<ImageHolder> getImageHolders() {
        return imageHolders;
    }

    public String getAddressOfImage() {
        return addressOfImage;
    }

    public void setAddressOfImage(String addressOfImage) {
        this.addressOfImage = addressOfImage;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
