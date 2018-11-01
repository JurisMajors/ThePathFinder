import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;


public class main implements ActionListener,ChangeListener  {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private JFrame frame;
    private JPanel control_panel;
    private JSlider size_slider;
    private JButton reset;
    private JButton reset_path;
    private JButton runner;
    private JButton generator;
    private JComboBox pathfinders;
    private JTextField size;
    private String[] algorithms = { "A*", "Djikstra"};
    private World world;
    private PathFinder pathfinder;


    main(){
        prepareGUI(); }


    void prepareGUI(){
        frame = new JFrame("The Pathfinder");
        control_panel = new JPanel();
        size_slider = new JSlider(JSlider.HORIZONTAL, 10, 100, 50);
        size = new JTextField("50");
        reset = new JButton("RESET");
        runner = new JButton("RUN");
        reset_path = new JButton("Remove Path");
        generator = new JButton("Generate Maze");
        world = new World(size_slider.getValue());
        pathfinders = new JComboBox(algorithms);
        pathfinder = new PathFinder(world, (String)pathfinders.getSelectedItem());

        frame.setSize(WIDTH, HEIGHT);
//        frame.setLayout(new GridLayout(2, 1));
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });

        size_slider.setMajorTickSpacing(20);
        size_slider.setMinorTickSpacing(2);
        size_slider.setPaintTicks(true);

        size.setPreferredSize(new Dimension(50, 20));

        size_slider.addChangeListener(this);
        pathfinders.addActionListener(this);
        runner.addActionListener(this);
        reset.addActionListener(this);
        reset_path.addActionListener(this);
        generator.addActionListener(this);

        // add everything to panel
        control_panel.setLayout(new FlowLayout());
        control_panel.add(new JLabel("Select Size"));
        control_panel.add(size);
        control_panel.add(size_slider, BorderLayout.SOUTH);
        control_panel.add(generator);
        control_panel.add(pathfinders);
        control_panel.add(reset);
        control_panel.add(reset_path);
        control_panel.add(runner);
        // add panel to frame
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
                    break;
                case "Djikstra":
                    pathfinder.setAlgorithm(algorithms[1]);
                    break;

            }
        }else if(e.getSource() == runner){
            pathfinder.run();
        }else if(e.getSource() == reset){
            this.world.reset(size_slider.getValue());
        }else if(e.getSource() == reset_path){
            this.world.resetPath();
        }else if(e.getSource() == generator){
            this.world.reset(size_slider.getValue());
            this.world.generate();
        }
        this.world.repaint();


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
