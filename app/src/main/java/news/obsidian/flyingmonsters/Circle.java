package news.obsidian.flyingmonsters;

import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by Bryan on 7/9/2016.
 */
public class Circle {
    private int xPos, yPos;
    private int velocityX, velocityY;
    private int background;
    private int radius;
    private RelativeLayout.LayoutParams params;
    private View view;
    private Handler handler;
    public static int canvasWidth, canvasHeight;

    public Circle( int xPos, int yPos, int velocityX,
                  int velocityY, int background, int radius,
                   RelativeLayout container, Handler handler) {
        this.handler = handler;
        this.xPos = xPos;
        this.yPos = yPos;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.background = background;
        this.radius = radius;
        view = new View(container.getContext());
        view.setBackgroundResource(this.background);
        params = new RelativeLayout.LayoutParams(radius*2, radius*2);
        container.addView(view, params);
    }

    public void move(){
        xPos += velocityX;
        yPos += velocityY;
        boolean f;
        if((f = xPos + radius >= canvasWidth)
                || xPos - radius <= 0){
            velocityX *= -1;
        if(f)
            xPos = canvasWidth - radius -1;
        else
            xPos = radius +1;
        }
        if((f= yPos + radius >= canvasHeight
        || yPos - radius <= 0)){
            velocityY *= -1;
            if(f)
                yPos = canvasHeight - radius -1;
            else
                yPos = radius + 1;
        }
    }//move()

    public boolean collide(Circle other){
        if(Math.abs(this.xPos - other.xPos)
                > this.radius + other.radius)
        return false;
        if(Math.abs(this.yPos - other.yPos)>
                this.radius+ other.radius)
            return false;
        double deltaX= this.xPos - other.xPos;
        double deltaY = this.yPos - other.yPos;
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY)
                <= this.radius + other.radius;
    }//collide()

public void flipDirection(){
    velocityX *= -1;
    velocityY *= -1;
}//flipDirection()

    public int getX() {
        return xPos;
    }

    public int getY() {
        return yPos;
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            params.setMargins(xPos - radius, yPos - radius,
                    0,0);
            view.setLayoutParams(params);
        }
    };

    public void paint(){
        handler.post(runnable);
    }
}
