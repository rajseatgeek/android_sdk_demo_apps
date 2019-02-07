package com.zopim.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.zopim.android.sdk.api.ZopimChat;
import com.zopim.android.sdk.model.VisitorInfo;
import com.zopim.android.sdk.prechat.PreChatForm;
import com.zopim.android.sdk.prechat.ZopimChatActivity;

import java.util.Random;

public class EntryActivity extends AppCompatActivity {

    private static final Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Global.isMissingCredentials()) {
            setContentView(R.layout.missing_credentials);
            return;
        }
        setContentView(R.layout.entry_activity);
    }

    /**
     * Pre-sets {@link com.zopim.android.sdk.model.VisitorInfo} data in the chat config and starts the new chat
     */
    public void buttonPreSetData(View view) {
        // build and set visitor info
        String randomNote = Character.toString((char) (random.nextInt(26) + 'a'));

        Toast.makeText(this, "Random note is letter: " + randomNote, Toast.LENGTH_SHORT).show();

        VisitorInfo visitorInfo = new VisitorInfo.Builder()
                .phoneNumber("+1800111222333")
                .email("visitor@example.com")
                .name("Sample Visitor")
                .note(randomNote)
                .build();

        // visitor info can be set at any point when that information becomes available
        ZopimChat.setVisitorInfo(visitorInfo);

        // set pre chat fields as mandatory
        PreChatForm preChatForm = new PreChatForm.Builder()
                .name(PreChatForm.Field.REQUIRED_EDITABLE)
                .email(PreChatForm.Field.REQUIRED_EDITABLE)
                .phoneNumber(PreChatForm.Field.REQUIRED_EDITABLE)
                .department(PreChatForm.Field.REQUIRED_EDITABLE)
                .message(PreChatForm.Field.REQUIRED_EDITABLE)
                .build();

        // build chat config
        ZopimChat.SessionConfig config = new ZopimChat.SessionConfig().preChatForm(preChatForm).department("My memory");

        // start chat activity with config
        ZopimChatActivity.startActivity(EntryActivity.this, config);
    }
}
