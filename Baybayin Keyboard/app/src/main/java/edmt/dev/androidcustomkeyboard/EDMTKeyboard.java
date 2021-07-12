package edmt.dev.androidcustomkeyboard;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;

public class EDMTKeyboard extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    Context context;

    private KeyboardView kv;
    private Keyboard keyboard;

    private  boolean isCaps = false;


    //Press Ctrl+O
    public static final int SWITCH_KEYBOARD = -777;
    public static final int OPEN_SETTINGS = -778;


    @Override
    public View onCreateInputView() {
        kv = (KeyboardView)getLayoutInflater().inflate(R.layout.keyboard,null);
        keyboard = new Keyboard(this,R.xml.qwerty);
        kv.setKeyboard(keyboard);
        kv.setOnKeyboardActionListener(this);
        return kv;

    }

    @Override
    public void onPress(int i) {

    }

    @Override
    public void onRelease(int i) {

    }

    @Override
    public void onKey(int i, int[] ints) {

        InputConnection ic = getCurrentInputConnection();
        playClick(i);
        switch (i)
        {
            case Keyboard.KEYCODE_DELETE:
                ic.deleteSurroundingText(1,0);

            break;
            case Keyboard.KEYCODE_SHIFT:
                isCaps = !isCaps;
                keyboard.setShifted(isCaps);
                kv.invalidateAllKeys();
                break;
            case Keyboard.KEYCODE_DONE:
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_ENTER));
                break;
                default:
                    char code = (char)i;
                    if(Character.isLetter(code) && isCaps)
                        code = Character.toUpperCase(code);
                    ic.commitText(String.valueOf(code),1);
                    if (String.valueOf(code).equals("ﳷ")) {
                        ic.deleteSurroundingText(1,0);
                        switchKeyboard();
                    } else if (String.valueOf(code).equals("ﳶ")) {
                        ic.deleteSurroundingText(1,0);
                        openSettings();
                    };
        }

    }

    private void playClick(int i) {

        AudioManager am = (AudioManager)getSystemService(AUDIO_SERVICE);
        switch(i)
        {
            case 32:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                break;
            case Keyboard.KEYCODE_DONE:
            case 10:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
                break;
            case Keyboard.KEYCODE_DELETE:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE);
                break;
            default: am.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
        }
    }

    @Override public void onText(CharSequence text) {
        InputConnection ic = getCurrentInputConnection();
        ic.commitText(text, 0);
    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {
    }

    @Override
    public void swipeUp() {
    }



    public void switchKeyboard() {
        InputMethodManager imeManager = (InputMethodManager) getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
        imeManager.showInputMethodPicker();
        getApplicationContext();
        getSystemService(INPUT_METHOD_SERVICE);

    }

    public void openSettings() {

    }
}