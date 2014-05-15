package com.github.techisfun.android.utils.donediscard;

import com.github.techisfun.android.utils.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;

public abstract class DoneDiscardActivity extends SherlockActivity {

    private View discardView, doneView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate a "Done/Discard" custom action bar view.
        LayoutInflater inflater = (LayoutInflater) getSupportActionBar().getThemedContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View customActionBarView = inflater.inflate(
                R.layout.actionbar_custom_view_done_discard, null);
        doneView = customActionBarView.findViewById(R.id.actionbar_done);
        doneView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onDone();
                    }
                });
        discardView = customActionBarView.findViewById(R.id.actionbar_discard);
        discardView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onDiscard();
                    }
                });

        // Show the custom action bar view and hide the normal Home icon and title.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(
                ActionBar.DISPLAY_SHOW_CUSTOM,
                ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME
                        | ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setCustomView(customActionBarView, new ActionBar.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
    }

    protected abstract void onDone();

    protected abstract void onDiscard();

    protected void setDoneText(int stringId) {
        TextView tv = (TextView) doneView.findViewById(R.id.actionbar_done_button_text);
        tv.setText(stringId);
    }

    protected void setDiscardText(int stringId) {
        TextView tv = (TextView) doneView.findViewById(R.id.actionbar_discard_button_text);
        tv.setText(stringId);
    }
}
