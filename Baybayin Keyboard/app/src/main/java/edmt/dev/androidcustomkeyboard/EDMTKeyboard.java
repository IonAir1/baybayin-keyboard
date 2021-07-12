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

import java.util.ArrayList;

public class EDMTKeyboard extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    Context context;

    private KeyboardView kv;
    private Keyboard keyboard;

    private  boolean isCaps = false;

    char pubCode;
    String currWord = "";
    ArrayList<String> diacritics = new ArrayList<String>() {
        {
            add("ᜒ");
            add("ᜓ");
            add("᜔");
            add("᜴");
            add("\uD804\uDF33");
            add(" ");
        }
    };
    ArrayList<String> titik = new ArrayList<String>() {
        {
            //add("ᜀ");
            add("ᜊ");
            add("ᜃ");
            add("ᜇ");
            //add("ᜁ");
            add("ᜄ");
            add("ᜑ");
            add("ᜎ");
            add("ᜋ");
            add("ᜈ");
            add("ᜅ");
            //add("ᜂ");
            add("ᜉ");
            add("ᜐ");
            add("ᜆ");
            add("ᜏ");
            add("ᜌ");
        }
    };



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
                    pubCode = code;
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

        String texto = currWord + text;
        currWord = texto;

        if (titik.contains(String.valueOf(texto))) {
            currWord = String.valueOf(text);
            texto = currWord;
            ic.setComposingText(String.valueOf(texto), 1);
        }

        if (diacritics.contains(String.valueOf(text))) {
            ic.commitText(String.valueOf(texto),1);
            currWord = "";
        }
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