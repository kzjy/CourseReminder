package kz.coursereminder.adapters;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class CourseLinearLayout extends LinearLayout {

    public CourseLinearLayout(Context context) {
        super(context);
    }

    public CourseLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CourseLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec); // This is the key that will make the height equivalent to its width
    }

}
