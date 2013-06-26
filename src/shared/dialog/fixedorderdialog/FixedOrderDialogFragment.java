/*******************************************************************************
 * Copyright 2013 Steven Rudenko
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package shared.dialog.fixedorderdialog;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FixedOrderDialogFragment extends BaseDialogFragment<FixedOrderDialogFragment.FixedOrderDialogListener> {

  public interface FixedOrderDialogListener {
    public void onDialogButtonClick(FixedOrderDialogFragment f, int button);
    public void onDialogCanceled(FixedOrderDialogFragment f);
  }

  public static final int BUTTON_LEFT = 0;
  public static final int BUTTON_CENTER = 1;
  public static final int BUTTON_RIGHT = 2;
  private static final int BUTTONS_COUNT = 3;

  private static final String ARG_TITLE = "arg:title";
  private static final String ARG_MESSAGE = "arg:message";
  private static final String ARG_LEFT_TEXT = "arg:left";
  private static final String ARG_CENTER_TEXT = "arg:center";
  private static final String ARG_RIGHT_TEXT = "arg:right";
  private static final String ARG_DEFAULT_TEXT = "arg:default";
  private static final String[] ARG_BUTTON_TEXTS = new String[] {
    ARG_LEFT_TEXT, ARG_CENTER_TEXT, ARG_RIGHT_TEXT
  };

  public FixedOrderDialogFragment() {
    // use Builder instead of instantiation
  }

  @Override
  public void onCancel(DialogInterface dialog) {
    super.onCancel(dialog);
    getListener().onDialogCanceled(this);
  }

  @Override
  protected boolean isListenerOptional() {
    return false;
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    final Context context = getActivity();

    final Bundle args = getArguments();
    final int title = args.getInt(ARG_TITLE);
    final int message = args.getInt(ARG_MESSAGE);

    final int defaultButton = args.getInt(ARG_DEFAULT_TEXT, -1);
    final ArrayList<CharSequence> buttonTexts = new ArrayList<CharSequence>();
    for ( int i=0; i<BUTTONS_COUNT; ++i ) {
      final int textId = args.getInt(ARG_BUTTON_TEXTS[i]);
      final CharSequence text = context.getText(textId);
      if ( !TextUtils.isEmpty(text) )
        buttonTexts.add(text);
    }

    final AlertDialog.Builder builder = new AlertDialog.Builder(context)
    .setTitle(title > 0 ? context.getText(title) : null)
    .setMessage(message);

    final int buttonCount = buttonTexts.size();
    switch ( buttonCount ) {
    case 3:
      builder.setNegativeButton(buttonTexts.get(BUTTON_RIGHT), null);
    case 2:
      builder.setNeutralButton(buttonTexts.get(BUTTON_CENTER), null);
    case 1:
      builder.setPositiveButton(buttonTexts.get(BUTTON_LEFT), null);
    default:
      break;
    }

    final AlertDialog dialog = builder.create();

    dialog.setOnShowListener(new DialogInterface.OnShowListener() {
      @Override
      public void onShow(DialogInterface d) {
        final Button[] buttons = getVisibleButtons(dialog);
        for ( int i=0; i<buttonCount; ++i ) {
          final CharSequence text = buttonTexts.get(i);
          final Button button = buttons[i];
          final int position = i;

          if ( defaultButton == i )
            button.setTypeface(null, Typeface.BOLD);

          button.setText(text);
          button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              getListener().onDialogButtonClick(FixedOrderDialogFragment.this, position);
              dismiss();
            }
          });
        }
      }
    });

    return dialog;
  }

  private static final Button[] getVisibleButtons(AlertDialog dialog) {
    final View v = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
    final ViewGroup parent = (ViewGroup)v.getParent();

    final int count = parent.getChildCount();

    final ArrayList<Button> buttons = new ArrayList<Button>();
    for (int i=0; i<count; ++i) {
      final View child = parent.getChildAt(i);

      if ( child.getVisibility() != View.VISIBLE )
        continue;

      if ( child instanceof Button ) {
        buttons.add((Button)child);
      }
    }
    final Button[] result = new Button[buttons.size()];
    buttons.toArray(result);
    return result;
  }

  public static class Builder {
    private final Bundle args = new Bundle();

    public Builder() {
    }

    public Builder setTitle(int title) {
      args.putInt(ARG_TITLE, title);
      return this;
    }

    public Builder setMessage(int message) {
      args.putInt(ARG_MESSAGE, message);
      return this;
    }

    public Builder setLeftButtonText(int text) {
      args.putInt(ARG_LEFT_TEXT, text);
      return this;
    }

    public Builder setCenterButtonText(int text) {
      args.putInt(ARG_CENTER_TEXT, text);
      return this;
    }

    public Builder setRightButtonText(int text) {
      args.putInt(ARG_RIGHT_TEXT, text);
      return this;
    }

    public Builder setDefaultButton(int button) {
      if ( button < 0 || button >= BUTTONS_COUNT )
        return this;
      args.putInt(ARG_DEFAULT_TEXT, button);
      return this;
    }

    public FixedOrderDialogFragment create() {
      final FixedOrderDialogFragment f = new FixedOrderDialogFragment();
      f.setArguments(args);
      return f;
    }
  }
}