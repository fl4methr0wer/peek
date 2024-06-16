package pl.lodz.uni.ui;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    public Window(String title) {
        super(title);
        setVisible(true);
        setSize(new Dimension(300, 400));
    }
}
