package com.github.techisfun.android.utils.views;

import android.content.Context;
import android.view.*;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrea Maglie on 24/02/14.
 */
public class ExclusiveCheckbox<T> {

    private T currentSelection;

    private List<CheckBox> boxes = new ArrayList<CheckBox>();

    private final ExclusiveCheckboxListener<T> exclusiveCheckboxListener;

    private final LayoutInflater inflater;

    public ExclusiveCheckbox(Context context,
            ExclusiveCheckboxListener<T> exclusiveCheckboxListener) {
        this.exclusiveCheckboxListener = exclusiveCheckboxListener;

        this.inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void create(ViewGroup root, List<T> objs, List<String> descriptions, int layout) {

        if (objs.size() != descriptions.size()) {
            throw new IllegalArgumentException("objs and descriptions size mismatch");
        }

        InnerClickListener clickListener = new InnerClickListener();

        int i = 0;

        for (T object : objs) {
            CheckBox checkBox = (CheckBox) inflater.inflate(layout, root, false);
            checkBox.setTag(object);
            checkBox.setText(descriptions.get(i++));
            checkBox.setOnClickListener(clickListener);

            boxes.add(checkBox);

            if (object.equals(currentSelection)) {
                checkBox.setChecked(true);
            }

            root.addView(checkBox);
        }
    }

    private class InnerClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            T tag = getTag(v);
            if (currentSelection != null && tag.equals(currentSelection)) {
                uncheckAll();
                currentSelection = null;
                exclusiveCheckboxListener.onItemChecked(null);
            } else {
                uncheckAllExcept(v);
                currentSelection = tag;
                exclusiveCheckboxListener.onItemChecked(currentSelection);
            }
        }

    }

    @SuppressWarnings("unchecked")
    private T getTag(View v) {
        return (T) v.getTag();
    }

    private void uncheckAll() {
        for (CheckBox checkBox : boxes) {
            checkBox.setChecked(false);
        }
    }

    private void uncheckAllExcept(View except) {
        for (CheckBox checkBox : boxes) {
            if (checkBox != except) {
                checkBox.setChecked(false);
            }
        }
    }

    public Object getCurrentSelection() {
        return currentSelection;
    }

    public void setCurrentSelection(T currentSelection) {
        this.currentSelection = currentSelection;
    }

}

interface ExclusiveCheckboxListener<T> {
    public void onItemChecked(T object);
}

