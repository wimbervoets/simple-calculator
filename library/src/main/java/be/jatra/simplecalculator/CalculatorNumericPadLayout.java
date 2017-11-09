package be.jatra.simplecalculator;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import java.text.DecimalFormatSymbols;
import java.util.Locale;

import static be.jatra.simplecalculator.util.Utils.hasLollipop;

public class CalculatorNumericPadLayout extends CalculatorPadLayout {

    public CalculatorNumericPadLayout(Context context) {
        this(context, null);
    }

    public CalculatorNumericPadLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalculatorNumericPadLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void onFinishInflate() {
        super.onFinishInflate();

        Locale locale = getResources().getConfiguration().locale;
        if (hasLollipop() && !getResources().getBoolean(R.bool.use_localized_digits)) {
            locale = new Locale.Builder()
                    .setLocale(locale)
                    .setUnicodeLocaleKeyword("nu", "latn")
                    .build();
        }

        final DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(locale);
        final char zeroDigit = symbols.getZeroDigit();
        for (int childIndex = getChildCount() - 1; childIndex >= 0; --childIndex) {
            final View v = getChildAt(childIndex);
            if (v instanceof Button) {
                final Button b = (Button) v;
                if (b.getId() == R.id.digit_0) {
                    b.setText(String.valueOf(zeroDigit));
                } else if (b.getId() == R.id.digit_1) {
                    b.setText(String.valueOf((char) (zeroDigit + 1)));
                } else if (b.getId() == R.id.digit_2) {
                    b.setText(String.valueOf((char) (zeroDigit + 2)));
                } else if (b.getId() == R.id.digit_3) {
                    b.setText(String.valueOf((char) (zeroDigit + 3)));
                } else if (b.getId() == R.id.digit_4) {
                    b.setText(String.valueOf((char) (zeroDigit + 4)));
                } else if (b.getId() == R.id.digit_5) {
                    b.setText(String.valueOf((char) (zeroDigit + 5)));
                } else if (b.getId() == R.id.digit_6) {
                    b.setText(String.valueOf((char) (zeroDigit + 6)));
                } else if (b.getId() == R.id.digit_7) {
                    b.setText(String.valueOf((char) (zeroDigit + 7)));
                } else if (b.getId() == R.id.digit_8) {
                    b.setText(String.valueOf((char) (zeroDigit + 8)));
                } else if (b.getId() == R.id.digit_9) {
                    b.setText(String.valueOf((char) (zeroDigit + 9)));
                } else if (b.getId() == R.id.dec_point) {
                    b.setText(String.valueOf(symbols.getDecimalSeparator()));
                }
            }
        }
    }
}

