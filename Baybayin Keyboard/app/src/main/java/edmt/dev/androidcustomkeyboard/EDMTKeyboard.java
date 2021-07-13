 package edmt.dev.androidcustomkeyboard;

 import android.content.Context;
import android.content.Intent;
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

    //ArrayList<String> katinig = new ArrayList<String>() {
    //    {
    //        //add("ᜀ");
    //        add("ᜊ");
    //        add("ᜃ");
    //        add("ᜇ");
    //        //add("ᜁ");
    //        add("ᜄ");
    //       add("ᜑ");
    //        add("ᜎ");
    //        add("ᜋ");
    //        add("ᜈ");
    //        add("ᜅ");
    //        //add("ᜂ");
    //        add("ᜉ");
    //        add("ᜐ");
    //        add("ᜆ");
    //        add("ᜏ");
    //        add("ᜌ");
    //    }
    //};


    @Override
    public View onCreateInputView() { //sets up the keyboard
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
    public void onKey(int i, int[] ints) { //makes the simple keys on keyboard work

        InputConnection ic = getCurrentInputConnection();
        playClick(i);
        switch (i)
        {
            case Keyboard.KEYCODE_DELETE: //backspace
                ic.deleteSurroundingText(1,0);
                break;

            case Keyboard.KEYCODE_DONE: //exit keyboard / enter
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_ENTER));
                break;

                default:
                    char code = (char)i;
                    ic.commitText(String.valueOf(code),1); //types out the key

                    if (String.valueOf(code).equals("ﳷ")) { //special key for chagne keyboard button
                        ic.deleteSurroundingText(1,0);
                        switchKeyboard();
                    } else if (String.valueOf(code).equals("ﳶ")) { //special key for open settings
                        ic.deleteSurroundingText(1,0);
                        openSettings();
                    };
                    //Button button = keyboard.;
                   // if (katinig.contains(code)) {
                    //    //ei.setBackgroundColor(ei.getContext().getResources().getColor(R.color.red));
                    //} else {
                        //ei.setBackgroundColor(ei.getContext().getResources().getColor(R.color.red));
                    //}
        }

    }

    private void playClick(int i) {

        AudioManager am = (AudioManager)getSystemService(AUDIO_SERVICE); //audio
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

    @Override public void onText(CharSequence text) { //types out the Ra and Chakma virama/big virama
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
            Intent intent = new Intent(this, settings.class);
            startActivity(intent);
    }

}

 //class MKeyboardView extends KeyboardView {
//
//     public MKeyboardView(Context context, AttributeSet attrs) {
//         super(context, attrs);
//         System.out.println("AAAAAAA");
//     }
//
//     @Override
//     public void onDraw(Canvas canvas) {
 //        super.onDraw(canvas);

//         System.out.println("BBBBBBBB");

 //        Paint paint = new Paint();
  //       paint.setTextSize(15);
  //       paint.setColor(Color.GRAY);

    //     List<Keyboard.Key> keys = getKeyboard().getKeys();
   //      for(Keyboard.Key key: keys) {
   //          System.out.println("CCCCCCCCC");
    //         canvas.drawText("1", key.x + (key.width/2), key.y + 25, paint);
      //   }
  //   }
// }