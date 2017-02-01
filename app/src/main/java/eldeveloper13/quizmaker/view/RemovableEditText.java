package eldeveloper13.quizmaker.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eldeveloper13.quizmaker.R;

public class RemovableEditText extends LinearLayout {

    @BindView(R.id.editText1)
    EditText mEditText;
    @BindView(R.id.button_remove)
    ImageView mRemoveButton;

    OnRemoveButtonClickedListener mOnRemoveButtonClickedListener = null;

    public RemovableEditText(Context context) {
        super(context);
        init(null);
    }

    public RemovableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public RemovableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RemovableEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    public void setRemoveButtonVisible(boolean visible) {
        if (visible) {
            mRemoveButton.setVisibility(VISIBLE);
            mRemoveButton.setEnabled(true);
        } else {
            mRemoveButton.setVisibility(INVISIBLE);
            mRemoveButton.setEnabled(false);
        }
    }

    public void setText(CharSequence text) {
        mEditText.setText(text);
    }

    public Editable getText() {
        return mEditText.getText();
    }

    public void setOnRemoveButtonClickedListener(OnRemoveButtonClickedListener listener) {
        mOnRemoveButtonClickedListener = listener;
    }

    public void setHint(CharSequence hint) {
        mEditText.setHint(hint);
    }

    @OnClick(R.id.button_remove)
    public void onRemoveButtonClicked() {
        if (mOnRemoveButtonClickedListener != null) {
            mOnRemoveButtonClickedListener.onClicked();
        }
    }

    private void init(AttributeSet attrs) {
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_removable_edit_text, this, true);
        ButterKnife.bind(this, view);

        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.RemovableEditText);
            String text = typedArray.getString(R.styleable.RemovableEditText_text);
            String hint = typedArray.getString(R.styleable.RemovableEditText_hint);
            Boolean visible = typedArray.getBoolean(R.styleable.RemovableEditText_removeButtonVisible, false);

            mEditText.setText(text);
            mEditText.setHint(hint);
            setRemoveButtonVisible(visible);
        }
    }

    public interface OnRemoveButtonClickedListener {
        void onClicked();
    }
}
