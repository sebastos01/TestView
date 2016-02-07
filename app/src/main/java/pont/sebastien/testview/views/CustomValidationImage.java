
package pont.sebastien.testview.views;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by Sébastien on 7/2/16.
 */
public class CustomValidationImage extends ImageView {

    public CustomValidationImage(Context context) {
        super(context);
        init();
    }

    public CustomValidationImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomValidationImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        setImageResource(android.R.drawable.ic_menu_delete);
        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibility(View.GONE);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setVisibility(View.VISIBLE);
                    }
                }, 4000);
            }
        });
    }
}
