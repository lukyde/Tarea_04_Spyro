package dam.pmdm.spyrothedragon.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import androidx.core.content.ContextCompat;

import dam.pmdm.spyrothedragon.R;

public class DrawView extends View {

    public DrawView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {



        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);


        canvas.drawCircle(130, 230, 30, paint);

        paint.setColor(Color.YELLOW);
        canvas.drawCircle(130, 230, 20, paint);

        paint.setColor(Color.WHITE);
        canvas.drawCircle(130, 230, 5, paint);

        invalidate();
    }

}
