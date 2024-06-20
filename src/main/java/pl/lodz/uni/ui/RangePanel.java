package pl.lodz.uni.ui;

import pl.lodz.uni.core.controller.Presenter;

import javax.swing.*;
import java.awt.*;

public class RangePanel extends JPanel implements Presenter {

    private JLabel nameLabel;
    private JLabel valueLabel;
    private JProgressBar progressBar;

    public RangePanel() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());

        nameLabel = new JLabel("Name:");
        GridBagConstraints gbcNameLabel = new GridBagConstraints();
        gbcNameLabel.anchor = GridBagConstraints.WEST;
        gbcNameLabel.insets = new Insets(5, 10, 5, 5);
        gbcNameLabel.gridx = 0;
        gbcNameLabel.gridy = 0;
        add(nameLabel, gbcNameLabel);

        valueLabel = new JLabel("Value:");
        GridBagConstraints gbcValueLabel = new GridBagConstraints();
        gbcValueLabel.anchor = GridBagConstraints.WEST;
        gbcValueLabel.insets = new Insets(5, 5, 5, 10);
        gbcValueLabel.gridx = 1;
        gbcValueLabel.gridy = 0;
        add(valueLabel, gbcValueLabel);

        progressBar = new JProgressBar();
        progressBar.setMaximum(100);
        progressBar.setStringPainted(false); // disable % values on bar
        progressBar.setPreferredSize(new Dimension(200, 20)); // Fixed width
        GridBagConstraints gbcProgressBar = new GridBagConstraints();
        gbcProgressBar.fill = GridBagConstraints.HORIZONTAL;
        gbcProgressBar.gridwidth = 2;
        gbcProgressBar.insets = new Insets(5, 10, 5, 10);
        gbcProgressBar.gridx = 0;
        gbcProgressBar.gridy = 1;
        add(progressBar, gbcProgressBar);

        setPreferredSize(new Dimension(240, 80));
        setMinimumSize(new Dimension(240, 80));
        setMaximumSize(new Dimension(240, 80));
    }

    @Override
    public void setName(String name) {
        nameLabel.setText(name);
    }

    @Override
    public void setValue(String value) {
        valueLabel.setText(value);
    }

    @Override
    public void setProgress(int progress) {
        progressBar.setValue(progress);
    }
}