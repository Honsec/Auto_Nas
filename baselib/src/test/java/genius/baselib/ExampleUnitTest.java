package genius.baselib;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }



//    String send_uri_txt = extras.getString(Intent.EXTRA_TEXT, "");
//    Uri send_uri_stream = extras.getParcelable(Intent.EXTRA_STREAM);
//    ParcelFileDescriptor file = baseActivity.getContentResolver().openFileDescriptor(send_uri_stream, "rw");
//    Bitmap decodeStream = BitmapFactory.decodeFileDescriptor(file.getFileDescriptor());
//    <intent-filter>
//    <action android:name="android.intent.action.SEND" />
//
//    <category android:name="android.intent.category.DEFAULT" />
//
//    <data android:mimeType="text/plain" />
//    </intent-filter>
//    <intent-filter>
//    <action android:name="android.intent.action.SEND" />
//
//    <category android:name="android.intent.category.DEFAULT" />
//
//    <data android:mimeType="image/jpeg" />
//    </intent-filter>

}