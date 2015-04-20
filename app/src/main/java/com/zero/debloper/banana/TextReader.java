package com.zero.debloper.banana;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;

import java.util.HashMap;
import java.util.Locale;

/**
 * Created by debloper on 20/4/15.
 */
public class TextReader {
    private TextToSpeech mTextToSpeech;
    private TextToSpeech.OnInitListener mOnInitListener;
    private UtteranceProgressListener mUtteranceProgressListener;
    private int mMaxSpeechInputLength;
    private int mSpeechCount;
    private static TextReader mTextReader = null;
    private final String TAG = "TextReader";

    private TextReader(Context context) {
        mOnInitListener = new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    mTextToSpeech.setLanguage(Locale.getDefault());
                    // mMaxSpeechInputLength =
                    // mTextToSpeech.getMaxSpeechInputLength();
                    mSpeechCount = 0;
                    mUtteranceProgressListener = new UtteranceProgressListener() {
                        @Override
                        public void onStart(String utteranceId) {
                            Log.d(TAG, "Reading " + utteranceId + " started.");
                        }

                        @Override
                        @Deprecated
                        public void onError(String utteranceId) {
                            Log.d(TAG, "Reading " + utteranceId + " failed.");
                        }

                        @Override
                        public void onDone(String utteranceId) {
                            Log.d(TAG, "Reading " + utteranceId + " completed.");
                        }
                    };

                    mTextToSpeech.setOnUtteranceProgressListener(mUtteranceProgressListener);
                    mMaxSpeechInputLength = mTextToSpeech.getMaxSpeechInputLength();
                    Log.v(TAG, "Max Speech Input Length is " + mMaxSpeechInputLength);
                    check();
                    Log.d(TAG, "TextReader initialized successfully.");
                } else {
                    Log.d(TAG, "TextReader initialization failed.");
                    mTextToSpeech = null;
                }
            }
        };

        mTextToSpeech = new TextToSpeech(context, mOnInitListener);
    }

    /**
     * @param context Creates TextReader singleton object
     */
    public static void initTextReader(Context context) {
        if (mTextReader == null)
            mTextReader = new TextReader(context);
    }

    /**
     * @return TextReader
     * null if not initialized.
     */
    public static TextReader getTextReader() {
        return mTextReader;
    }

    /**
     * @param text This method reads text using google TextToSpeech engine
     */
    public void read(String text) {
        int stringLength = text.length();
        Log.v(TAG, "String Length is " + stringLength);

        if (stringLength > mMaxSpeechInputLength) {
            // Divide the string in different pieces.
        }

        HashMap<String, String> params = new HashMap();
        mSpeechCount = mSpeechCount % 10;
        String utteranceId = "utteranceId-" + mSpeechCount;
        mSpeechCount++;
        params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, utteranceId);
        mTextToSpeech.speak(text, TextToSpeech.QUEUE_ADD, params);
    }

    public void destroy() {
        if (mTextReader != null) {
            mTextToSpeech.stop();
            mTextToSpeech.shutdown();
        }

        mTextReader = null;
    }

    /**
     *
     */
    public void check() {
        if (mTextReader != null)
            read("Yes, I am Ready.");
    }
}
