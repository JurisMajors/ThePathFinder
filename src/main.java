import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;

import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;

public class main implements ActionListener,ChangeListener  {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private JFrame frame;
    private JPanel control_panel;
    private JSlider size_slider;
    private World world;
    private JButton runner;
    private JComboBox pathfinders;
    private JTextField size;
    private String[] algorithms = { "A*", "Djikstra"};
    private PathFinder pathfinder;


    main(){
        prepareGUI(); }


    void prepareGUI(){
        frame = new JFrame("The Pathfinder");
        frame.setSize(WIDTH, HEIGHT);
//        frame.setLayout(new GridLayout(2, 1));
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });
        control_panel = new JPanel();
        size_slider = new JSlider(JSlider.HORIZONTAL, 10, 100, 50);
        size_slider.setMajorTickSpacing(20);
        size_slider.setMinorTickSpacing(2);
        size_slider.setPaintTicks(true);
        size_slider.addChangeListener(this);
        size = new JTextField("50");
        size.setPreferredSize(new Dimension(50, 20));
        pathfinders = new JComboBox(algorithms);
        pathfinders.addActionListener(this);
        runner = new JButton("RUN");
        runner.addActionListener(this);

        // add everything to panel
        control_panel.setLayout(new FlowLayout());
        control_panel.add(new JLabel("Select Size"));
        control_panel.add(size);
        control_panel.add(size_slider, BorderLayout.SOUTH);
        control_panel.add(pathfinders);
        control_panel.add(runner);
        // add panel to frame
        world = new World(size_slider.getValue());
        pathfinder = new PathFinder(world, (String)pathfinders.getSelectedItem());
        frame.add(control_panel, BorderLayout.SOUTH);
        frame.add(world);
        world.repaint();
        frame.setVisible(true);


    }
    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == pathfinders){
            JComboBox cb = (JComboBox)e.getSource();
            String selected = (String)cb.getSelectedItem();
            switch(selected){
                case "A*":
                    pathfinder.setAlgorithm(algorithms[0]);
                    System.out.println(algorithms[0]);
                    break;
                case "Djikstra":
                    pathfinder.setAlgorithm(algorithms[1]);
                    System.out.println(algorithms[1]);
                    break;

            }
        }else if(e.getSource() == runner){
            System.out.println("RUNNING");
            pathfinder.run();
        }


    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider s = (JSlider) e.getSource();
        size.setText("" + s.getValue());
        this.world.reset(s.getValue());
        this.world.repaint();
    }

    public static void main(String[] args) {
        new main();
    }

}
