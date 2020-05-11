//Light-Bright: Divide the drawing panel into a grid (the size of which should be controlled by command line arguments and
//        defaulting to 50 by 50). Each cell of the grid should start off black. If a cell is "clicked" or "dragged" over
//        then it should toggle its color between white and black. Drawn thin grey lines between each cell of the grid.
//        Note that the size of the cells will stay fixed, so rows and columns will have to be "added" or "removed" as the
//        frame is resized.
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class LightBright extends JFrame {
    private static double rectHeight = 50;
    private static double rectWidth = 50;

    public static class DrawingPanel extends JPanel {

        public int cellX=-1;
        public int cellY=-1;
        public Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        public double width = screenSize.getWidth();
        public double height = screenSize.getHeight();
        private int ROWS = (int) (height / rectHeight);
        private int COLS = (int) (width / rectWidth);

        public int x = 0;
        public int y= 0;

        public boolean[][] isWhite=new boolean[COLS+1][ROWS+1];
        public DrawingPanel() {
            addMouseListener(new MouseAdapter()
            {
                @Override
                public void mousePressed(MouseEvent e)
                {
                    x=e.getX();
                    y=e.getY();
                    isWhite[(int)(x/rectWidth)][(int)(y/rectHeight)]=!isWhite[(int)(x/rectWidth)][(int)(y/rectHeight)];
                    cellX= (int) (x/rectWidth);
                    cellY=(int)(y/rectHeight);
                    repaint();
                }
            });
            addMouseMotionListener(new MouseMotionListener() {
                @Override
                public void mouseDragged(MouseEvent event) {
                    x=event.getX();
                    y=event.getY();
                    if((int)(x/rectWidth) != cellX || (int)(y/rectHeight)!=cellY ){
                        isWhite[(int) (x / rectWidth)][(int) (y / rectHeight)] = !isWhite[(int) (x / rectWidth)][(int) (y / rectHeight)];
                        cellX= (int) (x/rectWidth);
                        cellY=(int)(y/rectHeight);
                    }
                    repaint();
                }
                @Override
                public void mouseMoved(MouseEvent mouseEvent) {
                }
            } );
        }
        @Override
        protected void paintComponent(Graphics graphic) {
            super.paintComponent(graphic);
            squaresDrawing(graphic);
        }
        public void squaresDrawing(Graphics graphic) {
            Graphics2D brick = (Graphics2D) graphic.create();
            double x = 0;
            double y = 0;
            for (int i = 0; i <= ROWS; i++) {
                for (int j = 0; j <= COLS; j++) {
                    if (!isWhite[j][i]){
                        brick.setColor(Color.BLACK);
                    }else{
                        brick.setColor(Color.white);
                    }
                    Rectangle2D.Double rect = new Rectangle2D.Double(x, y, rectWidth, rectHeight);
                    brick.fill(rect);
                    brick.setColor(Color.gray);
                    brick.draw(rect);
                    x += rectWidth;
                }
                repaint();
                x = 0;
                y += rectHeight;
            }
        }
    }
    public LightBright(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,800);
        add(new DrawingPanel());
    }
    public static void main(String[] args){
        if (args.length>0) {
            try {
                rectHeight = Integer.parseInt(args[0]);
                rectWidth = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.out.print("The value you entered is not a number");
                System.exit(1);
            }
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LightBright().setVisible(true);
            }
        });
    }
}
