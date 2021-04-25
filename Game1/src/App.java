import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class App extends Canvas implements Runnable{
    
    private boolean isRunning;
    private Thread thread;

    public static JFrame frame;
    private final int width = 240;
    private final int height = 160;
    private final int scale = 3;
    private BufferedImage image;
    
    public App(){
        this.setPreferredSize(new Dimension(width*scale,height*scale));
        frame = new JFrame("Game1");
        frame.add(this);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

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
        stop();
    }

    public synchronized void start(){
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }

    public synchronized void stop(){
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            
        }
    }

    public void update(){
        //System.out.println("update");
    }

    public void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if  (bs == null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = image.getGraphics();
        //REFRESH NA TELA... LIMPANDO TUDO COM FUNDO DA TELA
        g.setColor(new Color(12,12,12)); //cor de fundo da tela
        g.fillRect(0, 0, width, height); //desenhado um retangulo do tamanho da tela

        /* EXEMPLO DE COMO MUDAR ESSE FUNDO (COM TEXTO)
        g.setColor(Color.CYAN);
        g.fillRect(80, 40, width/3, height/3);

        g.setColor(Color.MAGENTA);
        g.setFont(new Font("Arial",Font.BOLD, 20));
        g.drawString("Game1", 85, 60);
        */
        
        g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, width*scale, height*scale, null); //jogando desenho na tela
        bs.show();

    }
}
