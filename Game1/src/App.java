import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;

public class App extends Canvas implements Runnable{
    
    private boolean isRunning;
    private Thread thread;

    public static JFrame frame;
    private final int width = 160;
    private final int height = 120;
    private final int scale = 7;
    
    public App(){
        this.setPreferredSize(new Dimension(width*scale,height*scale));
        frame = new JFrame("Game1");
        frame.add(this);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        App app = new App();
        app.start();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000/amountOfTicks;
        double delta = 0;
        int frames = 0;
        double timer = System.currentTimeMillis();
        while(isRunning){
            long now = System.nanoTime();
            delta += (now -lastTime) / ns;
            lastTime = now;
            if (delta >= 1) {
               update(); 
               render();    
               delta--;
               frames++;
            }
           
            if(System.currentTimeMillis() - timer >= 1000){
                System.out.println("FPS: " + frames);
                frames = 0;
                timer+=1000;
            }

        }
    }

    public synchronized void start(){
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }

    public synchronized void stop(){
        thread = new Thread(this);
        thread.start();
    }

    public void update(){
        //System.out.println("update");
    }

    public void render(){
        //System.out.println("render");
    }
}
