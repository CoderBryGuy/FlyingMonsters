package news.obsidian.flyingmonsters;

import android.graphics.Point;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RelativeLayout layout;
    RelativeLayout.LayoutParams params;
    Handler handler = new Handler();
    boolean go = true;
    Thread thread;
    List<Circle> circles;
    final static int[] backgrounds=
            {R.drawable.circle_blue, R.drawable.circle_green,
            R.drawable.circle_red};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = (RelativeLayout)findViewById(R.id.layout);
        params = new RelativeLayout.LayoutParams(200,200);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Circle.canvasWidth = size.x;
        Circle.canvasHeight = size.y - 300;
        circles = new ArrayList<>();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        go = true;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(go){
                    for(Circle c : circles){
                        c.move();
                    }
                    for (int i = 0; i <circles.size() -1 ; i++) {
                        Circle c1 = circles.get(i);
                        for (int j = i+1; j < circles.size(); j++) {
                            Circle c2 = circles.get(j);
                            if(c1.collide(c2)){
                                c1.flipDirection();
                                c2.flipDirection();
                            }
                        }
                    }
                    for(Circle c : circles)
                    {
                        c.paint();
                    }
                    try{
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }//onResume

    @Override
    protected void onPause() {
        super.onPause();
        go = false;
        thread.interrupt();
        thread = null;
    }


    public void btnAddCircle(View view) {
        circles.add(new Circle(120,130, 13, 8,
                backgrounds[circles.size()%backgrounds.length], 100,
                layout, handler));
    }
}
