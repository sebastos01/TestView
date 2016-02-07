
package pont.sebastien.testview.views;

import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Sébastien on 7/2/16.
 */
public class CustomView extends LinearLayout {

    public static final String TAG = "CustomView";

    private int mPreferredWidth;
    private int mExtraLabelWidth;
    private EditText mEditText;
    private TextView mTextView;

    private ViewTreeObserver.OnGlobalLayoutListener mOnGlobalListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            if (Build.VERSION.SDK_INT >= 16) {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
            } else {
                getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }

            Log.d(TAG, "Row width = " + getWidth() + ", textview = " + mExtraLabelWidth);
            int parentWidth = getWidth() - (mTextView != null ? mExtraLabelWidth : 0);
            Log.i(TAG, "preferred = " + mPreferredWidth + ", available = " + parentWidth);
            if (mPreferredWidth >= parentWidth || mPreferredWidth <= 0) {
                mEditText.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
            } else {

                mEditText.setLayoutParams(
                        new LayoutParams(mPreferredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
        }
    };

    public CustomView(Context context, int preferredWidth) {
        this(context, null, preferredWidth);
    }

    public CustomView(Context context, AttributeSet attrs, int preferredWidth) {
        this(context, attrs, 0, preferredWidth);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr, int preferredWidth) {
        super(context, attrs, defStyleAttr);
        init(preferredWidth);
    }

    private void init(int preferredWidth) {
        Log.d(TAG, "init");

        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        setOrientation(HORIZONTAL);

        mEditText = new EditText(getContext());
        mEditText.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        mEditText.setSingleLine(true);

        mTextView = new TextView(getContext());
        mTextView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        mTextView.setText("km/u");
        mExtraLabelWidth = (int) mTextView.getPaint().measureText("km/u");

        addView(mEditText);
        addView(mTextView);
        mPreferredWidth = preferredWidth > 0 ? getTextViewWidth(mEditText, preferredWidth) : 0;

        getViewTreeObserver().addOnGlobalLayoutListener(mOnGlobalListener);
    }

    public static int getTextViewWidth(TextView textView, int ems) {
        Paint paint = textView.getPaint();
        float width = paint.measureText("W");
        return (int) (width * (ems + 0.5));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            getViewTreeObserver().addOnGlobalLayoutListener(mOnGlobalListener);
        }
    }

}
