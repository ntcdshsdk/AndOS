package com.osshare.andos.util;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

/**
 * Created by apple on 16/9/27.
 */
public class AudioUtil {
    static int SAMPLE_RATE_IN_HZ = 8000;
    static int BUFFER_SIZE = AudioRecord.getMinBufferSize(SAMPLE_RATE_IN_HZ,
            AudioFormat.CHANNEL_IN_DEFAULT, AudioFormat.ENCODING_PCM_16BIT);

    public static void getAudio() {
        AudioRecord record = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE_IN_HZ,
                AudioFormat.CHANNEL_IN_DEFAULT, AudioFormat.ENCODING_PCM_16BIT, BUFFER_SIZE);
        if (record == null) {
            return;
        }

        record.startRecording();
    }
}
