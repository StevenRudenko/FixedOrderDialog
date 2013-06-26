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

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class BaseDialogFragment<Listener> extends DialogFragment {

  private Listener listener;

  public Listener getListener() {
    return listener;
  }

  public boolean isShowing() {
    final Dialog dialog = getDialog();
    return dialog != null && dialog.isShowing();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
  }

  protected boolean isListenerOptional() {
    return true;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);

    try {
      listener = (Listener) activity;
    } catch (ClassCastException e) {
      if ( !isListenerOptional() )
        throw new ClassCastException(activity.getClass().getName() + " must implement listener interface.");
    }
  }

  // Hack for android issue 17423 in the compatibility library
  @Override
  public void onDestroyView() {
    if ( getDialog() != null && getRetainInstance() )
      getDialog().setDismissMessage(null);
    super.onDestroyView();
  }
}

